package com.happy.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class IniReamlTest {

    @Test
    public void testAuthentication(){
        //1：创建SecurityManager环境
        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(iniRealm);

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
        subject.checkPermission("user:delete");  //authorizer
    }
}
