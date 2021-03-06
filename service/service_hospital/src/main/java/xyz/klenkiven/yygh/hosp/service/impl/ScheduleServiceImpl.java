package xyz.klenkiven.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import xyz.klenkiven.yygh.hosp.repository.ScheduleRepository;
import xyz.klenkiven.yygh.hosp.service.DepartmentService;
import xyz.klenkiven.yygh.hosp.service.HospitalService;
import xyz.klenkiven.yygh.hosp.service.ScheduleService;
import xyz.klenkiven.yygh.model.hosp.Hospital;
import xyz.klenkiven.yygh.model.hosp.Schedule;
import xyz.klenkiven.yygh.vo.hosp.BookingScheduleRuleVo;
import xyz.klenkiven.yygh.vo.hosp.ScheduleQueryVo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MongoTemplate mongoTemplate;
    private final HospitalService hospitalService;
    private final DepartmentService departmentService;

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
        Pageable pageable = PageRequest.of(page-1, limit);
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

    @Override
    public Map<String, Object> getScheduleRule(long page, long limit, String hoscode, String depcode) {
        // ???????????????????????????????????????
        Criteria criteria = Criteria.where("hoscode").is(hoscode).and("depcode").is(depcode);

        // Mongo ????????????
        // ?????????????????????????????????????????????
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),  // ????????????
                Aggregation.group("workDate")  // ????????????
                        .first("workDate").as("workDate")
                        .count().as("docCount")
                        .sum("reservedNumber").as("reservedNumber")
                        .sum("availableNumber").as("availableNumber"),
                Aggregation.sort(Sort.Direction.DESC, "workDate"), // ??????
                // ??????
                Aggregation.skip((page-1)*limit),
                Aggregation.limit(limit)
        );
        AggregationResults<BookingScheduleRuleVo> aggregate =
                mongoTemplate.aggregate(agg, Schedule.class, BookingScheduleRuleVo.class);
        List<BookingScheduleRuleVo> bookingScheduleRuleVoList = aggregate.getMappedResults();

        // ??????????????????
        Aggregation totalAgg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("workDate")
        );
        AggregationResults<BookingScheduleRuleVo> totalAggResults =
                mongoTemplate.aggregate(totalAgg, Schedule.class, BookingScheduleRuleVo.class);
        int total = totalAggResults.getMappedResults().size();

        // ???????????????????????????
        for (BookingScheduleRuleVo b: bookingScheduleRuleVoList) {
            Date workDate = b.getWorkDate();
            String dayOfWeek = getDayOfWeek(new DateTime(workDate));
            b.setDayOfWeek(dayOfWeek);
        }

        // ??????????????????
        Map<String, Object> result = new HashMap<>();
        result.put("bookingScheduleRuleList", bookingScheduleRuleVoList);
        result.put("total", total);
        Map<String, String> baseMap = new HashMap<>();
        baseMap.put("hosname", hospitalService.getHosnameByHoscode(hoscode));
        result.put("baseMap", baseMap);

        return result;
    }

    @Override
    public List<Schedule> getScheduleDetail(String hoscode, String depcode, String workDate) {
        List<Schedule> scheduleList =
                scheduleRepository.findAllByHoscodeAndDepcodeAndWorkDate(hoscode, depcode, new DateTime(workDate).toDate());
        scheduleList.forEach(this::packageSchedule);
        return scheduleList;
    }

    /**
     * ????????????????????????
     *      ????????????
     *      ????????????
     *      ??????????????????
     * @param item ??????????????????
     */
    private void packageSchedule(Schedule item) {
        Map<String, Object> param = item.getParam();
        param.put("hosname", hospitalService.getHosnameByHoscode(item.getHoscode()));
        param.put("depname", departmentService.getDepnameByDepcode(item.getHoscode(), item.getDepcode()));
        param.put("dayOfWeek", getDayOfWeek(new DateTime(item.getWorkDate())));
    }

    /**
     * ??????????????????????????????
     * @param dateTime ??????
     * @return ??????
     */
    private String getDayOfWeek(DateTime dateTime) {
        String dayOfWeek = "";
        switch (dateTime.getDayOfWeek()) {
            case DateTimeConstants.SUNDAY:
                dayOfWeek = "??????";
                break;
            case DateTimeConstants.MONDAY:
                dayOfWeek = "??????";
                break;
            case DateTimeConstants.TUESDAY:
                dayOfWeek = "??????";
                break;
            case DateTimeConstants.WEDNESDAY:
                dayOfWeek = "??????";
                break;
            case DateTimeConstants.THURSDAY:
                dayOfWeek = "??????";
                break;
            case DateTimeConstants.FRIDAY:
                dayOfWeek = "??????";
                break;
            case DateTimeConstants.SATURDAY:
                dayOfWeek = "??????";
            default:
                break;
        }
        return dayOfWeek;
    }
}
