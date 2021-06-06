package xyz.klenkiven.yygh.user.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.klenkiven.yygh.user.service.UserInfoService;

@Api(tags = "用户信息API")
@RestController
@RequestMapping("/api/user/")
@AllArgsConstructor
public class UserInfoApiController {

    private final UserInfoService userInfoService;

}
