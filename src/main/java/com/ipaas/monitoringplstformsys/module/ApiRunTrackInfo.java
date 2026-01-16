package com.ipaas.monitoringplstformsys.module;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat; // 引入这个包
import lombok.Data;
import java.util.Date;

@Data
@TableName("api_run_track_info")
public class ApiRunTrackInfo {
    private Long id;
    private String requestId;

    /**
     * 前端传参：2026-01-06T02:11:12.000Z (UTC时间)
     * 后端解析：需要指定 pattern 和 timezone
     *
     * timezone = "GMT+8": 会把前端的 02:11 (UTC) 自动转为 10:11 (北京时间) 存入数据库
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date preResolveTime;

    private String result;
}
