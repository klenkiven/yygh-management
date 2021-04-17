package xyz.klenkiven.yygh.cmn.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.klenkiven.yygh.cmn.service.DictService;

/**
 * 数据字典的控制层
 * @author klenkiven
 */
@Api("数据字典管理")
@RestController
@CrossOrigin
@RequestMapping("/admin/cmn/dict")
@AllArgsConstructor
public class DictController {

    private final DictService dictService;

}
