package com.me.service;

import com.me.entity.*;
import com.me.dao.DepartmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    public  List<Department> getAllDepartment(){

        return  departmentDao.getAllDepartment();
    }

    public   int insert(String name){
        return departmentDao.insert(name);
    }

    public  int deleteById(int id){
        return departmentDao.deleteDepById(id);
    }

    public  int updateDepById(String name, int id){

        return departmentDao.updateDepById(name,id);
    }
}
