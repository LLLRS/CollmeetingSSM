package com.me.web;



import com.me.Utils.Md5Utils;
import com.me.dto.MeetingDto;
import com.me.dto.SearchEmpDto;
import com.me.dto.SearchMeetingDto;
import com.me.entity.Department;
import com.me.entity.Employee;
import com.me.entity.Meeting;
import com.me.entity.MeetingRoom;
import com.me.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Controller
@SessionAttributes({"loginUser","vc"})
@RequestMapping("/coolmeetting")
public class EmployeeController {

    @Autowired
    EmployeeService es;

    @Autowired
    MeetingService meetingService;

    @Autowired
    CountService countService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    MeetingRoomService meetingRoomService;

    @RequestMapping(value = "/login")
    public String detail(String username, String password, HttpServletRequest request, Model model)
    {
        if(username==null||username.equals("")||password==null||password.equals(""))
            return "login";

        int login = es.login(username,password);

        switch (login){

            case 0 : model.addAttribute("error", "用户待审批，请稍候");
                     return "login";

            case 1 :
                     model.addAttribute("loginUser", es.getLoginUser());
                     model.addAttribute("vc",countService.getVcCount());

                    HttpSession session = request.getSession();
                    session.setAttribute("username", username);

                     return "redirect:/coolmeetting/notifications";

            case 2 : model.addAttribute("error", "用户审批未通过，请重新注册");
                     return "login";

            case 3 : model.addAttribute("error", "用户名或者密码输入错误，请重新登录");
                     return "login";

            case -1 : model.addAttribute("error", "账号已关闭，登陆失败，请联系管理员");
                      return "login";
        }

        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/coolmeetting/login";
    }

    @RequestMapping(value = "/notifications")
    public String notifications(@ModelAttribute("loginUser")Employee loginUser,Model model){

//        System.out.println(loginUser);
        int loginEmpId = loginUser.getEmployeeid();
        List<MeetingDto> mt7 = meetingService.getMeeting7Days(loginEmpId);
        List<MeetingDto> ct = meetingService.getCanceledMeeting(loginEmpId);

//        System.out.println(e);
        model.addAttribute("mt7",mt7);
        model.addAttribute("cm",ct);

        return "notifications";
    }


    @RequestMapping("/meetingdetails")
    public String meetingdetails(int meetingid,String type, Model model){

        Meeting meeting = meetingService.getMeetingById(meetingid);

        List<Employee> emps = meetingService.getEmps();
        model.addAttribute("emps",emps);
        model.addAttribute("mt",meeting);
        if(type!=null)
            model.addAttribute("type",type);


        return "meetingdetails";
    }

    @RequestMapping("/cancelmeeting")
    public String cancelmeeting(int meetingid, Model model){

        Meeting mt = meetingService.getMeetingDetailsByMeetingId(meetingid);
        model.addAttribute("m",mt);


        return "cancelmeeting";
    }

    @RequestMapping("/docanceled")
    public String docanceled(int meetingid,String canceledreason, Model model){

        Timestamp time = new Timestamp(System.currentTimeMillis());
        meetingService.cancelMeeting(meetingid,time,canceledreason);
//        model.addAttribute("m",mt);

        return "redirect:/coolmeetting/mybooking";
    }

    @RequestMapping("/mybooking")
    public String mybooking(@ModelAttribute("loginUser")Employee loginUser,Model model){

        System.out.println(loginUser);

        List<MeetingDto> mrs = meetingService.getMyBookingMeeting(loginUser.getEmployeeid());
        model.addAttribute("mrs",mrs);

        return "mybookings";
    }

    @RequestMapping("/mymeeting")
    public String mymeeting(@ModelAttribute("loginUser")Employee loginUser,Model model){

//        System.out.println(loginUser);

        List<MeetingDto> mrs = meetingService.getMyBookingMeeting(loginUser.getEmployeeid());
        model.addAttribute("mrs",mrs);

        return "mymeetings";
    }

    @RequestMapping("/departments")
    public String departments(Model model){

        List<Department> list = departmentService.getAllDepartment();
        model.addAttribute("list", list);

        return "departments";
    }

    @RequestMapping("/adddep")
    public String adddep(String departmentname,Model model){

        int flag = departmentService.insert(departmentname);
        if(flag==1){
            return "redirect:/coolmeetting/departments";
        }else{
            model.addAttribute("error", "添加失败");
            return "departments";
        }
    }

    @RequestMapping("/deletedep")
    public String deletedep(int departmentid,Model model){

        int flag = departmentService.deleteById(departmentid);
        if(flag==1){
            return "redirect:/coolmeetting/departments";
        }else{
            model.addAttribute("error", "删除失败");
            return "departments";
        }
    }

    @RequestMapping("/updateDep")
    public String updateDep(int id,String depName,Model model){

        int flag = departmentService.updateDepById(depName,id);
//        PrintWriter out = response.getWriter();
        //TODO
//        if (flag == 1) {
//            out.write("修改成功");
//        } else {
//            out.write("修改失败");
//        }

        return "departments";

    }

    @RequestMapping("/reg")
    public String reg(Employee employee,Model model){

        List<Department> list = departmentService.getAllDepartment();
        model.addAttribute("list", list);

        return "register";
    }

    @RequestMapping("/doreg")
    //直接映射
    public String doreg(Employee employee,Model model){


        int reg = es.reg(employee);
        if (reg == 1) {
            //注册成功，再次跳转到注册页面

            List<Department> list = departmentService.getAllDepartment();
            model.addAttribute("list", list);
            return "register";
        } else if (reg == -1) {
            //用户名重复，注册失败
            model.addAttribute("error", "用户名重复，注册失败");
            return "forward:/coolmeetting/reg";
        } else {
            model.addAttribute("error", "不明原因，注册失败");
            return "forward:/coolmeetting/reg";
        }
    }

    @RequestMapping("/approveaccount")
    public String approveaccount(Model model){

        List<Employee> e = es.getUnApproveaccount();
        model.addAttribute("list",e);

        return "approveaccount";
    }
    
    @RequestMapping("/updateEmpStatus")
    public String aupdateEmpStatus(int empid,int status,Model model){

        List<Employee> e = es.getUnApproveaccount();
        model.addAttribute("list",e);
        
        int flag = es.updateEmpStatusById(empid,status);

        if (flag== 1) {
            
            return "redirect:/coolmeetting/approveaccount";
        } else {
            model.addAttribute("error", "审批失败");
            return "approveaccount";
        }
       
    }
    

    @RequestMapping("/serachemp")
    public String serachemp(HttpServletRequest request, Model model){


        String us = request.getParameter("updateStatus");
        if(us!=null&&"-1".equals(us)){
            int empid = Integer.parseInt(request.getParameter("empid"));
            int st = Integer.parseInt(us);
            es.updateEmpStatusById(empid,st);
        }

        String employeename = request.getParameter("employeename");
        String username = request.getParameter("username");
        String status = request.getParameter("status");
        String page = request.getParameter("page");
        String count = request.getParameter("count");

        if (status == null || "".equals(status)) {
            status = "1";
        }
        if (page == null || "".equals(page)) {
            page = "1";
        }
        if (count == null || "".equals(count)) {
            count = "10";
        }

        SearchEmpDto searchEmpDto = new SearchEmpDto(employeename, username, Integer.parseInt(status),
                (Integer.parseInt(page)-1)*Integer.parseInt(count), Integer.parseInt(count));

        List<Employee> list = es.searchEmp(searchEmpDto);
        int totalCount = es.getCount(searchEmpDto);
        int totalPage = totalCount / Integer.parseInt(count) + 1;

        request.setAttribute("list", list);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("page", page);
        request.setAttribute("employeename", employeename);
        request.setAttribute("username", username);
        request.setAttribute("status", status);

        return "searchemployees";

    }

    @RequestMapping("/searchmeeting")
    public String  searchmeeting(HttpServletRequest request, Model model){


        String meetingname = request.getParameter("meetingname");
        String roomname = request.getParameter("roomname");
        String reservername = request.getParameter("reservername");
        String reservefromdate = request.getParameter("reservefromdate");
        String reservetodate = request.getParameter("reservetodate");
        String meetingfromdate = request.getParameter("meetingfromdate");
        String meetingtodate = request.getParameter("meetingtodate");
        String page = request.getParameter("page");
        String count = request.getParameter("count");
        if (page == null || "".equals(page)) {
            page = "1";
        }
        if (count == null || "".equals(count)) {
            count = "10";
        }

        SearchMeetingDto searchMeetingDto = new SearchMeetingDto(meetingname, roomname, reservername,
                reservefromdate, reservetodate, meetingfromdate, meetingtodate,
                (Integer.parseInt(page)-1)*Integer.parseInt(count), Integer.parseInt(count));

        List<SearchMeetingDto> list = meetingService.searchMeeting(searchMeetingDto);
        int totalCount = meetingService.getCount(searchMeetingDto);

        int totalPage = totalCount / Integer.parseInt(count) + 1;

        request.setAttribute("list", list);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("page", page);
        request.setAttribute("meetingname", meetingname);
        request.setAttribute("roomname", roomname);
        request.setAttribute("reservername", reservername);
        request.setAttribute("reservefromdate", reservefromdate);
        request.setAttribute("reservetodate", reservetodate);
        request.setAttribute("meetingfromdate", meetingfromdate);
        request.setAttribute("meetingtodate", meetingtodate);
        request.setAttribute("count", count);

        return "searchmeetings";

    }

    @RequestMapping("/addmr")
    public String addmr(HttpServletRequest request, Model model){
//        String roomid = request.getParameter("roomid");

        String add = request.getParameter("addstatus");
//        System.out.println(add);
        if("0".equals(add)){
            return "addmeetingroom";
        }else{
            System.out.println("okkkk");
            String roomid = request.getParameter("roomid");
            String roomnum = request.getParameter("roomnum");
            String roomname = request.getParameter("roomname");
            String capacity = request.getParameter("capacity");
            String status = request.getParameter("status");
            String description = request.getParameter("description");

            MeetingRoom meetingRoom = new MeetingRoom(Integer.parseInt(roomnum),
                    roomname, Integer.parseInt(capacity), Integer.parseInt(status), description);

            if (roomid == null || "".equals(roomid)) {

                int result = meetingRoomService.insert(meetingRoom);
                if (result == 1) {
                    //去查看会议室页面
                    return "redirect:/coolmeetting/getallmr";
                } else {
                    model.addAttribute("error", "添加失败");
                    return "addmeetingroom";
                }
            }else{
                meetingRoom.setRoomid(Integer.parseInt(roomid));
                int update = meetingRoomService.update(meetingRoom);
                if (update == 1) {
                    return "redirect:/coolmeetting/getallmr";
                }else{
                    //更新失败
//                    System.out.println("tttttttttttttttttttt");
                    model.addAttribute("error", "更新失败");
                    return "addmeetingroom";
                }
            }

        }
    }

    @RequestMapping("/getallmr")
    public String getallmr(Model model){

        List<MeetingRoom> all = meetingRoomService.getAllMeetingRoom();

        model.addAttribute("list",all);
        return "meetingrooms";
    }


    @RequestMapping("/roomdetails")
    public String roomdetails(int roomid, Model model){

        MeetingRoom meetingRoom = meetingRoomService.getMeetingRoomById(roomid);
        model.addAttribute("mr", meetingRoom);

        return "roomdetails";
    }

    @RequestMapping("/bookmeeting")
    public String bookmeeting( Model model){

        List<MeetingRoom> list = meetingRoomService.getAllMeetingRoom();
        model.addAttribute("mrs",list);

        return "bookmeeting";
    }

    @RequestMapping("/dobookmeeting")
    public String dobookmeeting( Model model){

        List<MeetingRoom> list = meetingRoomService.getAllMeetingRoom();
        model.addAttribute("mrs",list);

        return "bookmeeting";
    }


    @RequestMapping("/getalldepjson")
    @ResponseBody
    public List<Department> getalldepjson() {

        List<Department> list = departmentService.getAllDepartment();
        return list;

    }

    @RequestMapping("/getempbydepid")
    @ResponseBody
    public List<Employee> getempbydepid(int departmentid){

        List<Employee> list = es.getEmpByDepId(departmentid);
        return list;
    }

    @RequestMapping("/changepassword")
    public String changepassword(@ModelAttribute("loginUser")Employee loginUser,
                                 HttpServletRequest request, Model model){

        String dostatus = request.getParameter("dostatus");

        if("1".equals(dostatus)) {
            String np = request.getParameter("new");
            String cp = request.getParameter("comfirm");
            String op = Md5Utils.getMd5(loginUser.getUsername(),request.getParameter("origin"));


            int loginEmpId = loginUser.getEmployeeid();
            String ep = es.getOddPsswordByEmpId(loginEmpId);

//            System.out.println("employeename: "+loginUser.getUsername());
//            System.out.println("loginEmpId: "+loginEmpId+"  ep:  "+ ep);
//            System.out.println("loginEmpId: "+loginEmpId+"  op:  "+ op);

            if(ep.equals(op)){
                if(Objects.equals(cp,np)){
                    es.updateOddPsswordByEmpId(loginEmpId, Md5Utils.getMd5(loginUser.getUsername(),np));
                    model.addAttribute("error", "密码修改成功，请用新密码登录");
                    return "login";
                }else{
                    model.addAttribute("error", "两次输入密码不一致");
                }
            }else{
                model.addAttribute("error", "原密码输入错误");
            }
        }

        return "changepassword";
    }
}
