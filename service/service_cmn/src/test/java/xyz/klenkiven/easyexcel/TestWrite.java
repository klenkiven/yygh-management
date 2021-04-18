package xyz.klenkiven.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestWrite {

    public static void main(String[] args) {
        // Construct a List
        List<UserData> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserData user = new UserData();
            user.setUid(i);
            user.setUname(UUID.randomUUID().toString());
            userList.add(user);
        }

        // Set excel file path
        String fileName = "/home/klenkiven/01.xlsx";

        // Call method
        EasyExcel.write(fileName, UserData.class)
                .sheet("userData")
                .doWrite(userList);
    }

}
