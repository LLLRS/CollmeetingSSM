package com.me.service;


import com.me.dao.MeetingRoomDao;
import com.me.entity.MeetingRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class MeetingRoomService {

    @Autowired
    private MeetingRoomDao meetingRoomDao;


    public  int insert(MeetingRoom meetingRoom) {

        return meetingRoomDao.insert(meetingRoom);
    }


    public List<MeetingRoom> getAllMeetingRoom()
    {
        return meetingRoomDao.getAllMeetingRoom();
    }


    public   MeetingRoom getMeetingRoomById(int id) {

        return meetingRoomDao.getMeetingRoomById(id);
    }


    public   int update(MeetingRoom meetingRoom) {

        return meetingRoomDao.update(meetingRoom);
    }
}
