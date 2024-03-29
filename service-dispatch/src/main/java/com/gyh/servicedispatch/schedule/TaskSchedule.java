package com.gyh.servicedispatch.schedule;

import com.gyh.servicedispatch.context.TaskStore;
import com.gyh.servicedispatch.task.ITask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class TaskSchedule {
    private static final Logger logger = LoggerFactory.getLogger(TaskSchedule.class);
    @Autowired
    private TaskStore taskStore;
    @Autowired
    private TaskManager taskManager;

    @Scheduled(cron = "0/1 * * * * ? ") // 每5秒执行一次
    public void schedule() {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<ITask> tasks = taskStore.getNeedRetryTask();
        tasks.stream().forEach(it -> taskManager.retry(it));
    }
}
