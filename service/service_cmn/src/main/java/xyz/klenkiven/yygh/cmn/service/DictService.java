package xyz.klenkiven.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import xyz.klenkiven.yygh.cmn.mapper.DictMapper;
import xyz.klenkiven.yygh.model.cmn.Dict;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 字典服务接口
 * @author klenkiven
 */
public interface DictService extends IService<Dict> {

    /**
     *  根据父字典ID获取子数据
     * @param id parentId
     * @return 字典的数据
     */
    List<Dict> findChildrenByParentId(Long id);

    /**
     * 导出字典数据
     * @param response 响应
     */
    void exportDict(HttpServletResponse response);

    void importDict(MultipartFile file);
}
