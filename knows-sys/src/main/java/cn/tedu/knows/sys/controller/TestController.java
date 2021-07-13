package cn.tedu.knows.sys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/sys")
public class TestController {
    @GetMapping
    public String say(){
        return "sys:Hello";
    }
}
