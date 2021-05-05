package xyz.klenkiven.yygh.hosp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.klenkiven.yygh.common.result.Result;
import xyz.klenkiven.yygh.hosp.service.ScheduleService;
import xyz.klenkiven.yygh.model.hosp.Schedule;

import java.util.List;
import java.util.Map;

@Api(tags = "排班管理接口")
@RestController
@RequestMapping("/admin/hosp/schedule")
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

    /**
     * 根据医院编号，科室编号和工作日期，查询排班详细信息
     * @param hoscode 医院编号
     * @param depcode 科室编号
     * @param workDate 工作日期
     * @return 排班详细信息
     */
    @ApiOperation("查询排班详细信息")
    @GetMapping("/detail/{hoscode}/{depcode}/{workDate}")
    public Result<?> listScheduleDetail(@PathVariable String hoscode,
                                        @PathVariable String depcode,
                                        @PathVariable String workDate) {
        List<Schedule> result = scheduleService.getScheduleDetail(hoscode, depcode, workDate);

        return Result.ok(result);
    }

}
