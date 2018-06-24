package com.me.dto;

public class SearchEmpDto {

    private String employeename;
    private String username;
    private int status;
    private int page;
    private int count;


    public SearchEmpDto() {
    }

    public SearchEmpDto(String employeename, String username, int status) {
        this.employeename = employeename;
        this.username = username;
        this.status = status;
    }

    public SearchEmpDto(String employeename, String username, int status, int page, int count) {
        this.employeename = employeename;
        this.username = username;
        this.status = status;
        this.page = page;
        this.count = count;
    }

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "SearchMeetingDto{" +
                "employeename='" + employeename + '\'' +
                ", username='" + username + '\'' +
                ", status=" + status +
                ", page=" + page +
                ", count=" + count +
                '}';
    }
}
