package com.aquarium.service;

import com.aquarium.pojo.SysVenue;
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
public interface ISysVenueService extends IService<SysVenue> {

    /**
     * 分页查询场馆
     *
     * @param page
     * @param limit
     * @param name
     * @return
     */
    ResponseVo listVenue(long page, long limit, String name);

    /**
     * 新增或更新场馆
     *
     * @param venue
     * @return
     */
    ResponseVo addOrUpdate(SysVenue venue);
}
