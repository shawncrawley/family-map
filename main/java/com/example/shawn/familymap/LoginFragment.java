package com.example.shawn.familymap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This is the fragment that will display the user login screen
 */
public class LoginFragment extends Fragment {

    private EditText txtUsername;
    private EditText txtPassword;
    private EditText txtHost;
    private EditText txtPort;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        txtUsername = (EditText) v.findViewById(R.id.username);
        txtPassword = (EditText) v.findViewById(R.id.password);
        txtHost = (EditText) v.findViewById(R.id.host);
        txtPort = (EditText) v.findViewById(R.id.port);
        Button btnSignIn = (Button) v.findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitClicked();
            }
        });
        return v;
    }

    private void onSubmitClicked() {
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        String host = txtHost.getText().toString();
        String port = txtPort.getText().toString();

        new validateUserTask().execute(username, password, host, port);
    }

    private JSONObject sendRequestToUrl(String url, String requestParams, String authToken) throws IOException {
        URL urlObj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)urlObj.openConnection();
        conn.setRequestMethod("POST");
        conn.addRequestProperty("Accept", "application/json");
        conn.addRequestProperty("Content-Type", "application/json");

        if (authToken != null) {
            conn.addRequestProperty("Authorization", authToken);
        }

        if (requestParams != null) {
            byte[] outputInBytes = requestParams.getBytes("UTF-8");
            OutputStream os = conn.getOutputStream();
            os.write(outputInBytes);
            os.close();
        }

        conn.connect();

        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream responseBody = conn.getInputStream();

            // Read response body bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = responseBody.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }

            // Convert response body bytes to a string
            String responseBodyData = baos.toString();
            try {
                JSONObject loginResponse = new JSONObject(responseBodyData);
                try {
                    loginResponse.getString("message");
                } catch (JSONException e) {
                    loginResponse.put("success", "True");
                    return loginResponse;
                }
                loginResponse.put("success", "False");
                return loginResponse;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            return null;
        }
        return null;
    }

    private class validateUserTask extends AsyncTask<String, Void, JSONObject> {
        String host;
        String port;

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected JSONObject doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            this.host = params[2];
            this.port = params[3];
            String url = "http://" + host + ":" + port + "/user/login";
            String requestParams =  "{username: \"" + username + "\", password: \"" + password + "\"}";
            try {
                JSONObject loginResponse = sendRequestToUrl(url, requestParams, null);
                if (loginResponse == null) {
                    return null;
                } else {
                    return loginResponse;
                }
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject loginResponse) {
            if (loginResponse == null) {
                Toast toast = Toast.makeText(getContext(), "Error encountered while processing request.", Toast.LENGTH_LONG);
                toast.show();
            } else {
                try {
                    if (loginResponse.getString("success").equalsIgnoreCase("True")) {
                        String authorization = loginResponse.getString("Authorization");
                        new getUserEventsTask().execute(this.host, this.port, authorization);
                    } else {
                        Toast toast = Toast.makeText(getContext(), loginResponse.getString("message"), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class getUserEventsTask extends AsyncTask<String, Void, JSONObject> {
        String host;
        String port;
        String authorization;

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected JSONObject doInBackground(String... params) {
            host = params[0];
            port = params[1];
            authorization = params[2];
            String url = "http://" + host + ":" + port + "/event/";

            try {
                JSONObject getEventsResponse = sendRequestToUrl(url, null, authorization);
                if (getEventsResponse == null) {
                    return null;
                } else {
                    return getEventsResponse;
                }
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject getEventsResponse) {
            if (getEventsResponse == null) {
                Toast toast = Toast.makeText(getContext(), "Error encountered while getting user info.", Toast.LENGTH_LONG);
                toast.show();
            } else {
                try {
                    JSONArray data = getEventsResponse.getJSONArray("data");
                    int dataCount = data.length();
                    for (int i = 0; i < dataCount; i++) {
                        JSONObject eventObj = data.getJSONObject(i);
                        Event event = new Event();
                        event.setId(eventObj.getString("eventID"));
                        event.setPersonId(eventObj.getString("personID"));
                        event.setLatitude(eventObj.getDouble("latitude"));
                        event.setLongitude(eventObj.getDouble("longitude"));
                        event.setCountry(eventObj.getString("country"));
                        event.setCity(eventObj.getString("city"));
                        event.setDescription(eventObj.getString("description"));
                        event.setYear(Integer.parseInt(eventObj.getString("year")));
                        event.setDescendant(eventObj.getString("descendant"));
                        DataModel.SINGLETON.addEvent(event.getId(), event);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new getUserPeopleTask().execute(this.host, this.port, this.authorization);
            }
        }
    }

    private class getUserPeopleTask extends AsyncTask<String, Void, JSONObject> {

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected JSONObject doInBackground(String... params) {
            String host = params[0];
            String port = params[1];
            String authorization = params[2];
            String url = "http://" + host + ":" + port + "/person/";

            try {
                JSONObject getEventsResponse = sendRequestToUrl(url, null, authorization);
                if (getEventsResponse == null) {
                    return null;
                } else {
                    return getEventsResponse;
                }
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject getPeopleResponse) {
            if (getPeopleResponse == null) {
                Toast toast = Toast.makeText(getContext(), "Error encountered while getting user info.", Toast.LENGTH_LONG);
                toast.show();
            } else {
                try {
                    JSONArray data = getPeopleResponse.getJSONArray("data");
                    int dataCount = data.length();
                    for (int i = 0; i < dataCount; i++) {
                        JSONObject eventObj = data.getJSONObject(i);
                        Person person = new Person();
                        person.setId(eventObj.getString("personID"));
                        person.setFirstName(eventObj.getString("firstName"));
                        person.setLastName(eventObj.getString("lastName"));
                        person.setGender(eventObj.getString("gender").charAt(0));
                        person.setDescendant(eventObj.getString("descendant"));
                        try {
                            person.setSpouseId(eventObj.getString("spouse"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            person.setFatherId(eventObj.getString("father"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            person.setMotherId(eventObj.getString("mother"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        DataModel.SINGLETON.addPerson(person.getId(), person);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MainActivity mainActivity = ((MainActivity) getActivity());
                mainActivity.onPrepareOptionsMenu(mainActivity.get_menu());
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new MapviewFragment())
                        .addToBackStack(null)
                        .commit();
            }
        }
    }
}
