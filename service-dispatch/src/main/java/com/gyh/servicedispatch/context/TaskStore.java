package com.gyh.servicedispatch.context;

import com.gyh.servicedispatch.task.ITask;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gyh
 * @date 2023/2/20
 * */
@Component
@Data
public class TaskStore {
    private static final Logger logger = LoggerFactory.getLogger(TaskStore.class);

    private final ConcurrentHashMap<Integer, ITask> results = new ConcurrentHashMap<>();

    public void addTask(int taskId, ITask task) {
        results.put(taskId, task);
    }

    public List<ITask> getNeedRetryTask() {
        synchronized (results) {
            List<ITask> list = new ArrayList<>(results.values());
            results.clear();
            return list;
        }
    }
}
