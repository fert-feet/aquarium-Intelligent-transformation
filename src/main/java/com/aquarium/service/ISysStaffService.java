package com.aquarium.service;

import com.aquarium.pojo.SysStaff;
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
public interface ISysStaffService extends IService<SysStaff> {

    /**
     * 分页查询人员
     *
     * @param page
     * @param limit
     * @param name
     * @return
     */
    ResponseVo listStaff(long page, long limit, String name);


}
