package xyz.klenkiven.yygh.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.klenkiven.yygh.model.hosp.Hospital;

import java.util.Map;

public interface HospitalService {

    void save(Map<String, Object> paramMap);

}
