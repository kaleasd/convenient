package com.gyh.apilistenorder.controller;

import com.gyh.apilistenorder.response.PreGrabResponse;
import com.gyh.apilistenorder.service.ListenService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gyh
 */
@RestController
@RequestMapping("/listen")
public class ListenController {

    @Autowired
    private ListenService listenService;

    @RequestMapping(value = "/driver/{driverId}",produces = "text/event-stream;charset=utf-8")
    public String getData(@PathVariable("driverId") int driverId){

        System.out.println("方法进来了"+Math.random());
        PreGrabResponse preGrabResponse = listenService.listen(driverId);

        return "data:"+ JSONObject.fromObject(preGrabResponse)+"\n\n";
    }
}
