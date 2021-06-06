package xyz.klenkiven.yygh.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.klenkiven.yygh.model.user.UserInfo;

/**
 * 用户信息的Mapper
 * @author klenkiven
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
