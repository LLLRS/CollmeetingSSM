package com.me.Utils;

import org.springframework.util.DigestUtils;

public class Md5Utils {

    //MD5 salt
    private  final static  String salt = "ahg%$%lagfjh&*OP$#$^&kdjs";

    //md5
    public static  String getMd5(String employeename,String password){
        String base = employeename + "/"  + salt + "/" + password;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    public static void main(String[] args){

        String employeename = "w";
        String password = "1";

        System.out.println(getMd5(employeename,password));
    }
}
