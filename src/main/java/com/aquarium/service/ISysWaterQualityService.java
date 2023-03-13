package com.aquarium.service;

import com.aquarium.pojo.SysWaterQuality;
import com.aquarium.response.ResponseVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tt-Tang
 * @since 2023-03-04
 */
public interface ISysWaterQualityService extends IService<SysWaterQuality> {

    /**
     * 根据场馆 ID 查询水质信息
     *
     * @param page
     * @param limit
     * @param name
     * @param venueId
     * @param date
     * @return
     */
    ResponseVo listWaterData(long page, long limit, String name, int venueId, String date);

    /**
     * 生成数据
     *
     * @return
     */
    ResponseVo createOne();
}
