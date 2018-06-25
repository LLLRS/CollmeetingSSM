SSMCollmeeting
=============

写在前面
------------

对之前的用JavaBean+Servlet+Jsp的collmeting项目用SSM+Maven进行了重写，重写过程中遇到了很多问题，解决这些问题的过程中，进一步加深了对SSM的理解。<br>
- [SSM的整合流程](#1)
- [SpringMVC传值问题](#2)
-  [SpringMVC的拦截器](#3)

<a id="1">SSM的整合流程</a>
-----------

完成Spring、Spring MVC以及Mybatis整合工作，其实不难，就是将这三个框架的配置文件提取出来放在一个项目中，使得Spring可以统一管理资源。整个过程分为三步。<br><br>
第一步:整合dao(即mapper)，完成Spring与Mybatis的整合。<br>
第二步:整合service，Spring管理service接口，service中可以调用Spring容器中的dao(mapper)。<br>
第三步:整合controller，Spring管理controller接口，在controller调用service。<br>
<br>
**目录结构**<br>
[](https://github.com/LLLRS/SSMCollmeeting/blob/master/img/content.JPG)
<br><br>
可以看出，利用Maven创建的项目将所有的配置文件都放在了resources目录下，包含mapper文件，spring-dao.xml,spring-service.xml,spring-web.xml,以及mybatis-config.xml,jdbc.properties(存放数据库的配置文件).
<br><br>
1.整合Dao
--------
这个过程中主要是编写mapper文件，spring-dao.xml和mybatis-config.xml这三个文件，.实现对数据库的相关操作。<br><br>
mapper文件是我们根据对应的Dao接口开发的实现操纵数据库的文件,namespace属性一定要和对应的Dao接口关联。
```
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.me.dao.DepartmentDao">

    <select id="getAllDepartment" resultType="Department">

          SELECT
              *
          FROM
              department
    </select>

    <insert id="insert">

        INSERT INTO department (departmentname)
        VALUES
            (#{name})
    </insert>

    <delete id="deleteDepById">
        DELETE
        FROM
            department
        WHERE
            departmentid = #{id}

    </delete>

    <update id="updateDepById">

        UPDATE department
        SET departmentname = #{name}
        WHERE
            departmentid = #{id}
    </update>
    
</mapper>
```
mybatis-config.xml文件用来实现对Mybatis属性的一些配置。
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
    <!--配置全局属性-->
    <settings>
        <!--使用jdbc的getGeneratekeys获取自增主键值-->
        <setting name="useGeneratedKeys" value="true"/>
        <!--使用列别名替换列名　　默认值为true
        select name as title(实体中的属性名是title) form table;
        开启后mybatis会自动帮我们把表中name的值赋到对应实体的title属性中
        -->
        <setting name="useColumnLabel" value="true"/>

        <!--开启自动映射  SQL的列名和entity属性名一致时，
            PARTIAL 只对没有嵌套结果集进行映射
          -->
        <setting name="autoMappingBehavior" value="PARTIAL"/>
    </settings>

</configuration>
```
spring-dao.xml文件用来完成Spring与Mybatis的整合，以及引入mapper文件。
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!--配置整合mybatis过程
    1.配置数据库相关参数-->
    <context:property-placeholder location="classpath:jdbc.properties"/>

    <!--2.数据库连接池-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <!--配置连接池属性-->
        <property name="driverClass" value="${jdbc.driver}" />
        <!-- 基本属性 url、user、password -->
        <property name="jdbcUrl" value="${jdbc.url}" />
        <property name="user" value="${jdbc.username}" />
        <property name="password" value="${jdbc.password}" />

        <!--c3p0私有属性-->
        <property name="maxPoolSize" value="30"/>
        <property name="minPoolSize" value="10"/>
        <!--关闭连接后不自动commit-->
        <property name="autoCommitOnClose" value="false"/>

        <!--获取连接超时时间-->
        <property name="checkoutTimeout" value="1000"/>
        <!--当获取连接失败重试次数-->
        <property name="acquireRetryAttempts" value="2"/>
    </bean>

    <!--约定大于配置-->
    <!--３.配置SqlSessionFactory对象-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--往下才是mybatis和spring真正整合的配置-->
        <!--注入数据库连接池-->
        <property name="dataSource" ref="dataSource"/>
        <!--配置mybatis全局配置文件:mybatis-config.xml-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <!--扫描entity包,使用别名,多个用;隔开-->
        <property name="typeAliasesPackage" value="com.me.entity"/>
        <!--扫描sql配置文件:mapper需要的xml文件-->
        <property name="mapperLocations" value="classpath:mapper/*.xml"/>
    </bean>

    <!--４:配置扫描Dao接口包,动态实现DAO接口,注入到spring容器-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!--注入SqlSessionFactory-->
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
        <!-- 给出需要扫描的Dao接口-->
        <property name="basePackage" value="com.me.dao"/>

    </bean>

</beans>
```
jdbc.properties文件
```
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/meeting?useUnicode=true&characterEncoding=utf8
jdbc.username=root
jdbc.password=root
```
<br>

2.整合Service
--------

这个步骤主要是编写spring-service.xml文件的编写，实现业务逻辑的编写。
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">

    <!--扫描service包下所有使用注解的类型-->
    <context:component-scan base-package="com.me.service"/>

    <!--配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">

        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!-- 配置基于注解的声明式事务-->
    <tx:annotation-driven transaction-manager="transactionManager"/>
    
</beans>
```

3.整合controller
--------
这个步骤主要是编写spring-web.xml文件的编写，实现与web页面的交互。
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd">


    <!--配置spring mvc-->
    <!--1,开启springmvc注解模式
    a.自动注册DefaultAnnotationHandlerMapping,AnnotationMethodHandlerAdapter
    b.默认提供一系列的功能:数据绑定，数字和日期的format@NumberFormat,@DateTimeFormat
    c:xml,json的默认读写支持-->
    <mvc:annotation-driven/>

    <!--2.静态资源默认servlet配置-->
    <!--
        1).加入对静态资源处理：js,gif,png
        2).允许使用 "/" 做整体映射
    -->
    <mvc:default-servlet-handler/>

    <!--3：配置JSP 显示ViewResolver-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

    <!--4:扫描web相关的controller-->
    <context:component-scan base-package="com.me.web"/>

    <!--5:配置拦截器 -->
    <mvc:interceptors>
        <!-- 多个拦截器，按顺序执行 -->
        <mvc:interceptor>
            <mvc:mapping path="/**"/> <!-- 拦截所有的url包括子url路径 -->
            <bean class="com.me.web.LoginInterceptor"/>
        </mvc:interceptor>
        <!-- 其他拦截器 -->
    </mvc:interceptors>

</beans>
```

<a id="2">SpringMVC的传值问题</a>
-----------

1.前端传到Controller
-----------

**方法1：通过HttpServletRequest**<br>
HttpServletRequest类是Servlet中的类型，代表了一个Servlet请求。无论Post还是Get请求，都能通过这种方式获取到。<br><br>

**方法2：通过使用路径变量**<br>

@RequestMapping中的{}中即为路径变量，该变量还需要在方法的参数值出现，并且标记@PathVariable。
通过URL匹配的方式既可以实现传值，这是REST风格的一种传值方式。
```
@Controller
public class MyTestController {
@RequestMapping("/print/{name}/{age}")
public String PrintInfo(@PathVariable String name, @PathVariable int age) {
System.out.println("name:" + name);
System.out.println("age:" + age);
return "testpage";
}
}
```

**方法3：参数名匹配的方式**<br>
```
@Controller
public class MyTestController {
@RequestMapping(value="/print")
public String PrintInfo(String name, int age) {
System.out.println("name:" +name);
System.out.println("age:" + age);
return "testpage";
}
}
```
或者
```
@Controller
public class MyTestController {
@RequestMapping(value="/print")
public String PrintInfo(@RequestParam("name") String name,@RequestParam("age") int age) {
System.out.println("name:" +name);
System.out.println("age:" + age);
return "testpage";
}
}
```
当请求传入的参数名字和controller中代码的名字一样的时候，两种方式都可以，区别在于使用了注解@RequestParam，可以设置一个默认值来处理到null值,或者允许传入null值。如@RequestParam(value="name", defaultValue="John")。但是如果请求中参数的名字和变量名不一样的时候，就只能使用@RequestParam注解。<br>

2.Controller传递到JSP
---------

**方法1：使用ModelAndView类**<br>
代码如下：
```
@RequestMapping("/hello")
public ModelAndView showMessage() {
ModelAndView mv = new ModelAndView("helloworld");
mv.addObject("userList", GetUserList());
return mv;
}
public List<User> GetUserList()
{
List<User> lst=new ArrayList<User>();
User user1=new User();
user1.setName("zhangsan");
user1.setAge(20);
lst.add(user1);
User user2=new User();
user2.setName("lisi");
user2.setAge(30);
lst.add(user2);
return lst;
}
```
JSP页面(helloworld.jsp)中：
```
<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring 4 MVC -HelloWorld</title>
</head>
<body>
<c:forEach items="${userList}" var="user">
${user.name} ${user.age}
<br />
</c:forEach>
</body>
</html>
```

**方法2：使用Model或者ModelMap**<br>

Model是一个接口，ModelMap实现了Model接口。该方法和ModelAndView方法相似，只是Model和View分开来了，通过返回一个String来找到View，Model是注入到Controller的一个参数，通过对它添加属性，在jsp端读取值。代码如下:
```
@Controller
public class HelloWorldController {
String message = "Welcome to Spring MVC!";
@RequestMapping("/hello")
public String showMessage(Model model) {
model.addAttribute("userList", GetUserList());
return "helloworld";
}
public List<User> GetUserList()
{
List<User> lst=new ArrayList<User>();
User user1=new User();
user1.setName("zhangsan");
user1.setAge(10);
lst.add(user1);
User user2=new User();
user2.setName("lisi");
user2.setAge(33);
lst.add(user2);
return lst;
}
}
```
JSP页面跟上面一样。

<br>方法3：使用HttpServletRequest 和 Session,然后setAttribute()，就和Servlet中一样
<br>更详细的内容，请参见[这篇文章](http://blog.51cto.com/cnn237111/1894506)<br>

3.Controller之间的传值
----------
1.**redirect 重定向**
```
@RequestMapping(value ="/one")
public String one(ModelMap model) {
    model.put("name","cv");
    return"redirect:/two?id=1";
}
       
@RequestMapping(value ="/two")
public String two(String id,String name,ModelMap model) {

    return"index";
}

```
请求/one后 请求重定向到/two时，通过model.put方法 ，two中name自动赋值为"cv",通过参数拼接id自动赋值为"1"。
故redirect跳转到另一个controller是通过model还是拼接url均可。但是参数名称需一致，否则无法传参赋值。

2.**forward 请求转发**
```
@RequestMapping(value ="/one")
public String one(HttpServletRequest request,ModelMapmodel) {
return"forward:/two";
}  
 @RequestMapping(value ="/two")
    public String two(String id,ModelMap model) {
    return"index;
}
```
forward跳转，是请求转发，参数自动跳转，所以当请求/one?id=1时，请求转发到/two接口时，参数自动带过来了，two中id自动赋值为"1"。<br>
参数名称需一致，否则无法传参赋值。

4.@SessionAttributes()
-----------
SessionAttributes可以将值保存在session会话中。
```
import *;

@Controller
@SessionAttributes({"loginUser","vc"})
@RequestMapping("/coolmeetting")
public class EmployeeController {

    @Autowired
    EmployeeService es;

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

    @RequestMapping(value = "/notifications")
    public String notifications(@ModelAttribute("loginUser")Employee loginUser,Model model){

        int loginEmpId = loginUser.getEmployeeid();
        List<MeetingDto> mt7 = meetingService.getMeeting7Days(loginEmpId);
        List<MeetingDto> ct = meetingService.getCanceledMeeting(loginEmpId);

        model.addAttribute("mt7",mt7);
        model.addAttribute("cm",ct);

        return "notifications";
    }
 }
 ```
 **注意：**Spring允许有选择地指定 ModelMap中的哪些属性需要转存到 session中，以便下一个请求属对应的 ModelMap的属性列表中还能访问到这些属性。这一功能是通过类定义处标注 @SessionAttributes注解来实现的。@SessionAttributes只能声明在类上，而不能声明在方法上。@ModelAttribute跟@SessionAttributes配合在一起用。可以将ModelMap中属性的值通过该注解自动赋给指定变量。<br>


<a id="3">SpringMVC的拦截器</a>
-----------
SpringMVC的处理器拦截器类似于Servlet 开发中的过滤器Filter，用于对处理器进行预处理和后处理。<br>

1. **SpringMVC拦截器的定义**
在SpringMVC中，定义拦截器要实现HandlerInterceptor接口，并实现该接口中提供的三个方法，如下：
```
public class LoginInterceptor implements HandlerInterceptor {

    /*
     * preHandle方法：进入Handler方法之前执行。可以用于身份认证、身份授权。
     *               比如如果认证没有通过表示用户没有登陆，需要此方法拦截不再往下执行
     *                （return   false），否则就放行（return true）。
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object o) throws Exception {

        String url = request.getRequestURI();
        if(url.endsWith("login"))
            return true;

        HttpSession session = request.getSession();
        //从session中取出用户身份信息
        String username = (String) session.getAttribute("username");
        if(username != null) {
            return true;
        }
        System.out.println(url);

        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);

        return false;
    }

    /*
     * postHandle方法：进入Handler方法之后，返回ModelAndView之前执行。
     *                 应用场景：从modelAndView出发：将公用的模型数据（比如菜单导航之类的）。
     *                在这里传到视图，也可以在这里同一指定视图。
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }
    
    /*
     *afterCompletion方法：执行Handler完成之后执行。应用场景：统一异常处理，统一日志处理等。
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {

    }
}

```
2.**配置拦截器**
```
    <!--5:配置拦截器 -->
    <mvc:interceptors>
        <!-- 多个拦截器，按顺序执行 -->
        <mvc:interceptor>
            <mvc:mapping path="/**"/> <!-- 拦截所有的url包括子url路径 -->
            <bean class="com.me.web.LoginInterceptor"/>
        </mvc:interceptor>
        <!-- 其他拦截器 -->
    </mvc:interceptors>
```

3.**拦截器链**

配置三个拦截器仿照上面的HandlerInterceptor1，HandlerInterceptor2和HandlerInterceptor3，配置是按照上面这个配置。来测试一下三个拦截器的执行情况，并做相关总结。
```
//测试拦截器1
public class HandlerInterceptor1 implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {

        System.out.println("HandlerInterceptor1....preHandle");

        //false表示拦截，不向下执行；true表示放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        System.out.println("HandlerInterceptor1....postHandle");

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        System.out.println("HandlerInterceptor1....afterCompletion");
    }
}
//HandlerInterceptor2和HandlerInterceptor3类似
```
```
<!-- 配置拦截器 -->
<mvc:interceptors>
    <!-- 多个拦截器，按顺序执行 -->        
    <mvc:interceptor>
        <mvc:mapping path="/**"/> <!-- 表示拦截所有的url包括子url路径 -->
        <bean class="com.me.web.HandlerInterceptor1"/>
    </mvc:interceptor>
    <mvc:interceptor>
        <mvc:mapping path="/**"/>
        <bean class="com.me.web.HandlerInterceptor2"/>
    </mvc:interceptor>
    <mvc:interceptor>
        <mvc:mapping path="/**"/>
        <bean class="com.me.web.HandlerInterceptor3"/>
    </mvc:interceptor>
</mvc:interceptors>
```

**情况1：三个拦截器都放行**<br>
将三个拦截器的preHandle方法中返回值都改成true，测试一下拦截器的执行顺序，测试结果如下：
```
HandlerInterceptor1….preHandle 
HandlerInterceptor2….preHandle 
HandlerInterceptor3….preHandle

HandlerInterceptor3….postHandle 
HandlerInterceptor2….postHandle 
HandlerInterceptor1….postHandle

HandlerInterceptor3….afterCompletion 
HandlerInterceptor2….afterCompletion 
HandlerInterceptor1….afterCompletion
```
**结论：**当所有拦截器都放行的时候，preHandle方法是按照配置的顺序执的；而另外两个方法按照配置的顺序逆向执行的。类似设计模式中的责任链模式.<br><br>
**情况2：有一个拦截器不放行**<br>
将第三个拦截器的preHandle方法中返回值改成false，前两个还是true，来测试一下拦截器的执行顺序，测试结果如下：
```
HandlerInterceptor1….preHandle 
HandlerInterceptor2….preHandle 
HandlerInterceptor3….preHandle

HandlerInterceptor2….afterCompletion 
HandlerInterceptor1….afterCompletion
```
- 由于拦截器1和2放行，所以拦截器3的preHandle才能执行。也就是说前面的拦截器放行，后面的拦截器才能执行preHandle.
- 拦截器3不放行，所以其另外两个方法没有被执行。即如果某个拦截器不放行，那么它的另外两个方法就不会背执行。 
- 只要有一个拦截器不放行，所有拦截器的postHandle方法都不会执行，但是只要执行过preHandle并且放行的，就会执行afterCompletion方法。
<br><br>
**情况3：三个拦截器都不放行**<br>
运行结果：
```
HandlerInterceptor1….preHandle
```
**结论：** 只执行了第一个拦截器的preHandle方法，因为都不放行，所以没有一个执行postHandle方法和afterCompletion方法。<br><br>



<br>更详细的内容，请参见[这篇文章](https://blog.csdn.net/eson_15/article/details/51749880)<br>
