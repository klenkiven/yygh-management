package xyz.klenkiven.yygh.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.klenkiven.yygh.model.user.UserInfo;
import xyz.klenkiven.yygh.user.mapper.UserInfoMapper;
import xyz.klenkiven.yygh.user.service.UserInfoService;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements UserInfoService {
}
