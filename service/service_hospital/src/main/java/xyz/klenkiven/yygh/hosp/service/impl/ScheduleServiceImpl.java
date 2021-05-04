package xyz.klenkiven.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import xyz.klenkiven.yygh.hosp.repository.ScheduleRepository;
import xyz.klenkiven.yygh.hosp.service.ScheduleService;
import xyz.klenkiven.yygh.model.hosp.Schedule;
import xyz.klenkiven.yygh.vo.hosp.ScheduleQueryVo;

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

    @Override
    public Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo queryVo) {
        Pageable pageable = PageRequest.of(page, limit);
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(queryVo, schedule);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                                            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                                            .withIgnoreCase(true);

        Example<Schedule> scheduleExample = Example.of(schedule, exampleMatcher);
        Page<Schedule> all = scheduleRepository.findAll(scheduleExample, pageable);
        return all;
    }

    @Override
    public void remove(String hoscode, String hosScheduleId) {
        Schedule schedule = scheduleRepository
                .getScheduleByHoscodeAndHosScheduleId(hoscode, hosScheduleId);
        if (schedule != null)
            scheduleRepository.deleteScheduleByHoscodeAndHosScheduleId(hoscode, hosScheduleId);
    }
}
