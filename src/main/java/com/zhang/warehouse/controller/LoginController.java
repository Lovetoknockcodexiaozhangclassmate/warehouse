package com.zhang.warehouse.controller;

import com.zhang.warehouse.code.ResponseData;
import com.zhang.warehouse.entity.UserSheetEntity;
import com.zhang.warehouse.service.impl.UserSheetServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author 1张伟严
 * @date 2021-12-09
 */
@RestController
@RequestMapping("/user")
@Api(value = "测试接口", tags = "用户登录接口", description = "用户测试接口")
public class LoginController {
    private final UserSheetServiceImpl userSheetService;
    @Autowired
    public LoginController(UserSheetServiceImpl userSheetService) {
        this.userSheetService = userSheetService;
    }


    @PostMapping("/login")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName",value = "用户登录的账号"),
                        @ApiImplicitParam(name = "userPassword",value = "用户登录的密码")})
    @ApiOperation(value = "登录方法",notes = "用户登录")
    public ResponseData login(@RequestBody UserSheetEntity userSheetEntity){
        if("".equals(userSheetEntity.getUserName()) || userSheetEntity.getUserName() == null){
            return ResponseData.error("账号不能为空","");
        }else if("".equals(userSheetEntity.getUserPassword()) || userSheetEntity.getUserPassword() == null){
            return ResponseData.error("密码不能为空","");
        }
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken toke = new UsernamePasswordToken(userSheetEntity.getUserName(),userSheetEntity.getUserPassword(),userSheetEntity.getRole());
        try{
            //执行登录方法，如果没有异常就登录成功了
            subject.login(toke);
            UserSheetEntity user = (UserSheetEntity) subject.getPrincipal();
            return ResponseData.success("登录成功",user);
        }catch (UnknownAccountException e){
            //用户名不存在

            return ResponseData.loginError("账号不存在","");
        }catch (IncorrectCredentialsException e){
            //密码不存在

            return ResponseData.loginError("密码错误","");
        }
    }


    @PostMapping("/register")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName",value = "注册用户账号"),
                        @ApiImplicitParam(name = "userPassword",value = "注册用户密码")})
    @ApiOperation(value = "注册方法",notes = "用户注册")
    public ResponseData register(@RequestBody UserSheetEntity userSheetEntity){
        if("".equals(userSheetEntity.getUserName()) || userSheetEntity.getUserName() == null){
            return ResponseData.error("账号不能为空","");
        }else if("".equals(userSheetEntity.getUserPassword()) || userSheetEntity.getUserPassword() == null){
            return ResponseData.error("密码不能为空","");
        }
        List<UserSheetEntity> list = userSheetService.find();
        boolean fole = false;
        for (UserSheetEntity us:list) {
            if (userSheetEntity.getUserName().equals(us.getUserName())) {
                fole = true;
                break;
            }
        }
        if(fole){
            return ResponseData.error("账号已存在","");
        }else{
            int i = userSheetService.register(userSheetEntity);
            if(i > 0){
                return ResponseData.success("注册成功","");
            }else{
                return ResponseData.error("注册失败","");
            }
        }
    }
}
