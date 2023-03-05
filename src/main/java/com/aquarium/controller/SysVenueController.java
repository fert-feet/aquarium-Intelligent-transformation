package com.aquarium.controller;

import com.aquarium.pojo.SysVenue;
import com.aquarium.response.ResponseVo;
import com.aquarium.service.ISysVenueService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/venue")
public class SysVenueController {

    @Resource
    private ISysVenueService venueService;

    /**
     * 场馆分页查询
     *
     * @param page
     * @param limit
     * @param name
     * @param sort
     * @return
     */
    @GetMapping("/list")
    public ResponseVo list(
            long page,
            long limit,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String sort) {
        return venueService.listVenue(page, limit, name);
    }

    /**
     * 查询场馆不分页
     *
     * @return
     */
    @GetMapping("/originList")
    public ResponseVo originList() {
        List<SysVenue> list = venueService.list();
        return ResponseVo.success().data("items", list).data("total", list.size());
    }

    @PostMapping("/delete")
    public ResponseVo delete(int venueId) {
        if (venueService.removeById(venueId)) {
            return ResponseVo.success();
        }
        return ResponseVo.exp();
    }

}
