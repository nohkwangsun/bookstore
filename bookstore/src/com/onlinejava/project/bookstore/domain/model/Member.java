package com.onlinejava.project.bookstore.domain.model;

import java.util.Objects;

public class Member extends Model {
    private String userName;
    private String email;
    private String address;
    private int totalPoint;
    private Grade grade;
    private boolean active;

    @Override
    public String toString() {
        return "Member{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", totalPoint=" + totalPoint +
                ", grade=" + grade +
                ", active=" + active +
                '}';
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return totalPoint == member.totalPoint
                && active == member.active
                && Objects.equals(userName, member.userName)
                && Objects.equals(email, member.email)
                && Objects.equals(address, member.address)
                && grade == member.grade;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, email, address, totalPoint, grade, active);
    }
}
