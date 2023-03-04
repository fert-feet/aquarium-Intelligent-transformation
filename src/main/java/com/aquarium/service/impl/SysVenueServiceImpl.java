package com.aquarium.service.impl;

import com.aquarium.mapper.SysVenueMapper;
import com.aquarium.pojo.SysVenue;
import com.aquarium.response.ResponseVo;
import com.aquarium.service.ISysVenueService;
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
public class SysVenueServiceImpl extends ServiceImpl<SysVenueMapper, SysVenue> implements ISysVenueService {

    @Resource
    private SysVenueMapper venueMapper;

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
}
