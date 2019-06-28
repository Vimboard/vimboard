package com.github.vimboard.rest;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

//@RestController
@Controller
public class Admin {

    private final Configuration freemarkerCfg;

    @Autowired
    public Admin(
            @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
            Configuration freemarkerCfg) {
        this.freemarkerCfg = freemarkerCfg;
    }

    @GetMapping("/admin.php")
    String home() throws Exception {
        Writer file = new FileWriter(new File("C:/Work/hello-freemarker.html"));
        Template template = freemarkerCfg.getTemplate("admin.ftl");
        template.process(null, file);
        file.flush();
        file.close();
        return "admin";
    }

//    @GetMapping("/city.php")
//    public ModelAndView city() {
//
//    }
}
