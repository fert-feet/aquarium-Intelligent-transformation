package com.aquarium.service;

import com.aquarium.pojo.SysAdministrator;
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
public interface ISysAdministratorService extends IService<SysAdministrator> {

    /**
     * 登录
     *
     * @param admin
     * @return
     */
    ResponseVo login(SysAdministrator admin);
}
