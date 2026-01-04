package com.ipaas.monitoringplstformsys.track.job;

import com.ipaas.monitoringplstformsys.track.service.ApiTrackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class ApiTrackJob {

    @Resource
    private ApiTrackService apiTrackService;


    @Scheduled(cron = "${sync.cron}") // cron表达式：每30分钟执行一次
    public void scheduledQueryApiUsageStats() {
        DateTimeFormatter formatterWithoutMillis = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.info("定时任务开始时间：" + LocalDateTime.now().format(formatterWithoutMillis));
        // 调用目标方法
        apiTrackService.queryApiUsageStats();
        log.info("定时任务结束时间："+LocalDateTime.now().format(formatterWithoutMillis));
    }

}
