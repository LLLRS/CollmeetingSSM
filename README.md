SSMCollmeeting
=============

写在前面
------------

对之前的用JavaBean+Servlet+Jsp的collmeting项目用SSM+Maven进行了重写，重写过程中遇到了很多问题，解决这些问题的过程中，进一步加深了对SSM的理解。<br>
- [SSM的整合流程](#1)
- [传值问题](#2)
-  [SpringMVC的拦截器](#3)
-  [JUnit单元测试](#4)

<a id="1">SSM的整合流程</a>
-----------
完成Spring、Spring MVC以及Mybatis整合工作，其实不难，就是将这三个框架的配置文件提取出来放在一个项目中，使得Spring可以统一管理资源。整个过程分为三步。<br><br>
第一步:整合dao(即mapper)，完成Spring与Mybatis的整合。<br>
第二步:整合service，Spring管理service接口，service中可以调用Spring容器中的dao(mapper)。<br>
第三步:整合controller，Spring管理controller接口，在controller调用service。<br>
<br><br><br>
**目录结构**<br>
[]()
<br><br>
可以看出，利用Maven创建的项目将所有的配置文件都放在了resources目录下，包含mapper文件，spring-dao.xml,spring-service.xml,spring-web.xml,以及mybatis-config.xml,jdbc.properties(存放数据库的配置文件).
<br><br>
1.整合Dao
--------
这个过程中主要是编写mapper文件，spring-dao.xml和mybatis-config.xml这三个文件。<br><br>
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

<a id="2">传值问题</a>
-----------



<a id="3">SpringMVC的拦截器</a>
-----------

<a id="4">JUnit单元测试</a>
-----------
