package com.weather.app;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Mahmoud.Fathy on 4/7/2017.
 */
@RestController
public class DemoController {
    @RequestMapping(value = "/hello")
    private String hello() {
        return "hello";
    }
}
