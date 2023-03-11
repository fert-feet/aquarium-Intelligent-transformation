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
    ResponseVo listDevice(long page, long limit, String name, String venueName);

    /**
     * 更新或新增设备
     *
     * @param device
     * @return
     */
    ResponseVo addOrUpdate(SysDevice device);
}
