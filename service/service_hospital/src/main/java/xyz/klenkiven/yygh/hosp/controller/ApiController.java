package xyz.klenkiven.yygh.hosp.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.klenkiven.yygh.hosp.service.HospitalService;

/**
 * 关于医院文档的借口
 */
@Api(tags = "医院接口")
@RestController
@RequestMapping("/api/hosp")
@AllArgsConstructor
@CrossOrigin
public class ApiController {

    private final HospitalService hospitalService;

}
