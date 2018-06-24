package com.me.dao;


import org.apache.ibatis.annotations.Param;

public interface CountDao {

    /**
     * @return
     */
    int getCount();

    int updateCount();
}
