package com.zhang.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.warehouse.entity.UserSheetEntity;
import org.apache.catalina.User;

import java.util.List;

/**
 * @author 1张伟严
 * @date 2021-12-09
 */
public interface UserSheetService {
    /**
     * 分页查询所有方法
     * @return 查询到的所有数据存储在list集合中
     * @param pageNum :
     * @param pageSize :
     * @param search :
     */
    Page<UserSheetEntity> selectPage(Integer pageNum, Integer pageSize, String search);
    /**
     * 全部查询
     * @return 查询到的所有数据存储在list集合中
     */
    List<UserSheetEntity> find();

    /**
     * 通过账号查找
     * @return 查询到的所有数据存储在实体类中
     * @param userName :
     */
    UserSheetEntity findName(String userName);

    /**
     * 重置密码
     * @return 返回int类型用于接受是否修改成功
     * @param userName : 传递修改的值,通过userName修改
     */
    int updateByName(String userName);
    /**
     * 修改方法
     * @param userSheetEntity  : 传递修改的值,通过userId修改
     */
    void updateById(UserSheetEntity userSheetEntity);

    /**
     * 通过id删除方法
     * @param userId : 传递的Id值，通过这个删除
     * @return 返回int类型用于接受是否删除成功
     */
    int delete(String userId);

    /**
     * 添加方法
     * @param userSheetEntity : 传递添加的值
     * @return 返回int类型用于接受是否添加成功
     */
    int save(UserSheetEntity userSheetEntity);

    /**
     * 注册方法
     * @param userSheetEntity ：传递的值
     * @return ：返回是否添加成功
     */
    int register(UserSheetEntity userSheetEntity);
}
