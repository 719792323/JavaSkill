package jmeter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JmeterController {

    @RequestMapping(value = "/test")
    public String test() {
        return "ok";
    }
}
