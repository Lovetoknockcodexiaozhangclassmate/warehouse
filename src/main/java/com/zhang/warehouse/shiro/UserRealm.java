package com.zhang.warehouse.shiro;

import com.zhang.warehouse.WarehouseApplication;
import com.zhang.warehouse.entity.UserSheetEntity;
import com.zhang.warehouse.service.UserSheetService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 1zhang
 */
public class UserRealm extends AuthorizingRealm {
    protected static final Logger logger = LoggerFactory.getLogger(WarehouseApplication.class);

    @Autowired
    UserSheetService userSheetService;
    /**
     * 授权
     * @param principalCollection :
     * @return :
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("执行了 =》 授权doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        UserSheetEntity currentUser = (UserSheetEntity) subject.getPrincipal();
        info.addStringPermission(currentUser.getRole());
        return info;
    }

    /**
     * 认证
     * @param authenticationToken :
     * @return :
     * @throws AuthenticationException :
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("执行了 =》 认证doGetAuthenticationInfo");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        UserSheetEntity userSheetEntity = userSheetService.findName(token.getUsername());
        if(userSheetEntity == null){
            logger.info("没有这个账号");
            return null;
        }

        //密码认证 shiro做
        return new SimpleAuthenticationInfo(userSheetEntity,userSheetEntity.getUserPassword(),"");
    }
}
