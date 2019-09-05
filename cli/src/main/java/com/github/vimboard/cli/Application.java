package com.github.vimboard.cli;

import com.github.vimboard.cli.dao.SchemaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    private final SchemaDao schemaDao;

    @Autowired
    public Application(SchemaDao schemaDao) {
        this.schemaDao = schemaDao;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Startin application...");

        if (args.length == 0) {
            logger.error("USAGE");
            return;
        }

        switch (args[0]) {
            case "create-schema":
                System.out.println(schemaDao.getVersion());
                break;
            case "drop-schema":
                break;
            default:
                logger.error("USAGE");
        }
    }
}
