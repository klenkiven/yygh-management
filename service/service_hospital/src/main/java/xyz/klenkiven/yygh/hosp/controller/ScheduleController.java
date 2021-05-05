package xyz.klenkiven.yygh.hosp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.klenkiven.yygh.common.result.Result;
import xyz.klenkiven.yygh.hosp.service.ScheduleService;
import xyz.klenkiven.yygh.model.hosp.Schedule;

import java.util.Map;

@Api(tags = "排班管理接口")
@RestController
@RequestMapping("/admin/hosp/schedule")
@CrossOrigin
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 根据医院编号和科室编号查询排班规则数据
     * @param page 当前页
     * @param limit 每页大小
     * @param hoscode 医院编号
     * @param depcode 部门编号
     * @return 排班规则查询
     */
    @ApiOperation("查询排班规则数据")
    @GetMapping("/rule/{page}/{limit}/{hoscode}/{depcode}")
    public Result<Map<String, Object>> listScheduleRule(@PathVariable long page,
                                     @PathVariable long limit,
                                     @PathVariable String hoscode,
                                     @PathVariable String depcode) {
        Map<String, Object> result = scheduleService.getScheduleRule(page, limit, hoscode, depcode);
        return Result.ok(result);
    }

}
