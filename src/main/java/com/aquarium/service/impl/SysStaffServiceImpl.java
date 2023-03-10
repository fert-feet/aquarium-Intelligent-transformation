package com.aquarium.service.impl;

import com.aquarium.mapper.SysStaffMapper;
import com.aquarium.mapper.SysVenueMapper;
import com.aquarium.pojo.SysStaff;
import com.aquarium.response.ResponseVo;
import com.aquarium.service.ISysStaffService;
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
public class SysStaffServiceImpl extends ServiceImpl<SysStaffMapper, SysStaff> implements ISysStaffService {

    @Resource
    private SysStaffMapper staffMapper;

    @Resource
    private SysVenueMapper venueMapper;

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

}
