package xyz.klenkiven.yygh.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.klenkiven.yygh.common.result.Result;

/**
 * @author klenkiven
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> error(Exception e) {
        e.printStackTrace();
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(YyghException.class)
    @ResponseBody
    public Result<?> yyghException(YyghException e) {
        e.printStackTrace();
        return Result.fail(e.getMessage());
    }
}
