package me.plurg.cloudgateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/creatorServiceFallBack")
    public String creatorServiceFallBack(){
        return "Creator Service is down";
    }

    @GetMapping("/articleServiceFallBack")
    public String articleServiceFallBack(){
        return "Article Service is down";
    }
}
