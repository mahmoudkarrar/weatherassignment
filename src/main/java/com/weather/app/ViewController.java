package com.weather.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Mahmoud.Fathy on 4/9/2017.
 */
@Controller
public class ViewController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
