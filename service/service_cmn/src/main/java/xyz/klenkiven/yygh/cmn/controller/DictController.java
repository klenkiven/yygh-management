package xyz.klenkiven.yygh.cmn.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.klenkiven.yygh.cmn.service.DictService;
import xyz.klenkiven.yygh.common.result.Result;
import xyz.klenkiven.yygh.model.cmn.Dict;

import java.util.List;

/**
 * 数据字典的控制层
 * @author klenkiven
 */
@Api(tags = "数据字典管理")
@RestController
@CrossOrigin
@RequestMapping("/admin/cmn/dict")
@AllArgsConstructor
public class DictController {

    private final DictService dictService;

    /**
     * 根据父节点id获取子节点数据列表
     * @param parentId
     * @return
     */
    @ApiOperation("根据父节点id获取子节点数据列表")
    @ApiImplicitParam(name = "parentId", value = "上级节点id", paramType = "path",
            required = true, dataType = "long")
    @GetMapping("findChildrenByParentId/{parentId}")
    public Result<List<Dict>> findChildrenByParentId(@PathVariable Long parentId) {
        List<Dict> dictList = dictService.findChildrenByParentId(parentId);
        return Result.ok(dictList);
    }

}
