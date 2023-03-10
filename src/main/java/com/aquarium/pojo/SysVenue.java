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
 * @since 2023-03-04
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
     * 场馆对应设备Id
     */
    private Integer deviceId;

    /**
     * 场馆水质最大阈值
     */
    private Integer maxWaterThreshold;

    /**
     * 场馆水质最小阈值
     */
    private Integer miniWaterThreshold;

    /**
     * 是否有管理人员
     */
    private Byte hasAdmin;
}
