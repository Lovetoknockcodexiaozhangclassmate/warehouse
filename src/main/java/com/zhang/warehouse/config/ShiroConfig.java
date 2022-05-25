package com.zhang.warehouse.config;

import com.zhang.warehouse.shiro.UserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 1张伟严
 */
@Configuration
public class ShiroConfig {

    /**
     * 先经过token过滤器，如果检测到请求头存在 token，则用 token 去 login，接着走 Realm 去验证
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /*
            anon : 无需认证就可以访问
            authc : 必须认证了才能访问
            user : 必须拥有 记住我 功能才能使用
            perms : 拥有对某个资源的权限才能访问
            role : 拥有某个角色权限才能访问
         */
        //拦截
        //Map<String,String> filterMap = new LinkedHashMap<>();
        //授权页面，没有授权的情况下会跳转到未授权页面
        //filterMap.put("/user","perms[管理员]");
        //拦截请求
        //filterMap.put("/**","authc");
        //filterMap.put("/static/dist/index.html","anon");
        //shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        //设置登录的请求
        //shiroFilterFactoryBean.setLoginUrl("/static/dist/index.html");
        //未授权页面
        //shiroFilterFactoryBean.setUnauthorizedUrl("/noauth");
        return shiroFilterFactoryBean;
    }

    @Bean(name="securityManager")
    public  DefaultWebSecurityManager getSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    @Bean(name="userRealm")
    public UserRealm userRealm(){
        return new UserRealm();
    }
}
