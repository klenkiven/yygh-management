package xyz.klenkiven.yygh.hosp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import xyz.klenkiven.yygh.hosp.service.HospitalService;
import xyz.klenkiven.yygh.model.hosp.Hospital;

@Service
@AllArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final MongoRepository<Hospital, String> hospitalRepository;

}
