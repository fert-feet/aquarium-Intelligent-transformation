package com.aquarium.service.impl;

import com.aquarium.mapper.SysAdministratorMapper;
import com.aquarium.pojo.SysAdministrator;
import com.aquarium.response.ResponseCode;
import com.aquarium.response.ResponseVo;
import com.aquarium.service.ISysAdministratorService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class SysAdministratorServiceImpl extends ServiceImpl<SysAdministratorMapper, SysAdministrator> implements ISysAdministratorService {

    @Resource
    private SysAdministratorMapper administratorMapper;

    @Override
    public ResponseVo login(SysAdministrator admin) {
        LambdaQueryWrapper<SysAdministrator> wrapper = Wrappers.lambdaQuery();
        // 根据账号密码查询是否存在这一条数据，存在则为登录成功
        wrapper.eq(SysAdministrator::getAdminName, admin.getAdminName()).eq(SysAdministrator::getPassword, admin.getPassword());
        SysAdministrator loginAdmin = administratorMapper.selectOne(wrapper);
        // 账号密码同时符合就为登录成功，否则直接返回错误，不提示是账号或密码错误
        if (loginAdmin != null) {
            return ResponseVo.success();
        }
        return ResponseVo.exp().status(ResponseCode.VALID_DATA);
    }
}
