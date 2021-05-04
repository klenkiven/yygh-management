package xyz.klenkiven.yygh.hosp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import xyz.klenkiven.yygh.common.result.Result;
import xyz.klenkiven.yygh.hosp.service.HospitalService;
import xyz.klenkiven.yygh.model.hosp.Hospital;
import xyz.klenkiven.yygh.vo.hosp.HospitalQueryVo;

@Api(tags = "医院管理")
@RestController
@RequestMapping("/admin/hosp/hospital")
@CrossOrigin
@AllArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    @ApiOperation("医院分页查询")
    @GetMapping("/list/{page}/{limit}")
    public Result<Page<Hospital>> pagingResult(@PathVariable int page,
                                               @PathVariable int limit,
                                               HospitalQueryVo queryVo) {
        Page<Hospital> hospitalPage = hospitalService.selectHospPage(page, limit, queryVo);
        return Result.ok(hospitalPage);
    }
}
