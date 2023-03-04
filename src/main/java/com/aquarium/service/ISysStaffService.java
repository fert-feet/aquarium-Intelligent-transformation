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
     * 新增或更新人员
     *
     * @param sysStaff
     * @return
     */
    ResponseVo createOrUpdate(SysStaff sysStaff);
}
