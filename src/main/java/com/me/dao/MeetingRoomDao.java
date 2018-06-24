package com.me.dao;

import com.me.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface MeetingRoomDao {

    List<MeetingRoom> getAllMeetingRoom();


    MeetingRoom getMeetingRoomById(@Param("id") int id);


    int insert(MeetingRoom meetingRoom);


    int update(MeetingRoom meetingRoom);
}
