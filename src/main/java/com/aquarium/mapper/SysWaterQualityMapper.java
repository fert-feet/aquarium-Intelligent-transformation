package com.aquarium.mapper;

import com.aquarium.pojo.SysWaterQuality;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tt-Tang
 * @since 2023-03-04
 */
public interface SysWaterQualityMapper extends BaseMapper<SysWaterQuality> {

    /**
     * 查找场馆水质数据ID
     *
     * @param venueId
     * @return
     */
    List<Integer> findDataIdsByVenueId(int venueId);
}
