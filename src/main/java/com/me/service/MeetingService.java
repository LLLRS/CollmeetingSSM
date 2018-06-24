package com.me.service;

import com.me.dao.EmployeeDao;
import com.me.entity.*;
import com.me.dao.*;
import com.me.dto.*;
import com.me.entity.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Service
public class MeetingService {

    @Autowired
    private MeetingDao meetingDao;

    @Autowired
    private EmployeeDao employeeDao;

    private List<Employee> emps;

    public  List<MeetingDto> getCanceledMeeting(int empid) {

        return meetingDao.getCanceledMeeting(empid);
    }


    public  List<MeetingDto> getMeeting7Days(int empid) {

        return meetingDao.getMeeting7Days(empid);
    }

    public Meeting getMeetingById(int mid) {

        emps = employeeDao.getEmpByMeetingId(mid);
        return meetingDao.getMeetingById(mid);
    }

    public List<Employee> getEmps() {
        return emps;
    }

    public  List<MeetingDto> getMyBookingMeeting(int empid) {

        return meetingDao.getMyBookingMeeting(empid);
    }

    public  Meeting getMeetingDetailsByMeetingId(int mid) {
        return meetingDao.getMeetingById(mid);
    }

    public  int cancelMeeting(int mid, Timestamp time, String reason) {

        return meetingDao.cancelMeeting(mid, time,reason);
    }

    //TODO
    public  void insert(Meeting meeting, String[] empids){
        int meetingid = meetingDao.insert(meeting);
//        insertAux(meetingid,empids);
    }

//    public  void insertAux(int meetingid, String[] empids) {
//        Connection con = null;
//        PreparedStatement ps = null;
//        try {
//            con = DBUtils.getConnection();
//            ps = con.prepareStatement("insert into meetingparticipants (meetingid,employeeid) values (?,?);");
//            for (String empid : empids) {
//                ps.setInt(1, meetingid);
//                ps.setInt(2, Integer.parseInt(empid));
//                ps.addBatch();
//            }
//            ps.executeBatch();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            DBUtils.close(ps);
//            DBUtils.close(con);
//        }
//    }

    public  List<SearchMeetingDto> searchMeeting(SearchMeetingDto searchMeetingDto) {

        return meetingDao.searchMeeting(searchMeetingDto);
    }


    public  int getCount(SearchMeetingDto searchMeetingDto) {

        return meetingDao.getCount(searchMeetingDto);
    }
}
