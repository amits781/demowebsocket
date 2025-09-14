package com.aidyn.demowebsocket.controller;

import com.aidyn.demowebsocket.service.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyMessageController {

    private final MessageService service;

    public MyMessageController(MessageService service) {
        this.service = service;
    }

    @GetMapping("{clientName}/sendHello")
    public String sayHello(@PathVariable(name = "clientName") String name){
        service.sendMessageToClient(name,"Hello");
        return "Sent";
    }

    @GetMapping(value = "/metrics", produces = "text/plain")
    public String metrics() {
        int value = fetchValueFromHttp(); // your HTTP call
        return "my_cost_http_metric " + value + "\n";
    }

    private int fetchValueFromHttp() {
        // call your API and return some number
        return 42;
    }
}
