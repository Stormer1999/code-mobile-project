package com.example.code_mobile.controller.api;

import com.example.code_mobile.util.DateUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

//@RestController
public class DemoController {

    DateUtils dateUtis;
    private final SayService sayService;

    DemoController(DateUtils dateUtis, @Qualifier("cat_rock") SayService sayService) {
        this.dateUtis = dateUtis;
        this.sayService = sayService;
    }

    @GetMapping("/")
    public String getToday() {
        return dateUtis.todayString();
    }

    @GetMapping("/say")
    public String say() {
        return sayService.say();
    }

}

interface SayService {
    String say();
}

@Component("cat_rock")
class Cat implements SayService {
    @Override
    public String say() {
        return "meowwww";
    }
}

@Component
class Dog implements SayService {
    @Override
    public String say() {
        return "booooowwwwwww";
    }
}
