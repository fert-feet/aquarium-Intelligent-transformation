package com.aquarium.service;

import com.aquarium.pojo.SysDevice;
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
public interface ISysDeviceService extends IService<SysDevice> {

    /**
     * 设备分页查询
     *
     * @param page
     * @param limit
     * @param name
     * @param venueName
     * @return
     */
    ResponseVo listDevice(long page, long limit, String name, int venueId);

    /**
     * 更新设备所属场馆
     *
     * @param device
     * @return
     */
    ResponseVo updateBelongsVenue(SysDevice device);

    /**
     * 删除
     *
     * @param deviceId
     * @return
     */
    ResponseVo delete(int deviceId);
}
