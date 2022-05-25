package com.zhang.warehouse.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @author 张伟严
 * @date 2021-12-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_sheet")
public class UserSheetEntity {
    @TableId
    @ExcelIgnore
    private String userId;

    @ExcelProperty(value = "账号",index = 0)
    private String userName;

    @ExcelProperty(value = "密码",index = 1)
    private String userPassword;

    @ExcelIgnore
    private Date createTime;

    @ExcelProperty(value = "用户权限",index = 2)
    private String role;
}
