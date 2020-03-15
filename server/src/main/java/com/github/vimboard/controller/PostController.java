package com.github.vimboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/post.php")
public class PostController {

    @GetMapping("")
    String home() {
        return "POST: .";
    }

    @GetMapping("/")
    String index() {
        return "POST: /";
    }

    @GetMapping("/foo")
    String foo() {
        return "POST: foo";
    }

    @GetMapping("/bar")
    String bar() {
        return "POST: bar";
    }
}
