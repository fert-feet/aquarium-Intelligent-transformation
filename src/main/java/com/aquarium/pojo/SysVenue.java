package com.aquarium.pojo;

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
    private Integer venueId;

    /**
     * 场馆名称
     */
    private String name;

    /**
     * 是否已经有设备
     */
    private Byte isHasDevice;

    /**
     * 场馆水质阈值
     */
    private Integer waterThreshold;

    /**
     * 管理人员id
     */
    private Integer staffId;
}
