package com.me.dao;

import com.me.entity.Department;
import com.me.entity.Employee;
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
public class DepartmentDaoTest {

    @Autowired
    private DepartmentDao departmentDao;

    @Test
    public void getAllDepartment() {
        List<Department> list = departmentDao.getAllDepartment();
        for(Department t : list)
            System.out.println(t);
    }

    @Test
    public void insert() {
        String name = "新媒体部";
        int t = departmentDao.insert(name);
        System.out.println(t);
    }

    @Test
    public void deleteDepById() {

        int t = departmentDao.deleteDepById(14);
        System.out.println(t);
    }

    @Test
    public void updateDepById() {

        int t = departmentDao.updateDepById("新基建处",10);
        System.out.println(t);
    }
}