package com.fydp.fundusapp.Objects;

public class Patient {

    private String patientId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String phoneNumber;

    public Patient(String firstName, String lastName, String dateOfBirth, String phoneNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    public Patient(String patientId, String firstName, String lastName, String dateOfBirth, String phoneNumber){
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    public String getPatientId(){
        return patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
