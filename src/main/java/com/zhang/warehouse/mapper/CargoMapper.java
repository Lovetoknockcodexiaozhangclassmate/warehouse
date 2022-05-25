package com.zhang.warehouse.mapper;

import com.zhang.warehouse.entity.CargoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhang
 */
@Mapper
public interface CargoMapper {
    /**
     * 查询所有数据方法
     * @return 返回到集合中
     */
    List<CargoEntity> find();
}
