package com.aquarium.service.impl;

import com.aquarium.mapper.SysStaffMapper;
import com.aquarium.mapper.SysVenueMapper;
import com.aquarium.pojo.SysStaff;
import com.aquarium.pojo.SysVenue;
import com.aquarium.response.ResponseVo;
import com.aquarium.service.ISysVenueService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SysVenueServiceImpl extends ServiceImpl<SysVenueMapper, SysVenue> implements ISysVenueService {

    @Resource
    private SysVenueMapper venueMapper;

    @Resource
    private SysStaffMapper staffMapper;

    @Override
    public ResponseVo listVenue(long page, long limit, String name) {
        // 分页
        Page<SysVenue> venuePage = new Page<>();
        // 当前页面
        venuePage.setCurrent(page);
        //页面大小
        venuePage.setSize(limit);
        LambdaQueryWrapper<SysVenue> wrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(name)) {
            // 根据场馆名称查询
            wrapper.like(SysVenue::getName, name);
        }
        // 顺序排列
        wrapper.orderByAsc(SysVenue::getVenueId);
        Page<SysVenue> selectPage = venueMapper.selectPage(venuePage, wrapper);
        return ResponseVo.success().data("items", selectPage.getRecords()).data("total", selectPage.getTotal());
    }

    @Override
    public ResponseVo addOrUpdate(SysVenue venue) {
        // 设置管理员操作
        if (venue.getStaffId() != null) {
            SysStaff staff = new SysStaff();
            staff.setStaffId(venue.getStaffId());
            staff.setVenueId(venue.getVenueId());
            // 同步更新人员对应场馆
            if (staffMapper.updateById(staff) == 0) {
                return ResponseVo.exp();
            }
        }
        // 直接新增操作
        if (venue.getVenueId() == null) {
            venueMapper.insert(venue);
            return ResponseVo.success();
        }
        // 更新场馆信息
        if (venueMapper.updateById(venue) > 0) {
            return ResponseVo.success();
        }
        return ResponseVo.exp();
    }
}
