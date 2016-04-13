package com.example.shawn.familymap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class that represents a person
 */
public class Person implements Serializable {
    private String descendant;
    private String id;
    private String firstName;
    private String lastName;
    private char gender;
    private String spouseId = null;
    private String fatherId = null;
    private String motherId = null;
    private String baptismId = null;
    private String birthId = null;
    private String censusId = null;
    private String christeningId = null;
    private String deathId = null;
    private String marriageId = null;
    private String[] eventIds = new String[6];


    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getSpouseId() {
        return spouseId;
    }

    public void setSpouseId(String spouseId) {
        this.spouseId = spouseId;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getMotherId() {
        return motherId;
    }

    public void setMotherId(String motherId) {
        this.motherId = motherId;
    }

    public String getBaptismId() {
        return baptismId;
    }

    public void setBaptismId(String baptismId) {
        this.baptismId = baptismId;
        eventIds[0] = baptismId;
    }

    public String getBirthId() {
        return birthId;
    }

    public void setBirthId(String birthId) {
        this.birthId = birthId;
        eventIds[1] = birthId;
    }

    public String getCensusId() {
        return censusId;
    }

    public void setCensusId(String censusId) {
        this.censusId = censusId;
        eventIds[2] = censusId;
    }

    public String getChristeningId() {
        return christeningId;
    }

    public void setChristeningId(String christeningId) {
        this.christeningId = christeningId;
        eventIds[3] = christeningId;
    }

    public String getDeathId() {
        return deathId;
    }

    public void setDeathId(String deathId) {
        this.deathId = deathId;
        eventIds[4] = deathId;
    }

    public String getMarriageId() {
        return marriageId;
    }

    public void setMarriageId(String marriageId) {
        this.marriageId = marriageId;
        eventIds[5] = marriageId;
    }

    public ArrayList<Event> getEventsList() {
        ArrayList<Event> eventList = new ArrayList<>();
        for (String id : getEventIds()) {
            if (id != null) {
                eventList.add(DataModel.SINGLETON.getEvents().get(id));
            }
        }
        Collections.sort(eventList);
        return eventList;
    }

    @Override
    public String toString() {
        return "Person{" +
                "descendant='" + descendant + '\'' +
                ", id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", spouseId='" + spouseId + '\'' +
                ", fatherId='" + fatherId + '\'' +
                ", motherId='" + motherId + '\'' +
                ", baptismId='" + baptismId + '\'' +
                ", birthId='" + birthId + '\'' +
                ", censusId='" + censusId + '\'' +
                ", christeningId='" + christeningId + '\'' +
                ", deathId='" + deathId + '\'' +
                ", marriageId='" + marriageId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (getGender() != person.getGender()) return false;
        if (!getDescendant().equals(person.getDescendant())) return false;
        if (!getId().equals(person.getId())) return false;
        if (!getFirstName().equals(person.getFirstName())) return false;
        if (!getLastName().equals(person.getLastName())) return false;
        if (getSpouseId() != null ? !getSpouseId().equals(person.getSpouseId()) : person.getSpouseId() != null)
            return false;
        if (getFatherId() != null ? !getFatherId().equals(person.getFatherId()) : person.getFatherId() != null)
            return false;
        if (getMotherId() != null ? !getMotherId().equals(person.getMotherId()) : person.getMotherId() != null)
            return false;
        if (getBaptismId() != null ? !getBaptismId().equals(person.getBaptismId()) : person.getBaptismId() != null)
            return false;
        if (getBirthId() != null ? !getBirthId().equals(person.getBirthId()) : person.getBirthId() != null)
            return false;
        if (getCensusId() != null ? !getCensusId().equals(person.getCensusId()) : person.getCensusId() != null)
            return false;
        if (getChristeningId() != null ? !getChristeningId().equals(person.getChristeningId()) : person.getChristeningId() != null)
            return false;
        if (getDeathId() != null ? !getDeathId().equals(person.getDeathId()) : person.getDeathId() != null)
            return false;
        return !(getMarriageId() != null ? !getMarriageId().equals(person.getMarriageId()) : person.getMarriageId() != null);

    }

    @Override
    public int hashCode() {
        int result = getDescendant().hashCode();
        result = 31 * result + getId().hashCode();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + (int) getGender();
        result = 31 * result + (getSpouseId() != null ? getSpouseId().hashCode() : 0);
        result = 31 * result + (getFatherId() != null ? getFatherId().hashCode() : 0);
        result = 31 * result + (getMotherId() != null ? getMotherId().hashCode() : 0);
        result = 31 * result + (getBaptismId() != null ? getBaptismId().hashCode() : 0);
        result = 31 * result + (getBirthId() != null ? getBirthId().hashCode() : 0);
        result = 31 * result + (getCensusId() != null ? getCensusId().hashCode() : 0);
        result = 31 * result + (getChristeningId() != null ? getChristeningId().hashCode() : 0);
        result = 31 * result + (getDeathId() != null ? getDeathId().hashCode() : 0);
        result = 31 * result + (getMarriageId() != null ? getMarriageId().hashCode() : 0);
        return result;
    }

    public String[] getEventIds() {
        return eventIds;
    }
}
