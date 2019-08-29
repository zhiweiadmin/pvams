package com.goodpower.pvams.shiro;

import com.goodpower.pvams.common.ResultEnums;
import com.goodpower.pvams.model.RolePermission;
import com.goodpower.pvams.model.User;
import com.goodpower.pvams.service.RolePermissionService;
import com.goodpower.pvams.service.UserService;
import com.goodpower.pvams.util.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.*;

public class CustomRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Lazy
    @Autowired
    UserService userService;

    @Lazy
    @Autowired
    RolePermissionService rolePermissionService;

    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("身份认证开始:"+authenticationToken);
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null || !JWTUtil.verify(token, username)) {
            throw new AuthenticationException(ResultEnums.AUTH_ERROR.getCode());
        }
        Date expireDate = JWTUtil.getExpiresAt(token);
        if(expireDate == null){
            throw new AuthenticationException(ResultEnums.AUTH_ERROR.getCode());
        }
        if( System.currentTimeMillis() > expireDate.getTime()){
            throw new AuthenticationException(ResultEnums.TOKEN_EXPIRE.getCode());
        }
        return new SimpleAuthenticationInfo(token, token, getName());
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("权限认证开始:");
        String username = JWTUtil.getUsername(principals.toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
        User user = userService.findUserByUsername(username);
        Set<String> permissionSet = new HashSet<>();
        Set<String> roleSet = new HashSet<>();
        //每个角色拥有默认的权限
        if(user.getRole() != null){
            List<RolePermission> permissions = rolePermissionService.getRolePermission(user.getRole());
            for(RolePermission permission : permissions){
                permissionSet.add(permission.getPermission());
            }
        }
        //需要将 role, permission 封装到 Set 作为 info.setRoles(), info.setStringPermissions() 的参数
        roleSet.add(user.getRole()+"");
        //设置该用户拥有的角色和权限
        info.setRoles(roleSet);
        info.setStringPermissions(permissionSet);
        return info;
    }

}
