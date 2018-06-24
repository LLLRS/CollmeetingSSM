package com.me.dao;


import com.me.dto.MeetingDto;
import com.me.dto.SearchEmpDto;
import com.me.dto.SearchMeetingDto;
import com.me.entity.Meeting;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface MeetingDao {

    //获取七天内的会议
    List<MeetingDto> getMeeting7Days(@Param("empid") int empid);

    List<MeetingDto> getCanceledMeeting(@Param("empid") int empid);

    Meeting getMeetingById(@Param("mid") int mid);

    List<MeetingDto> getMyBookingMeeting(@Param("empid") int empid);

    //new Timestamp(System.currentTimeMillis()
    int cancelMeeting(@Param("mid") int mid, @Param("time") Timestamp time, @Param("reason") String reason);

    int insert(Meeting meeting);


    int getCount(SearchMeetingDto searchMeetingDto);

    //(page - 1) * count
    List<SearchMeetingDto> searchMeeting(SearchMeetingDto searchMeetingDto);


}