package com.example.antisocial;

public class UserModel {
    private String name , phone , password , email;
    String CoverPhoto;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    String UserId;

    public String getProfilePhoto() {
        return ProfilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        ProfilePhoto = profilePhoto;
    }

    String ProfilePhoto;

    public UserModel() {

    }

    public String getCoverPhoto() {
        return CoverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        CoverPhoto = coverPhoto;
    }

    public UserModel(String name, String phone, String password, String email) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.email = email;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
