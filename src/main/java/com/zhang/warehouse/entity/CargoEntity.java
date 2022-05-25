package com.zhang.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zhang
 */
@Data
@TableName("cargo_table")
public class CargoEntity {
    private int id;
    private String cargoName;
    private String cargoNumber;
    private int cargoAmount;
    private String imageUrl;
    private String cargoType;
}
