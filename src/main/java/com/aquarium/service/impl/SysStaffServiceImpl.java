package com.aquarium.service.impl;

import com.aquarium.mapper.SysStaffMapper;
import com.aquarium.mapper.SysVenueMapper;
import com.aquarium.pojo.SysStaff;
import com.aquarium.pojo.SysVenue;
import com.aquarium.response.ResponseVo;
import com.aquarium.service.ISysStaffService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class SysStaffServiceImpl extends ServiceImpl<SysStaffMapper, SysStaff> implements ISysStaffService {

    @Resource
    private SysStaffMapper staffMapper;

    @Resource
    private SysVenueMapper venueMapper;

    @Override
    public ResponseVo createOrUpdate(SysStaff sysStaff) {
        // 若更改归属实验室，级联更新实验室管理人员
        if (sysStaff.getVenueId() != null) {
            SysVenue venue = new SysVenue();
            // 设置场馆管理人员id和姓名
            venue.setVenueId(sysStaff.getVenueId());
            venue.setStaffId(sysStaff.getStaffId());
            venue.setStaffName(sysStaff.getName());
            if (venueMapper.updateById(venue) == 0) {
                return ResponseVo.exp();
            }
        }
        // 直接插入
        if (sysStaff.getStaffId() == null) {
            staffMapper.insert(sysStaff);
            return ResponseVo.success();
        }
        // 最后都要更新人员信息
        if (staffMapper.updateById(sysStaff) > 0) {
            return ResponseVo.success();
        }
        return ResponseVo.exp();
    }

    @Override
    public ResponseVo listStaff(long page, long limit, String name) {
        // 分页
        Page<SysStaff> staffPage = new Page<>();
        // 当前页面
        staffPage.setCurrent(page);
        //页面大小
        staffPage.setSize(limit);
        LambdaQueryWrapper<SysStaff> wrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(name)) {
            // 根据人员姓名查询
            wrapper.like(SysStaff::getName, name);
        }
        // 顺序排列
        wrapper.orderByAsc(SysStaff::getStaffId);
        Page<SysStaff> selectPage = staffMapper.selectPage(staffPage, wrapper);
        return ResponseVo.success()
                .data("items", selectPage.getRecords())
                .data("totalCount", selectPage.getTotal())
                .data("pageNo", page);
    }

    @Override
    public ResponseVo add(SysStaff sysStaff) {
        // 处理新增人员时设置管理的场馆
        if (sysStaff.getVenueId() != null) {
            // 查出传入场馆的信息
            SysVenue venue = venueMapper.selectById(sysStaff.getVenueId());
            // 场馆有人管理，且不是此人员，则冲突
            if (venue.getStaffId() != null) {
                return ResponseVo.exp();
            }
            // 场馆没人管理，则将此人设为管理员
            sysStaff.setVenueName(venue.getName());
            // 插入人员信息后更新场馆表中信息
            if (staffMapper.insert(sysStaff) > 0) {
                log.info("staff插入---id: {}", sysStaff.getStaffId());
                venue.setStaffId(sysStaff.getStaffId());
                venue.setStaffName(sysStaff.getName());
                // 更新场馆表
                if (venueMapper.updateById(venue) > 0) {
                    log.info("venue更新---id: {}", venue.getVenueId());
                    return ResponseVo.success();
                }
                return ResponseVo.exp();
            }
        }
        // 新增人员时不设置管理场所，则直接新增
        if (staffMapper.insert(sysStaff) > 0) {
            return ResponseVo.success();
        }
        return ResponseVo.exp();
    }

    @Transactional(rollbackFor = {})
    @Override
    public ResponseVo update(SysStaff sysStaff) {
        // 处理更新人员时更新管理的场馆
        if (sysStaff.getVenueId() != null) {
            // 查出传入场馆的信息
            SysVenue venue = venueMapper.selectById(sysStaff.getVenueId());
            // 如果该场馆已经有人管理，且管理员与此人员不相同，则冲突
            if (venue.getStaffId() != null && !venue.getStaffId().equals(sysStaff.getStaffId())) {
                return ResponseVo.exp();
            }
            // 如果场馆没人管理，则将此人设为管理员
            if (venue.getStaffId() == null) {
                // 设置人员和场馆表中信息
                sysStaff.setVenueName(venue.getName());
                venue.setStaffId(sysStaff.getStaffId());
                venue.setStaffName(sysStaff.getName());
                // 更新人员和场馆表
                if (staffMapper.updateById(sysStaff) > 0) {
                    if (venueMapper.updateById(venue) > 0) {
                        return ResponseVo.success();
                    }
                }
                return ResponseVo.exp();
            }
            // 场馆有人管理且与此人一致，则只更新人员信息
            if (staffMapper.updateById(sysStaff) > 0) {
                return ResponseVo.success();
            }
        }
        // 更新人员时传入的场馆id为空，则可能为将管理的场馆置空，或原本就为空且不更新
        // 查出此人信息
        SysStaff staffInfo = staffMapper.selectById(sysStaff);
        // 若原本有管理的场馆,置空管理和被管理的信息
        if (staffInfo.getVenueId() != null) {
            SysVenue venue = new SysVenue();
            // 需要new新对象，我也不知道为什么
            SysStaff staff = new SysStaff();
            venue.setVenueId(staffInfo.getVenueId());
            LambdaUpdateWrapper<SysStaff> staffUpdateWrapper = Wrappers.lambdaUpdate();
            LambdaUpdateWrapper<SysVenue> venueUpdateWrapper = Wrappers.lambdaUpdate();
            // Mybatis-Plus 新方法置空，直接置空会报错
            staffUpdateWrapper.set(SysStaff::getVenueId, null);
            staffUpdateWrapper.set(SysStaff::getVenueName, null);
            venueUpdateWrapper.set(SysVenue::getStaffId, null);
            venueUpdateWrapper.set(SysVenue::getStaffName, null);
            // 更新对应人员和场馆中信息
            if (staffMapper.update(staff, staffUpdateWrapper) > 0 && venueMapper.update(venue, venueUpdateWrapper) > 0) {
                return ResponseVo.success();
            }
        }
        // 原本就没有管理的场馆，直接更新
        if (staffMapper.updateById(sysStaff) > 0) {
            return ResponseVo.success();
        }
        return ResponseVo.exp();
    }
}
