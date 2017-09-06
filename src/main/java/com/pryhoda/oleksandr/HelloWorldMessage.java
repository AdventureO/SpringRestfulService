package com.pryhoda.oleksandr;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HelloWorldMessage {

        @RequestMapping("/")
        public String showIndex() {
            return "index";
        }

        @RequestMapping("/hello")
        public String sayHello() {
            return "hi";
        }
    }
