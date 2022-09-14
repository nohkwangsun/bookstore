package com.onlinejava.project.bookstore.domain;

public class Member {
    private String userName;
    private String email;
    private String address;

    private int totalPoint;
    private boolean active;

    public Member(String userName, String email, String address, int totalPoint, boolean active) {
        this.userName = userName;
        this.email = email;
        this.address = address;
        this.totalPoint = totalPoint;
        this.active = active;
    }

    @Override
    public String toString() {
        return "Member{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", totalPoint=" + totalPoint +
                ", active=" + active +
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    public void addPoint(int point) {
        this.totalPoint += point;
    }

    public String toCsvString() {
        return String.join(", ", userName, email, address, String.valueOf(active), String.valueOf(totalPoint));
    }
}
