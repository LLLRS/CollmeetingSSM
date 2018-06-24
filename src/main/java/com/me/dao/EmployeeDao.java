package com.me.dao;

import com.me.dto.SearchEmpDto;
import com.me.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface EmployeeDao {

    /**
     * 登录校验
     * 结果为 null 则不存在
     */
    Employee login(@Param("username") String username, @Param("password") String password);


    List<Employee> getEmpByMeetingId(@Param("mid") int mid);


    int reg(Employee employee);

    Employee getEmpByEmpId(@Param("empId") int empId);
    /**
     * 登录校验
     * 结果为 null 则用户名不存在
     */
    Employee isUsernameExists(String username);


    List<Employee> getUnApproveaccount();


    int updateEmpStatusById(@Param("id") int id, @Param("status") int status);



    int getCount(SearchEmpDto searchEmpDto);

    /*
        (page - 1) * count
    */
    List<Employee> searchEmp(SearchEmpDto searchEmpDto);


    List<Employee> getEmpByDepId(@Param("depId") int depId);



    String getOddPsswordByEmpId(@Param("empId") int empId);


    int updateOddPsswordByEmpId(@Param("empId") int empId, @Param("password") String password);


}
