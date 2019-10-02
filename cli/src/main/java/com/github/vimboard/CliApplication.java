package com.github.vimboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CliApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CliApplication.class);

    private final ApplicationContext applicationContext;

    @Autowired
    public CliApplication(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static void main(String[] args) {
        SpringApplication.run(CliApplication.class, args);
    }

    @Override
    public void run(String... args) {
        logger.info("Complete application with success");
    }

    @Deprecated
    private void exit() {
        logger.info("Exit application with fail");
        final int actualExitCode = SpringApplication.exit(
                applicationContext, (ExitCodeGenerator) () -> 1);
        System.exit(actualExitCode);
    }
}
