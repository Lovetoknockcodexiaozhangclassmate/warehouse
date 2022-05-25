package com.zhang.warehouse.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.warehouse.entity.UserSheetEntity;
import com.zhang.warehouse.mapper.UserSheetMapper;
import com.zhang.warehouse.service.UserSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 1张伟严
 * @date 2021-12-09
 */
@Service
public class UserSheetServiceImpl implements UserSheetService {
    private final UserSheetMapper userSheetMapper;
    @Autowired
    public UserSheetServiceImpl(UserSheetMapper userSheetMapper){
        this.userSheetMapper = userSheetMapper;
    }


    @Override
    public Page<UserSheetEntity> selectPage(Integer pageNum, Integer pageSize, String search) {
        return userSheetMapper.selectPage(new Page<>(pageNum,pageSize), Wrappers.<UserSheetEntity>lambdaQuery().like(UserSheetEntity::getUserName,search));
    }

    @Override
    public List<UserSheetEntity> find() {
        return userSheetMapper.find();
    }
    @Override
    public UserSheetEntity findName(String userName) {
        return userSheetMapper.findName(userName);
    }

    @Override
    public int updateByName(String userName) {
        return userSheetMapper.updateByName(userName);
    }

    @Override
    public void updateById(UserSheetEntity userSheetEntity) {
        userSheetMapper.updateById(userSheetEntity);
    }

    @Override
    public int delete(String userId) {
        return userSheetMapper.delete(userId);
    }

    @Override
    public int save(UserSheetEntity userSheetEntity) {
        return userSheetMapper.save(userSheetEntity);
    }

    @Override
    public int register(UserSheetEntity userSheetEntity) {
        return userSheetMapper.register(userSheetEntity);
    }
}
