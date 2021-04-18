package xyz.klenkiven.yygh.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import xyz.klenkiven.yygh.cmn.mapper.DictMapper;
import xyz.klenkiven.yygh.cmn.service.DictService;
import xyz.klenkiven.yygh.model.cmn.Dict;
import xyz.klenkiven.yygh.vo.cmn.DictEeVo;

@AllArgsConstructor
public class DictListener extends AnalysisEventListener<DictEeVo> {

    private final DictService dictService;

    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo, dict);
        dictService.saveOrUpdate(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
