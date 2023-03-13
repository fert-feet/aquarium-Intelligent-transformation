package com.aquarium.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author tt-Tang
 * @since 2023-03-13
 */
@Getter
@Setter
@TableName("sys_venue")
public class SysVenue implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 场馆id
     */
    @TableId(value = "venue_id", type = IdType.AUTO)
    private Integer venueId;

    /**
     * 场馆名称
     */
    private String name;

    /**
     * 水温最大阈值
     */
    private Double maxWaterTemperature;

    /**
     * 水温最小阈值
     */
    private Double minWaterTemperature;

    /**
     * 有效磷最大阈值
     */
    private Double maxWaterAvailablePhosphorous;

    /**
     * 有效磷最小阈值
     */
    private Double minWaterAvailablePhosphorous;

    /**
     * 场馆水质最大阈值
     */
    private Double maxWaterSalt;

    /**
     * 水体盐分最大阈值
     */
    private Double minWaterSalt;

    /**
     * 水体盐分最大阈值
     */
    private Double maxWaterPh;

    /**
     * 场馆水质最小阈值
     */
    private Double minWaterPh;

    private Integer maxWaterThreshold;

    private Integer miniWaterThreshold;

    /**
     * 是否有管理员
     */
    private Byte hasAdmin;

    /**
     * 是否有设备绑定
     */
    private Byte hasDevice;
}
