package xyz.klenkiven.easyexcel;

import com.alibaba.excel.EasyExcel;

public class TestRead {
    public static void main(String[] args) {
        String fileName = "/home/klenkiven/01.xlsx";
        EasyExcel.read(fileName, UserData.class, new ExcelListener())
                .sheet("userData")
                .doRead();
    }
}
