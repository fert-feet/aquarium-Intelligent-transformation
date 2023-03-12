package com.aquarium.mapper;

import com.aquarium.pojo.SysDevice;
import com.aquarium.pojo.SysVenue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tt-Tang
 * @since 2023-03-04
 */
public interface SysDeviceMapper extends BaseMapper<SysDevice> {

    /**
     * 查找所属场馆
     *
     * @param deviceId
     * @return
     */
    SysVenue findBelongsVenue(@Param("deviceId") Integer deviceId);
}
