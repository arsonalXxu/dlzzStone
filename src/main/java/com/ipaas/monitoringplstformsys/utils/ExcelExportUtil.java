package com.ipaas.monitoringplstformsys.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ExcelExportUtil {

    public static void exportExcel(HttpServletResponse response, List<?> dataList,
                                   Class<?> clazz, String fileName) throws IOException {
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + encodedFileName + ".xlsx");

        // 写入Excel
        EasyExcel.write(response.getOutputStream(), clazz)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .sheet("长期预测数据")
                .doWrite(dataList);
    }
    public static void exportDynamicExcel(
            HttpServletResponse response,
            String sheetName,
            List<List<String>> head,
            List<Map<Integer, Object>> dataList) throws IOException {

        // 设置响应头（必须在写入数据前）
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(sheetName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet(sheetName)
                    .head(head)
                    .build();

            // 写入数据
            for (Map<Integer, Object> rowMap : dataList) {
                List<Object> rowData = new ArrayList<>(head.size());
                for (int i = 0; i < head.size(); i++) {
                    rowData.add(rowMap.getOrDefault(i, "")); // 处理可能缺失的值
                }
                excelWriter.write(Collections.singletonList(rowData), writeSheet);
            }
        } // 自动关闭资源
    }
}