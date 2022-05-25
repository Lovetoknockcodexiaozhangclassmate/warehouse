package com.zhang.warehouse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.warehouse.WarehouseApplication;
import com.zhang.warehouse.code.ResponseData;
import com.zhang.warehouse.entity.UserSheetEntity;
import com.zhang.warehouse.service.impl.UserSheetServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 1zhang
 */
@RestController
@RequestMapping("/user")
public class UserSheetController {
    private final UserSheetServiceImpl userSheetService;
    @Autowired
    public UserSheetController(UserSheetServiceImpl userSheetService) {
        this.userSheetService = userSheetService;
    }
    protected static final Logger logger = LoggerFactory.getLogger(WarehouseApplication.class);

    @PutMapping("/update")
    public ResponseData update(@RequestBody UserSheetEntity userSheetEntity){
        if("".equals(userSheetEntity.getUserName()) || userSheetEntity.getUserName() == null){
            return ResponseData.error("账号不能为空","");
        }else if("".equals(userSheetEntity.getUserPassword()) || userSheetEntity.getUserPassword() == null){
            return ResponseData.error("密码不能为空","");
        }else if("".equals(userSheetEntity.getRole()) || userSheetEntity.getRole() == null){
            return ResponseData.error("权限不能为空","");
        }
        if("admin".equals(userSheetEntity.getUserName())){
            return ResponseData.error("修改失败，此账号不能修改","");
        }else {
            userSheetService.updateById(userSheetEntity);
            return ResponseData.success("修改成功", "");
        }
    }

    @PostMapping("/save")
    public ResponseData save(@RequestBody UserSheetEntity userSheetEntity){
        if("".equals(userSheetEntity.getUserName()) || userSheetEntity.getUserName() == null){
            return ResponseData.error("账号不能为空","");
        }else if("".equals(userSheetEntity.getUserPassword()) || userSheetEntity.getUserPassword() == null){
            return ResponseData.error("密码不能为空","");
        }else if("".equals(userSheetEntity.getRole()) || userSheetEntity.getRole() == null){
            return ResponseData.error("权限不能为空","");
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
            return ResponseData.error("添加失败，账号已存在","");
        }else{
            int i = userSheetService.save(userSheetEntity);
            if(i > 0){
                return ResponseData.success("添加成功","");
            }else{
                return ResponseData.error("添加失败","");
            }
        }
    }

    @GetMapping("/findPage")
    public ResponseData findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 @RequestParam(defaultValue = "") String search){
        Page<UserSheetEntity> userSheetEntityPage = userSheetService.selectPage(pageNum, pageSize, search);
        return ResponseData.success("查询成功",userSheetEntityPage);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseData delete(@PathVariable String userId){
        List<UserSheetEntity> list = userSheetService.find();
        boolean folt = false;
        for (UserSheetEntity us:list) {
            if("admin".equals(us.getUserName()) && userId.equals(us.getUserId())){
                folt = true;
                break;
            }
        }
        if(folt){
            return ResponseData.error("删除失败，该账号不能删除","");
        }else{
            int i = userSheetService.delete(userId);
            if(i > 0){
                return ResponseData.success("删除成功","");
            }else{
                return ResponseData.error("删除失败","");
            }
        }
    }

    @PutMapping("/updateByName")
    public ResponseData updateByName(@RequestBody String userName){
        logger.info(userName);
        if("admin".equals(userName)) {
            return ResponseData.error("此账号不允许重置密码", "");
        }else{
            int bool = userSheetService.updateByName(userName);
            if(bool > 0){
                return ResponseData.success("重置密码成功","");
            }else{
                return ResponseData.error("重置密码失败","");
            }
        }
    }
}
