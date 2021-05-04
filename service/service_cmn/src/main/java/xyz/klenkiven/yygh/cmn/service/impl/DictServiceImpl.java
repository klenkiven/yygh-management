package xyz.klenkiven.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.klenkiven.yygh.cmn.listener.DictListener;
import xyz.klenkiven.yygh.cmn.mapper.DictMapper;
import xyz.klenkiven.yygh.cmn.service.DictService;
import xyz.klenkiven.yygh.model.cmn.Dict;
import xyz.klenkiven.yygh.vo.cmn.DictEeVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 字典服务实现类
 * @author klenkiven
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {


    /**
     * 下面的注解表明了，寻找的缓存名是什么
     * 缓存的
     */
    @Override
    @Cacheable(value = "dict", keyGenerator = "keyGenerator")
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

    @Override
    public void exportDict(HttpServletResponse response) {
        // 设置下载信息
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = "dict";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        // 查询数据库
        List<Dict> dictList = baseMapper.selectList(null);
        // Dict转换为Dict vo对象
        List<DictEeVo> dictEeVoList = new ArrayList<>(dictList.size());
        for (Dict d: dictList) {
            DictEeVo dictEeVo = new DictEeVo();
            // 复制属性信息
//            dictEeVo.setDictCode(d.getDictCode());
//            dictEeVo.setId(d.getId());
//            dictEeVo.setName(d.getName());
//            dictEeVo.setValue(d.getValue());
//            dictEeVo.setParentId(d.getParentId());
            // 直接有工具可以做
            BeanUtils.copyProperties(d, dictEeVo);
            dictEeVoList.add(dictEeVo);
        }
        try {
            EasyExcel.write(response.getOutputStream(), DictEeVo.class)
                    .sheet("Dict")
                    .doWrite(dictEeVoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下面的注解表明了，更新的是哪一个注解，allEntries这个属性的含义就是
     * 清空所有的缓存
     */
    @Override
    @CacheEvict(value = "dict", allEntries = true)
    public void importDict(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DictEeVo.class, new DictListener(this))
                    .sheet("Dict")
                    .doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDictName(String diccode, String value) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("value", value);
        if (!"".equals(diccode)) {
            // Select for parent_id
            QueryWrapper<Dict> wrapper = new QueryWrapper<>();
            wrapper.eq("dict_code", diccode);
            Dict dict = baseMapper.selectOne(wrapper);
            // Add parent_id condition
            queryWrapper.eq("parent_id", dict.getId());
        }
        Dict dict = baseMapper.selectOne(queryWrapper);
        return dict != null? dict.getName(): null;
    }

    @Override
    public List<Dict> findByDictCode(String dictCode) {
        // Get parent_id
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_code", dictCode);
        Dict dict = baseMapper.selectOne(queryWrapper);

        // Get children
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", dict.getId());
        return baseMapper.selectList(wrapper);
    }
}
