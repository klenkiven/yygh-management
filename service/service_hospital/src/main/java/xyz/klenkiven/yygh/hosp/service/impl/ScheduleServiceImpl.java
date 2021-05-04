package xyz.klenkiven.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.klenkiven.yygh.hosp.repository.ScheduleRepository;
import xyz.klenkiven.yygh.hosp.service.ScheduleService;
import xyz.klenkiven.yygh.model.hosp.Schedule;

import java.util.Date;
import java.util.Map;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public void save(Map<String, Object> paramMap) {
        String s = JSONObject.toJSONString(paramMap);
        Schedule schedule = JSONObject.parseObject(s, Schedule.class);

        Schedule scheduleExist = scheduleRepository.
                getScheduleByHoscodeAndHosScheduleId(schedule.getHoscode(), schedule.getHosScheduleId());

        if (scheduleExist != null) {
            schedule.setId(scheduleExist.getId());
            schedule.setIsDeleted(schedule.getIsDeleted());
            schedule.setCreateTime(scheduleExist.getCreateTime());
        } else {
            schedule.setIsDeleted(0);
            schedule.setCreateTime(new Date());
        }

        schedule.setUpdateTime(new Date());
        scheduleRepository.save(schedule);
    }
}
