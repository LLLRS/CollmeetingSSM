package com.me.dao;

import com.me.entity.*;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface DepartmentDao {

    List<Department> getAllDepartment();


    int insert(@Param("name") String name);


    int deleteDepById(@Param("id") int id);


    int updateDepById(@Param("name") String name, @Param("id") int id);
}
