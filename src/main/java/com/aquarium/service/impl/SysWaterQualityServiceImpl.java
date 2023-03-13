package com.aquarium.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.aquarium.mapper.SysVenueMapper;
import com.aquarium.mapper.SysWaterQualityMapper;
import com.aquarium.pojo.SysVenue;
import com.aquarium.pojo.SysWaterQuality;
import com.aquarium.response.ResponseVo;
import com.aquarium.service.ISysWaterQualityService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
public class SysWaterQualityServiceImpl extends ServiceImpl<SysWaterQualityMapper, SysWaterQuality> implements ISysWaterQualityService {

    @Resource
    private SysWaterQualityMapper waterQualityMapper;

    @Resource
    private SysVenueMapper venueMapper;

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
        if (venueId != 0) {
            // 根据场馆ID查询场馆的水质数据
            wrapper.eq(SysWaterQuality::getVenueId, venueId);
        }
        // 根据日期顺序排列
        wrapper.orderByAsc(SysWaterQuality::getDataDate);
        Page<SysWaterQuality> selectPage = waterQualityMapper.selectPage(waterQualityPage, wrapper);
        return ResponseVo.success().data("items", selectPage.getRecords()).data("total", selectPage.getTotal());
    }

    @Override
    public ResponseVo createOne() {
        // 获取当前时间
        LocalDateTime time = LocalDateTime.now();
        // 查询所有场馆信息，作为随机生成水质数据的归属场所
        List<SysVenue> venueList = venueMapper.findVenueList();
        SysWaterQuality waterQuality = new SysWaterQuality();
        // 随机生成数据并返回对象
        SysWaterQuality generatedWaterQuality = generateWaterQuality(time, venueList, waterQuality);
        return ResponseVo.success().data("item", generatedWaterQuality);
    }

    /**
     * 随机生成水质数据
     *
     * @param time
     * @param venueList
     * @param waterQuality
     * @return
     */
    private SysWaterQuality generateWaterQuality(LocalDateTime time, List<SysVenue> venueList, SysWaterQuality waterQuality) {
        // 随机选择一个场馆
        SysVenue venue = RandomUtil.randomEle(venueList);
        String venueName = venueMapper.selectById(venue).getName();
        // 随机生成水温，数据乘上1.5是为了有几率产生超过阈值的数据以进行报警
        double randomTem = RandomUtil.randomDouble(venue.getMinWaterTemperature() * 1.5, venue.getMaxWaterTemperature() * 2);
        log.info("randomTem---{}", randomTem);
        // 随机生成PH值
        double randomPH = RandomUtil.randomDouble(venue.getMinWaterPh() * 1.5, venue.getMaxWaterPh() * 2);
        log.info("randomPH---{}", randomPH);
        // 随机生成盐度
        double randomSalt = RandomUtil.randomDouble(venue.getMinWaterSalt() * 1.5, venue.getMaxWaterSalt() * 2);
        log.info("randomSalt---{}", randomSalt);
        // 随机生成有效磷含量
        double randomAvaPho = RandomUtil.randomDouble(venue.getMinWaterAvailablePhosphorous() * 1.5, venue.getMaxWaterAvailablePhosphorous() * 2);
        log.info("randomAvaPho---{}", randomAvaPho);
        return setRandomDataWaterQuality(venue.getVenueId(), venueName, time, randomSalt, randomPH, randomAvaPho, randomTem, waterQuality);
    }

    /**
     * 将随机数据设置到水质对象中
     *
     * @param venueName
     * @param time
     * @param randomSalt
     * @param randomPH
     * @param randomAvaPho
     * @param randomTem
     * @param waterQuality
     * @return
     */
    private SysWaterQuality setRandomDataWaterQuality(Integer venueId, String venueName, LocalDateTime time, double randomSalt, double randomPH, double randomAvaPho, double randomTem, SysWaterQuality waterQuality) {
        // 将随机数设置到对象中
        waterQuality.setDataDate(time);
        waterQuality.setVenueId(venueId);
        waterQuality.setVenueName(venueName);
        waterQuality.setWaterPh(randomPH);
        waterQuality.setWaterSalt(randomSalt);
        waterQuality.setWaterTemperature(randomTem);
        waterQuality.setWaterAvailablePhosphorous(randomAvaPho);
        return waterQuality;
    }


}
