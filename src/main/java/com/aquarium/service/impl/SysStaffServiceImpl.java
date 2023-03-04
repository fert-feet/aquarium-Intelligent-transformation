package com.aquarium.service.impl;

import com.aquarium.mapper.SysStaffMapper;
import com.aquarium.mapper.SysVenueMapper;
import com.aquarium.pojo.SysStaff;
import com.aquarium.pojo.SysVenue;
import com.aquarium.response.ResponseVo;
import com.aquarium.service.ISysStaffService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tt-Tang
 * @since 2023-03-04
 */
@Service
public class SysStaffServiceImpl extends ServiceImpl<SysStaffMapper, SysStaff> implements ISysStaffService {

    @Resource
    private SysStaffMapper staffMapper;

    @Resource
    private SysVenueMapper venueMapper;

    @Override
    public ResponseVo createOrUpdate(SysStaff sysStaff) {
        // 直接插入
        if (sysStaff.getStaffId() == null) {
            staffMapper.insert(sysStaff);
            return ResponseVo.success();
        }
        // 若更改归属实验室，级联更新实验室管理人员
        if (sysStaff.getVenueId() != null) {
            SysVenue sysVenue = venueMapper.selectById(sysStaff.getVenueId());
            sysVenue.setStaffId(sysStaff.getStaffId());
            venueMapper.updateById(sysVenue);
        }
        // 最后都要更新人员信息
        if (staffMapper.updateById(sysStaff) > 0) {
            return ResponseVo.success();
        }
        return ResponseVo.exp();
    }

    @Override
    public ResponseVo listPerson(long page, long limit, String name) {
        // 分页
        Page<SysStaff> labPage = new Page<>();
        // 当前页面
        labPage.setCurrent(page);
        //页面大小
        labPage.setSize(limit);
        LambdaQueryWrapper<SysStaff> wrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(name)) {
            // 根据人员姓名查询
            wrapper.like(SysStaff::getName, name);
        }
        // 顺序排列
        wrapper.orderByAsc(SysStaff::getStaffId);
        Page<SysStaff> selectPage = staffMapper.selectPage(labPage, wrapper);
        return ResponseVo.success().data("items", selectPage.getRecords()).data("total", selectPage.getTotal());
    }
}
