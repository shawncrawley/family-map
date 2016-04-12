package com.example.shawn.familymap;

/**
 * Class that represents a person
 */
public class Person {
    private String descendant;
    private String id;
    private String firstName;
    private String lastName;
    private char gender;
    private String spouseId;
    private String fatherId;
    private String motherId;


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
        return !(getMotherId() != null ? !getMotherId().equals(person.getMotherId()) : person.getMotherId() != null);

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
        return result;
    }
}
