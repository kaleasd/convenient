package com.gyh.servicedispatch.controller;

import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.servicedispatch.db.RedisDb;
import com.gyh.servicedispatch.request.DispatchOrderRequest;
import com.gyh.servicedispatch.request.DispatchRequest;
import com.gyh.servicedispatch.schedule.TaskManager;
import com.gyh.servicedispatch.service.impl.DispatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 */
@RestController
@Slf4j
@RequestMapping("/dispatch")
public class OrderController {
    @Autowired
    private TaskManager taskManager;
    @Autowired
    private RedisDb redisDb;

    @RequestMapping("/")
    public String home() {
        return "dispatch";
    }

    /**
     * 派单
     *
     * @param request
     * @return
     * @throws InterruptedException
     */
    @ResponseBody
    @RequestMapping(value = "/dispatchOrder", produces = "application/json; charset=utf-8")
    public ResponseResult dispatchOrder(@RequestBody DispatchOrderRequest request) throws InterruptedException {
        int orderId = request.getOrderId();
        taskManager.dispatch(orderId);
        return ResponseResult.success("success");
    }

    @PostMapping("/vehicleDispatch")
    public ResponseResult dispatch(@RequestBody DispatchRequest dispatchRequest) {
        return DispatchServiceImpl.ins().dispatch(dispatchRequest);
    }

}
