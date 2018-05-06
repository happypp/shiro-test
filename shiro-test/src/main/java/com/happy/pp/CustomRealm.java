package com.happy.pp;

import com.mysql.jdbc.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Administrator
 * @CreateDate: 21:31 2018/5/3
 */
public class CustomRealm extends AuthorizingRealm {

    private Map<String,String> userMap = new HashMap<String, String>(16);

    {
        userMap.put("happy-pp","1370d627f9c00849c393f4881e12b708");
        super.setName("customRealm");
    }

    /**
     * @Author:Administrator
     * @params:[principalCollection]
     * @return: org.apache.shiro.authz.AuthorizationInfo  授权
     * @Date: 2018/5/3 21:31
    */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //从主体中获取用户名
        String userName = (String)principalCollection.getPrimaryPrincipal();
        Set<String> roles = getRolesByUserName(userName);
        Set<String> permissions = getPermissionsByUserName(userName);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * @Author:Administrator
     * @params:[userName]
     * @return: java.util.Set<java.lang.String> 获取对应角色的权限
     * @Date: 2018/5/3 21:46
    */
    private Set<String> getPermissionsByUserName(String userName) {
        Set<String> roles = new HashSet<String>();
        roles.add("user:delete");
        roles.add("admin:All");
        return roles;
    }

    /**
     * @Author:Administrator
     * @params:[userName]
     * @return: java.util.Set<java.lang.String> 获取对用的角色
     * @Date: 2018/5/3 21:44
    */
    private Set<String> getRolesByUserName(String userName) {
        Set<String> roles = new HashSet<String>();
        roles.add("user");
        roles.add("admin");
        return roles;
    }

    /**
     * @Author:Administrator
     * @params:[authenticationToken]
     * @return: org.apache.shiro.authc.AuthenticationInfo 认证
     * @Date: 2018/5/3 21:32
    */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //从主体传过来的用户名
        String userName = (String)authenticationToken.getPrincipal();
        if(StringUtils.isNullOrEmpty(userName)){
            return null;
        }
        //通过用户名到数据库中获取凭证
        String password = userMap.get(userName);
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userName,password,"customRealm");
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("happy"));
        return simpleAuthenticationInfo;
    }

    public static void main(String[] args) {
        //MD5加密  加盐
        Md5Hash md5Hash = new Md5Hash("pp-pp","happy");
        System.out.println(md5Hash.toString());
    }
}
