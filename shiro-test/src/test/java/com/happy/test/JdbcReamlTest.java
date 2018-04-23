package com.happy.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @Author: Administrator
 * @CreateDate: 22:30 2018/4/23
 */
public class JdbcReamlTest {

    DruidDataSource druidDataSource = new DruidDataSource();

    {
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/test");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("123456");
    }

    @Test
    public void testAuthentication(){
        //1：创建SecurityManager环境
        JdbcRealm jdbcRealm = new JdbcRealm(); //默认的sql，默认表名，源码。。。
        jdbcRealm.setDataSource(druidDataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);

        //自定义sql验证认证器
        String user_sql = "select password from users where username = ?";
        jdbcRealm.setAuthenticationQuery(user_sql);


        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(jdbcRealm);
        //2：主体提交验证请求
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("happy-pp","pp-pp");
        subject.login(usernamePasswordToken); //authenticator 认证器
        System.out.println("isAuthenticated: " + subject.isAuthenticated());
//        subject.logout();
//        System.out.println("isAuthenticated: " + subject.isAuthenticated());
        //检查是否有权限
        subject.checkRoles("admin","user"); //authorizer 授权器
        subject.checkPermission("user:select");  //authorizer
        subject.checkPermission("admin:ALL");  //authorizer
    }


}
