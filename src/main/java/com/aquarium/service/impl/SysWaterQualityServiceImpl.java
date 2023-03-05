package com.aquarium.service.impl;

import cn.hutool.core.date.DateUtil;
import com.aquarium.mapper.SysWaterQualityMapper;
import com.aquarium.pojo.SysWaterQuality;
import com.aquarium.response.ResponseVo;
import com.aquarium.service.ISysWaterQualityService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
public class SysWaterQualityServiceImpl extends ServiceImpl<SysWaterQualityMapper, SysWaterQuality> implements ISysWaterQualityService {

    @Resource
    private SysWaterQualityMapper waterQualityMapper;

    @Override
    public ResponseVo listWaterData(long page, long limit, String name, int venueId, String date) {
        // 分页
        Page<SysWaterQuality> waterQualityPage = new Page<>();
        // 当前页面
        waterQualityPage.setCurrent(page);
        //页面大小
        waterQualityPage.setSize(limit);
        LambdaQueryWrapper<SysWaterQuality> wrapper = Wrappers.lambdaQuery();
        // 根据日期查询
        if (date != null) {
            wrapper.eq(SysWaterQuality::getDataDate, DateUtil.parse(date));
        }
        // 根据场馆ID查询场馆的水质数据
        wrapper.eq(SysWaterQuality::getVenueId, venueId);
        // 根据日期顺序排列
        wrapper.orderByAsc(SysWaterQuality::getDataDate);
        Page<SysWaterQuality> selectPage = waterQualityMapper.selectPage(waterQualityPage, wrapper);
        return ResponseVo.success().data("items", selectPage.getRecords()).data("total", selectPage.getTotal());
    }
}
