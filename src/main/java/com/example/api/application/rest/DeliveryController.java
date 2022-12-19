package com.example.api.application.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/delivery")
public class DeliveryController {

    @GetMapping
    public Object aopResHelloWorld() {
        return "Hello World";
    }
}
