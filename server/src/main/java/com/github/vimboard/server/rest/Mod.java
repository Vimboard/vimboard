package com.github.vimboard.server.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/mod.php")
public class Mod {

    @GetMapping("/foo")
    String foo() {
        return "MOD: foo";
    }

    @GetMapping("/bar")
    String bar() {
        return "MOD: bar";
    }
}
