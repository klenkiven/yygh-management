package xyz.klenkiven.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.klenkiven.yygh.cmn.mapper.DictMapper;
import xyz.klenkiven.yygh.model.cmn.Dict;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典服务接口
 * @author klenkiven
 */
public interface DictService extends IService<Dict> {

    /**
     *  根据父字典ID获取子数据
     * @param id
     * @return
     */
    List<Dict> findChildrenByParentId(Long id);
}
