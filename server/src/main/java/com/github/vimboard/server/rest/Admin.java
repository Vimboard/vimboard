package com.github.vimboard.server.rest;

import com.github.vimboard.server.config.BoardConfig;
import com.github.vimboard.server.service.ICityService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;

@Controller()
@RequestMapping("/admin.php")
public class Admin {

    private static final Logger logger = LoggerFactory.getLogger(Admin.class);

    private final ApplicationContext applicationContext;

    private final BoardConfig boardConfig;
    private final Configuration freemarkerCfg;

    private final ICityService cityService;

    @Autowired
    public Admin(
            ApplicationContext applicationContext,
            BoardConfig boardConfig,
            ICityService cityService,
            Configuration freemarkerCfg) {
        this.applicationContext = applicationContext;
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

    @GetMapping("/shutdown")
    public void shutdown() {
        logger.info("Exit application with success");
        final int actualExitCode = SpringApplication.exit(
                applicationContext, (ExitCodeGenerator) () -> 0);
        System.exit(actualExitCode);
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
