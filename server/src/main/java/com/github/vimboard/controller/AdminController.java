package com.github.vimboard.controller;

import com.github.vimboard.config.VimboardProperties;
import com.github.vimboard.domain.Board;
import com.github.vimboard.service.ICityService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;

@Controller
@RequestMapping("/admin.php")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final ApplicationContext applicationContext;
    private final ICityService cityService;
    private final Configuration freemarkerCfg;
    private final VimboardProperties vimboardProperties;

    @Autowired
    public AdminController(
            ApplicationContext applicationContext,
            ICityService cityService,
            Configuration freemarkerCfg,
            VimboardProperties vimboardProperties) {
        this.applicationContext = applicationContext;
        this.cityService = cityService;
        this.freemarkerCfg = freemarkerCfg;
        this.vimboardProperties = vimboardProperties;
    }

    @GetMapping("/shutdown")
    public void shutdown() {
        // TODO: log user name
        logger.info("Exit application with success");
        final int actualExitCode = SpringApplication.exit(
                applicationContext, (ExitCodeGenerator) () -> 0);
        System.exit(actualExitCode);
    }

    @GetMapping("/write")
    @Deprecated
    String home() throws Exception {
        Writer file = new FileWriter(new File(vimboardProperties.getWww() + "/index.html"));
        Template template = freemarkerCfg.getTemplate("write.ftl");
        template.process(null, file);
        file.flush();
        file.close();
        return "write";
    }

    @GetMapping("/city")
    @Deprecated
    // TODO remove
    public ModelAndView write2() {
        var cities = cityService.findAll();

        var params = new HashMap<String, Object>();
        params.put("cities", cities);

        final Board board2 = new Board();
        board2.setTitle("");
        params.put("board2", board2);

        final Board board3 = new Board();
        board3.setTitle("BOARD #3");
        params.put("board3", board3);


        return new ModelAndView("city", params);
    }
}
