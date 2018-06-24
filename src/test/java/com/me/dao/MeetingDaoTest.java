package com.me.dao;

import com.me.dto.MeetingDto;
import com.me.dto.SearchEmpDto;
import com.me.dto.SearchMeetingDto;
import com.me.entity.Meeting;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class MeetingDaoTest {

    @Autowired
    MeetingDao meetingDao;

    @Test
    public void getMeeting7Days() {
        List<MeetingDto> list = meetingDao.getMeeting7Days(17);
        for(MeetingDto t : list)
            System.out.println(t);
    }

    @Test
    public void getCanceledMeeting() {

        List<MeetingDto> list = meetingDao.getCanceledMeeting(8);
        for(MeetingDto t : list)
            System.out.println(t);
    }

    @Test
    public void getMeetingById() {

        Meeting t = meetingDao.getMeetingById(25);
        System.out.println(t);
    }


    @Test
    public void getMyBookingMeeting() {

        List<MeetingDto> list = meetingDao.getMyBookingMeeting(8);
        for(MeetingDto t : list)
            System.out.println(t);
    }

    @Test
    public void cancelMeeting() {

        Timestamp time = new Timestamp(System.currentTimeMillis());
        String reason = "rrrrrr";
        int t = meetingDao.cancelMeeting(43,time,reason);
        System.out.println(t);
    }

    @Test
    public void insert() {

        String meetingname = "cvcv";
        int roomid = 10;
        int reservationistid = 9;
        int numberofparticipants = 89;
        Timestamp starttime = new Timestamp(System.currentTimeMillis());
        Timestamp endtime = new Timestamp(System.currentTimeMillis()+1000*24*60*60);
        Timestamp reservationtime = new Timestamp(1000*24*60*60);
        String description = "5656666666666666666666";
        int status = 1;

        Meeting m = new Meeting(meetingname, roomid, reservationistid, numberofparticipants, starttime, endtime, reservationtime, description,status);

        int t = meetingDao.insert(m);
        System.out.println(t);
    }

    @Test
    public void getCount() {
    }

    @Test
    public void searchMeeting() {

        SearchMeetingDto searchMeetingDto = new SearchMeetingDto(null, null, null,
                "", "",
                null, null, 0, 10);

        List<SearchMeetingDto> list = meetingDao.searchMeeting(searchMeetingDto);
        for(SearchMeetingDto t : list)
            System.out.println(t);
    }
}