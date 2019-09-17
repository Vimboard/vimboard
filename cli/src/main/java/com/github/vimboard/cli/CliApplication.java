package com.github.vimboard.cli;

import com.github.vimboard.cli.domain.User;
import com.github.vimboard.cli.service.SchemaService;
import com.github.vimboard.cli.domain.DBVersion;
import com.github.vimboard.cli.service.UserService;
import com.github.vimboard.version.ApplicationVersion;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.BadSqlGrammarException;

@SpringBootApplication
public class CliApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CliApplication.class);

    private final ApplicationContext applicationContext;
    private final SchemaService schemaService;
    private final UserService userService;

    @Autowired
    public CliApplication(
            ConfigurableApplicationContext applicationContext,
            SchemaService schemaService,
            UserService userService) {
        this.applicationContext = applicationContext;
        this.schemaService = schemaService;
        this.userService = userService;
    }

    public static void main(String[] args) {
        try {
            SpringApplication.run(CliApplication.class, args);
        } catch (Exception ex) {
            if (ex.getCause() instanceof MyBatisSystemException
                    || ex.getCause() instanceof BadSqlGrammarException) {
                System.out.println("Error: " + ex.getCause().getMessage());
            } else {
                System.out.println("Error: " + ex.getMessage());
            }
            System.exit(1);
        }
    }

    @Override
    public void run(String... args) {
        logger.info("Run application");

        if (args.length == 0) {
            doPrintUsage();
            exit();
        }

        switch (args[0]) {
            case "help":
                validateArgsCount(args, 1);
                doPrintUsage();
                break;
            case "schema-create":
                validateArgsCount(args, 1);
                schemaService.create();
                break;
            case "schema-drop":
                validateArgsCount(args, 1);
                schemaService.drop();
                break;
            case "user-alter":
                validateArgsCount(args, 3);
                userService.alter(args[1], args[2]);
                break;
            case "user-create":
                validateArgsCount(args, 3);
                userService.create(args[1], args[2]);
                break;
            case "user-drop":
                validateArgsCount(args, 2);
                userService.drop(args[1]);
                break;
            case "user-list":
                validateArgsCount(args, 1);
                doPrintAllUsers();
                break;
            case "version":
                validateArgsCount(args, 1);
                doPrintVersion();
                break;
            default:
                doPrintUsage();
                exit();
        }

        logger.info("Complete application with success");
    }

    private void doPrintAllUsers() {
        for (User user : userService.list()) {
            System.out.println(user.getUsername());
        }
    }

    private void doPrintUsage() {
        System.out.println("Usage: vimboard-cli <command>");
        System.out.println("Available commands:");
        System.out.println("   help");
        System.out.println("   schema-create");
        System.out.println("   schema-drop");
        System.out.println("   user-alter <username> <password>");
        System.out.println("   user-create <username> <password>");
        System.out.println("   user-drop <username>");
        System.out.println("   version");
    }

    private void doPrintVersion() {
        DBVersion dbVersion = schemaService.version();
        System.out.println("Database server: " + dbVersion.getServerVersion());
        System.out.println("Vimboard application: " + ApplicationVersion.get());
        System.out.println("Vimboard database: " + dbVersion.getSchemaVersion());
    }

    private void exit() {
        logger.info("Exit application with fail");
        final int actualExitCode = SpringApplication.exit(
                applicationContext, (ExitCodeGenerator) () -> 1);
        System.exit(actualExitCode);
    }

    private void validateArgsCount(String[] args, int expectedCount) {
        if (args.length != expectedCount) {
            doPrintUsage();
            exit();
        }
    }
}
