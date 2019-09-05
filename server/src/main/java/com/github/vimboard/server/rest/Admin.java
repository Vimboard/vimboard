package com.github.vimboard.server.rest;

import com.github.vimboard.server.config.BoardConfig;
import com.github.vimboard.server.service.ICityService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;

@Controller()
@RequestMapping("/admin.php")
public class Admin {

    private final BoardConfig boardConfig;
    private final Configuration freemarkerCfg;
    private final ICityService cityService;

    @Autowired
    public Admin(BoardConfig boardConfig, ICityService cityService, Configuration freemarkerCfg) {
        this.boardConfig = boardConfig;
        this.cityService = cityService;
        this.freemarkerCfg = freemarkerCfg;
    }

    @GetMapping("/")
    public ModelAndView index() {
        var cities = cityService.findAll();

        var params = new HashMap<String, Object>();
        params.put("boardConfig", boardConfig);
        params.put("cities", cities);

        return new ModelAndView("index", params);
    }

    @GetMapping("/write")
    String home() throws Exception {
        Writer file = new FileWriter(new File(boardConfig.getWww() + "/index.html"));
        Template template = freemarkerCfg.getTemplate("write.ftl");
        template.process(null, file);
        file.flush();
        file.close();
        return "write";
    }
}
