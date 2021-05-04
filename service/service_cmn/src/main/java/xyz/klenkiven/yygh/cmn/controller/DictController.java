package xyz.klenkiven.yygh.cmn.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.klenkiven.yygh.cmn.service.DictService;
import xyz.klenkiven.yygh.common.result.Result;
import xyz.klenkiven.yygh.model.cmn.Dict;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * @param parentId 父节点id
     * @return 子节点数据列表
     */
    @ApiOperation("根据父节点id获取子节点数据列表")
    @ApiImplicitParam(name = "parentId", value = "上级节点id", paramType = "path",
            required = true, dataType = "long")
    @GetMapping("findChildrenByParentId/{parentId}")
    public Result<List<Dict>> findChildrenByParentId(@PathVariable Long parentId) {
        List<Dict> dictList = dictService.findChildrenByParentId(parentId);
        return Result.ok(dictList);
    }

    /**
     * 导出字典数据
     *      这里对resp进行了一些操作
     *      所以直接返回空就好了，如果
     *      一定要返回结果就会出错，因
     *      为前端使用这个请求的时候，
     *      是按照请在资源请求的这种
     *      方式获得的。
     * @param response 响应传输数据
     */
    @ApiOperation("导出字典数据")
    @GetMapping("/exportData")
    public void exportDict(HttpServletResponse response) {
        dictService.exportDict(response);
    }

    /**
     * 导入字典数据
     * @param file 字典文件
     * @return 成功与否
     */
    @ApiOperation("导入字典数据")
    @PostMapping("/importData")
    public Result<?> importDict(MultipartFile file) {
        dictService.importDict(file);
        return Result.ok();
    }

    /**
     * 根据 dictCode 获取下级节
     * @param dictCode 字典名
     * @return 字典内容
     */
    @ApiOperation("根据 dictCode 获取下级节点")
    @GetMapping("/findByDictCode/{dictCode}")
    public Result<List<Dict>> findByDictCode(@PathVariable String dictCode) {
        List<Dict> dictList = dictService.findByDictCode(dictCode);
        return Result.ok(dictList);
    }

    /**
     * 根据dictcode和value查询字典内容
     * @param diccode 字典名
     * @param value 字典值
     * @return 对应的字典内容
     */
    @ApiOperation("根据dictcode和value查询")
    @GetMapping("/getName/{diccode}/{value}")
    public String getName(@PathVariable String diccode,
                          @PathVariable String value) {
        return dictService.getDictName(diccode, value);
    }

    /**
     * 根据Value查询字典内容
     * @param value 字典值
     * @return 字典内容
     */
    @ApiOperation("根据value查询")
    @GetMapping("/getName/{value}")
    public String getName(@PathVariable String value) {
        return dictService.getDictName("", value);
    }
}
