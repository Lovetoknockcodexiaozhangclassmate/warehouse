package com.zhang.warehouse.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.warehouse.entity.CargoEntity;
import com.zhang.warehouse.service.CargoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhang
 */
@Service
public class CargoServiceImpl implements CargoService {
    @Override
    public List<CargoEntity> find() {
        return null;
    }

    @Override
    public Page<CargoEntity> selectPage(Integer pageNum, Integer pageSize, String search) {
        return null;
    }
}
