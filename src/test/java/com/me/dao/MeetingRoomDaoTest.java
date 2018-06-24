package com.me.dao;

import com.me.entity.Department;
import com.me.entity.MeetingRoom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class MeetingRoomDaoTest {

    @Autowired
    MeetingRoomDao meetingRoomDao;

    @Test
    public void getAllMeetingRoom() {

        List<MeetingRoom> list = meetingRoomDao.getAllMeetingRoom();
        for(MeetingRoom t : list)
            System.out.println(t);
    }

    @Test
    public void getMeetingRoomById() {

        MeetingRoom t = meetingRoomDao.getMeetingRoomById(5);
        System.out.println(t);
    }

    @Test
    public void insert() {

        int roomnum = 1001;
        String roomname = "七天";
        int capacity = 10;
        int status = 0;
        String description = "shushe";

        MeetingRoom m = new MeetingRoom();
        m.setRoomnum(roomnum);
        m.setRoomname(roomname);
        m.setCapacity(capacity);
        m.setStatus(status);
        m.setDescription(description);

        int t = meetingRoomDao.insert(m);
        System.out.println(t);
    }

    @Test
    public void update() {

        int roomnum = 1002;
        String roomname = "个困";
        int capacity = 10;
        int status = 0;
        String description = "shushe";

        MeetingRoom m = new MeetingRoom();
        m.setRoomnum(roomnum);
        m.setRoomname(roomname);
        m.setCapacity(capacity);
        m.setStatus(status);
        m.setDescription(description);
        m.setRoomid(5);

        int t = meetingRoomDao.update(m);
        System.out.println(t);
    }


}