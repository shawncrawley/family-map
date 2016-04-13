package com.example.shawn.familymap;

import java.io.Serializable;

/**
 * Class that represents an event
 */
public class Event implements Comparable, Serializable {
    private String id;
    private String personId;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String description;
    private int year = 0;
    private String descendant;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getInfo() {
        return getDescription() + ": " + getCity() + ", " + getCountry() + " (" + getYear() + ")";
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", personId='" + personId + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", descendant='" + descendant + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;

        Event event = (Event) o;

        if (Double.compare(event.getLatitude(), getLatitude()) != 0) return false;
        if (Double.compare(event.getLongitude(), getLongitude()) != 0) return false;
        if (getYear() != event.getYear()) return false;
        if (!getId().equals(event.getId())) return false;
        if (!getPersonId().equals(event.getPersonId())) return false;
        if (!getCountry().equals(event.getCountry())) return false;
        if (!getCity().equals(event.getCity())) return false;
        if (!getDescription().equals(event.getDescription())) return false;
        return getDescendant().equals(event.getDescendant());
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getId().hashCode();
        result = 31 * result + getPersonId().hashCode();
        temp = Double.doubleToLongBits(getLatitude());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getLongitude());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getCountry().hashCode();
        result = 31 * result + getCity().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getYear();
        result = 31 * result + getDescendant().hashCode();
        return result;
    }

    /**
     * Compares this object to the specified object to determine their relative
     * order.
     *
     * @param another the object to compare to this instance.
     * @return a negative integer if this instance is less than {@code another};
     * a positive integer if this instance is greater than
     * {@code another}; 0 if this instance has the same order as
     * {@code another}.
     * @throws ClassCastException if {@code another} cannot be converted into something
     *                            comparable to {@code this} instance.
     */
    @Override
    public int compareTo(Object another) {
        int compareYear = ((Event)another).getYear();
        /* For Ascending order*/
        if (this.getYear() - compareYear == 0) {
            return (this.getDescription().toLowerCase().compareTo(((Event) another).getDescription().toLowerCase()));
        }
        return this.getYear() - compareYear;
    }
}
