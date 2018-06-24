package com.me.dto;

import java.sql.Timestamp;

public class SearchMeetingDto {

    private int meetingid;
    private String meetingname;
    private String roomname;
    private String reservername;
    private String reservefromdate;
    private String reservetodate;
    private String meetingfromdate;
    private String meetingtodate;
    private int page;
    private int count;
    private Timestamp starttime;
    private Timestamp endtime;
    private Timestamp reservationtime;
    private String employeename;

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }



    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }

    public Timestamp getReservationtime() {
        return reservationtime;
    }

    public void setReservationtime(Timestamp reservationtime) {
        this.reservationtime = reservationtime;
    }

    public SearchMeetingDto() {
    }

    public SearchMeetingDto(String meetingname, String roomname, String reservername,
                        String reservefromdate, String reservetodate, String meetingfromdate,
                        String meetingtodate, int page, int count) {
        this.meetingname = meetingname;
        this.roomname = roomname;
        this.reservername = reservername;
        this.reservefromdate = reservefromdate;
        this.reservetodate = reservetodate;
        this.meetingfromdate = meetingfromdate;
        this.meetingtodate = meetingtodate;
        this.page = page;
        this.count = count;
    }

    public SearchMeetingDto(String meetingname, String roomname, String reservername, String reservefromdate,
                        String reservetodate, String meetingfromdate, String meetingtodate) {
        this.meetingname = meetingname;
        this.roomname = roomname;
        this.reservername = reservername;
        this.reservefromdate = reservefromdate;
        this.reservetodate = reservetodate;
        this.meetingfromdate = meetingfromdate;
        this.meetingtodate = meetingtodate;
    }

    public String getMeetingname() {
        return meetingname;
    }

    public void setMeetingname(String meetingname) {
        this.meetingname = meetingname;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getReservername() {
        return reservername;
    }

    public void setReservername(String reservername) {
        this.reservername = reservername;
    }

    public String getReservefromdate() {
        return reservefromdate;
    }

    public void setReservefromdate(String reservefromdate) {
        this.reservefromdate = reservefromdate;
    }

    public String getReservetodate() {
        return reservetodate;
    }

    public void setReservetodate(String reservetodate) {
        this.reservetodate = reservetodate;
    }

    public String getMeetingfromdate() {
        return meetingfromdate;
    }

    public void setMeetingfromdate(String meetingfromdate) {
        this.meetingfromdate = meetingfromdate;
    }

    public String getMeetingtodate() {
        return meetingtodate;
    }

    public void setMeetingtodate(String meetingtodate) {
        this.meetingtodate = meetingtodate;
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
                "meetingid=" + meetingid +
                ", meetingname='" + meetingname + '\'' +
                ", roomname='" + roomname + '\'' +
                ", reservername='" + reservername + '\'' +
                ", reservefromdate='" + reservefromdate + '\'' +
                ", reservetodate='" + reservetodate + '\'' +
                ", meetingfromdate='" + meetingfromdate + '\'' +
                ", meetingtodate='" + meetingtodate + '\'' +
                ", page=" + page +
                ", count=" + count +
                ", starttime=" + starttime +
                ", endtime=" + endtime +
                ", reservationtime=" + reservationtime +
                ", employeename='" + employeename + '\'' +
                '}';
    }

    public int getMeetingid() {
        return meetingid;
    }

    public void setMeetingid(int meetingid) {
        this.meetingid = meetingid;
    }
}
