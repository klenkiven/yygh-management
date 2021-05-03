package xyz.klenkiven.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.klenkiven.yygh.hosp.repository.HospitalRepository;
import xyz.klenkiven.yygh.hosp.service.HospitalService;
import xyz.klenkiven.yygh.model.hosp.Hospital;

import java.util.Date;
import java.util.Map;

@Service
@AllArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;

    @Override
    public void save(Map<String, Object> paramMap) {
        // Convert to object of type of Hospital
        String mapStr = JSONObject.toJSONString(paramMap);
        Hospital hospital = JSONObject.parseObject(mapStr, Hospital.class);

        // Is exist?
        String hoscode = hospital.getHoscode();
        Hospital hospitalExist = hospitalRepository.getHospitalByHoscode(hoscode);

        // If not, add
        if (hospitalExist != null) {
            // 修改对应的值
            hospital.setStatus(hospitalExist.getStatus());
            hospital.setStatus(hospitalExist.getIsDeleted());
            hospital.setCreateTime(hospitalExist.getCreateTime());
            hospital.setUpdateTime(new Date());
        } else {
            // 修改对应的值
            hospital.setStatus(0);
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
        }

        hospitalRepository.save(hospital);
    }
}
