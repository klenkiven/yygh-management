package xyz.klenkiven.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import xyz.klenkiven.yygh.cmn.client.DictFeignClient;
import xyz.klenkiven.yygh.hosp.repository.HospitalRepository;
import xyz.klenkiven.yygh.hosp.service.HospitalService;
import xyz.klenkiven.yygh.model.hosp.Hospital;
import xyz.klenkiven.yygh.vo.hosp.HospitalQueryVo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;
    private final DictFeignClient dictFeignClient;

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

    @Override
    public Hospital getByHoscode(String hoscode) {
        return hospitalRepository.getHospitalByHoscode(hoscode);
    }

    @Override
    public Page<Hospital> selectHospPage(int page, int limit, HospitalQueryVo queryVo) {
        Pageable pageable = PageRequest.of(page-1, limit);
        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(queryVo, hospital);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                                            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                                            .withIgnoreCase(true);
        Example<Hospital> example = Example.of(hospital, exampleMatcher);

        Page<Hospital> pages = hospitalRepository.findAll(example, pageable);

        pages.getContent().forEach(this::setHospitalHosType);

        return pages;
    }

    @Override
    public void updateStatus(String id, Integer status) {
        Hospital hospital = hospitalRepository.findById(id).get();
        hospital.setStatus(status);
        hospital.setUpdateTime(new Date());
        hospitalRepository.save(hospital);
    }

    @Override
    public Map<String, Object> getHospById(String id) {
        Map<String, Object> result = new HashMap<>();
        Hospital hospital = hospitalRepository.findById(id).get();
        setHospitalHosType(hospital);

        result.put("hospital", hospital);
        result.put("bookingRule", hospital.getBookingRule());
        hospital.setBookingRule(null);

        return result;
    }

    /**
     * 为Hospital进行值封装
     * @param hospital 医院信息
     */
    private void setHospitalHosType(Hospital hospital) {
        String hostypeString = dictFeignClient.getName("Hostype", hospital.getHostype());
        String provinceString = dictFeignClient.getName(hospital.getProvinceCode());
        String cityString = dictFeignClient.getName(hospital.getCityCode());
        String districtString = dictFeignClient.getName(hospital.getDistrictCode());
        hospital.getParam().put("hostypeString", hostypeString);
        hospital.getParam().put("provinceString", provinceString);
        hospital.getParam().put("cityString", cityString);
        hospital.getParam().put("districtString", districtString);
    }
}
