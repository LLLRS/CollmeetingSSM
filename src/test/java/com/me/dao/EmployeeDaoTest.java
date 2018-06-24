package com.me.dao;

import com.me.dto.SearchEmpDto;
import com.me.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class EmployeeDaoTest {

    //注入Dao实现类依赖
    @Autowired
     EmployeeDao employeeDao;


    @Test
    public void loginTest() throws Exception {

        Employee t = employeeDao.login("w","1");
        System.out.println(t);
    }

    @Test
    public void getEmpByMeetingId() throws Exception {

        List<Employee> list = employeeDao.getEmpByMeetingId(28);
        for(Employee t : list)
            System.out.println(t);

    }

    @Test
    public void getEmpByEmpId() throws Exception{

        System.out.println(employeeDao.getEmpByEmpId(8));
    }

    
    @Test
    public void reg() {

         String employeename = "zs";
         String username = "101";
         String phone = "1";
         String email = "45";
         int status = 2;
         int departmentid = 13;
         String password = "1";
         int role = 2;

         Employee e = new Employee(employeename, username, phone, email, status, departmentid, password, role);

         int t = employeeDao.reg(e);
         System.out.println(t);

    }

    @Test
    public void isUsernameExists() {

        Employee t = employeeDao.isUsernameExists("101");
        System.out.println(t);
    }

    @Test
    public void getUnApproveaccount() {

        List<Employee> list = employeeDao.getUnApproveaccount();
        for(Employee t : list)
            System.out.println(t);

    }

    @Test
    public void updateEmpStatusById() {

        int t = employeeDao.updateEmpStatusById(42,0);
        System.out.println(t);

    }


    @Test
    public void getCount() {
    }

    @Test
    public void searchEmp() {
        String employeename = null;
        String username= null;
        int status = 1;
        int page = 0;
        int count = 10;

        SearchEmpDto searchEmpDto = new SearchEmpDto(employeename, username, status,page,count);

        List<Employee> list = employeeDao.searchEmp(searchEmpDto);
        for(Employee t : list)
            System.out.println(t);
    }

    @Test
    public void getEmpByDepId() {
        List<Employee> list = employeeDao.getEmpByDepId(13);
        for(Employee t : list)
            System.out.println(t);
    }

    @Test
    public void getOddPsswordByEmpId() {
        String t = employeeDao.getOddPsswordByEmpId(42);
        System.out.println(t);
    }

    @Test
    public void updateOddPsswordByEmpId() {
        int t = employeeDao.updateOddPsswordByEmpId(42,"234");
        System.out.println(t);
    }


}
