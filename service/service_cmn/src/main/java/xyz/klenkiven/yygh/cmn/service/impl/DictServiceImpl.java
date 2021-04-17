package xyz.klenkiven.yygh.cmn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.klenkiven.yygh.cmn.mapper.DictMapper;
import xyz.klenkiven.yygh.cmn.service.DictService;
import xyz.klenkiven.yygh.model.cmn.Dict;

/**
 * 字典服务实现类
 * @author klenkiven
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

}
