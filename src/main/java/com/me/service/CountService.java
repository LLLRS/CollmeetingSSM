package com.me.service;

import com.me.dao.CountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountService {

    @Autowired
    private CountDao countDao;

    public int getVcCount(){

        countDao.updateCount();

        return countDao.getCount();

    }


}
