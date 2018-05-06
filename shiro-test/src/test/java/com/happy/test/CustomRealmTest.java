package com.happy.test;

import com.happy.pp.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @Author: Administrator
 * @CreateDate: 21:39 2018/5/3
 */
public class CustomRealmTest {


    @Test
    public void testAuthentication(){

        CustomRealm customRealm = new CustomRealm();

        //1：创建SecurityManager环境
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(customRealm);

        //加密
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        customRealm.setCredentialsMatcher(matcher);

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
