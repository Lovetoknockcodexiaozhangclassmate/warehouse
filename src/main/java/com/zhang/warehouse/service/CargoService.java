package com.zhang.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.warehouse.entity.CargoEntity;
import com.zhang.warehouse.entity.UserSheetEntity;

import java.util.List;

/**
 * @author zhang
 */
public interface CargoService {
    /**
     * 全部查询方法
     * @return 查询的数据返回到list集合中
     */
    List<CargoEntity> find();

    /**
     * 分页查询所有方法
     * @return 查询到的所有数据存储在list集合中
     * @param pageNum :
     * @param pageSize :
     * @param search :
     */
    Page<CargoEntity> selectPage(Integer pageNum, Integer pageSize, String search);
}
