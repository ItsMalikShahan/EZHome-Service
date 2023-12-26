package com.example.ezhomeservice.model;

public class ServiceProviderModel {
    private String name, email, contact, experience, field, password, address,
            profileImgUrl, description, areaCover, workingDays, workingHours;

    public ServiceProviderModel(){}
    public ServiceProviderModel(String profileImgUrl, String description, String areaCover, String workingDays, String workingHours) {
        this.profileImgUrl = profileImgUrl;
        this.description = description;
        this.areaCover = areaCover;
        this.workingDays = workingDays;
        this.workingHours = workingHours;
    }

    public ServiceProviderModel(String name, String email, String contact, String field, String password, String address, String experience) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.field = field;
        this.password = password;
        this.address = address;
        this.experience = experience;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAreaCover() {
        return areaCover;
    }

    public void setAreaCover(String areaCover) {
        this.areaCover = areaCover;
    }

    public String getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(String workingDays) {
        this.workingDays = workingDays;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }
}
