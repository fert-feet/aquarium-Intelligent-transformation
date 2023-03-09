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
        // 直接新增操作
        if (venue.getVenueId() == null) {
            venueMapper.insert(venue);
            return ResponseVo.success();
        }
        // 设置管理员操作
        if (venue.getStaffId() != null) {
            SysStaff staff = new SysStaff();
            // 设置人员管理的实验室id和名称
            staff.setStaffId(venue.getStaffId());
            staff.setVenueName(venue.getName());
            staff.setVenueId(venue.getVenueId());
            // 同步更新人员对应场馆
            if (staffMapper.updateById(staff) == 0) {
                return ResponseVo.exp();
            }
        }
        // 更新场馆信息
        if (venueMapper.updateById(venue) > 0) {
            return ResponseVo.success();
        }
        return ResponseVo.exp();
    }

    @Transactional(rollbackFor = {})
    @Override
    public ResponseVo add(SysVenue venue) {
        // 处理新增实验室的时候有管理人员id传入
        if (venue.getStaffId() != null) {
            // 根据人员id查询人员信息
            SysStaff staff = staffMapper.selectById(venue.getStaffId());
            venue.setStaffName(staff.getName());
            // 场馆插入获得插入后的id
            int insert = venueMapper.insert(venue);
            // 是否插入成功
            if (insert > 0) {
                log.info("venue插入---id: {}", insert);
                staff.setVenueId(venue.getVenueId());
                staff.setVenueName(venue.getName());
                // 将场馆id和名称更新至员工表
                if (staffMapper.updateById(staff) > 0) {
                    return ResponseVo.success();
                }
            }
        }
        return ResponseVo.exp();
    }

    @Override
    public ResponseVo updateVenue(SysVenue venue) {
        return ResponseVo.exp();
    }
}
