package com.aquarium.controller;

import com.aquarium.response.ResponseVo;
import com.aquarium.service.ISysWaterQualityService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tt-Tang
 * @since 2023-03-04
 */
@RestController
@RequestMapping("/water")
public class SysWaterQualityController {
    @Resource
    private ISysWaterQualityService waterQualityService;

    /**
     * 根据场馆 ID 分页查询水质信息
     *
     * @param page
     * @param limit
     * @param name
     * @param sort
     * @return
     */
    @GetMapping("/list")
    public ResponseVo list(
            @RequestParam(name = "pageNo") long page,
            @RequestParam(name = "pageSize") long limit,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) int venueId,
            @RequestParam(required = false) String date) {
        return waterQualityService.listWaterData(page, limit, name, venueId, date);
    }
}
