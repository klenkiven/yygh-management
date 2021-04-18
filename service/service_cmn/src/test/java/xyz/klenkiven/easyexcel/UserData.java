package xyz.klenkiven.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class UserData {

    @ExcelProperty(value = "用户编号", index = 0)
    private int uid;

    @ExcelProperty(value = "用户姓名", index = 1)
    private String uname;

}
