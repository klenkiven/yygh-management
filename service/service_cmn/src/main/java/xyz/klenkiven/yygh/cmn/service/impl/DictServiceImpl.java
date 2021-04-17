package xyz.klenkiven.yygh.cmn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.klenkiven.yygh.cmn.mapper.DictMapper;
import xyz.klenkiven.yygh.cmn.service.DictService;
import xyz.klenkiven.yygh.model.cmn.Dict;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典服务实现类
 * @author klenkiven
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {


    @Override
    public List<Dict> findChildrenByParentId(Long id) {

        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);

        List<Dict> dictList = baseMapper.selectList(queryWrapper);
        // 填充 hasChildren 字段
        for (Dict d: dictList)
            d.setHasChildren(this.hasChildren(d));

        return dictList;
    }

    /**
     * 判断节点是不是有子节点
     *      下面这种写法，性能比较高，如果exists, 就直接返回了
     *      这样的话，性能比直接 selectCount 快多了
     * @param dict 字典数据
     * @return 是否有子节点
     */
    private boolean hasChildren(Dict dict) {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("id", dict.getId());
        dictQueryWrapper.exists("select * from dict where parent_id = " + dict.getId());

        return !baseMapper.selectList(dictQueryWrapper).isEmpty();
    }
}
