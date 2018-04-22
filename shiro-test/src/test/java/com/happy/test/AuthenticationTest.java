package com.happy.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addAccountRealm(){
        simpleAccountRealm.addAccount("happy-pp","pp-pp","admin","user");
    }

    @Test
    public void testAuthentication(){
        //1：创建SecurityManager环境
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(simpleAccountRealm);

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

    }



}
