package com.cj.core.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 跳转到swagger
 */
@Controller
@ApiIgnore
@RequestMapping
public class ToPage {

    @GetMapping("/api")
    public String toSwaggerUi(){
        return "redirect:/swagger-ui.html";
    }
}
