package com.onlinejava.project.bookstore.domain;

public class Member extends Entity {
    private String userName;
    private String email;
    private String address;
    private int totalPoint;
    private Grade grade;
    private boolean active;


    public Member(String userName, String email, String address, int totalPoint, boolean active) {
        this.userName = userName;
        this.email = email;
        this.address = address;
        this.totalPoint = totalPoint;
        this.active = active;
        this.grade = Grade.GENERAL;
    }

    @Override
    public String toString() {
        return "Member{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", totalPoint=" + totalPoint +
                ", active=" + active +
                ", grade=" + grade +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void addPoint(int point) {
        this.totalPoint += point;
    }
}
