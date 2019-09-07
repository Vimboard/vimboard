package com.github.vimboard.cli;

import com.github.vimboard.cli.dao.SchemaDao;
import com.github.vimboard.cli.domain.DBVersion;
import com.github.vimboard.starter.FooConfig;
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

    private final FooConfig fooConfig;

    private final ApplicationContext applicationContext;
    private final SchemaDao schemaDao;

    @Autowired
    public CliApplication(
            FooConfig fooConfig,
            ConfigurableApplicationContext applicationContext,
            SchemaDao schemaDao) {
        this.fooConfig = fooConfig;
        this.applicationContext = applicationContext;
        this.schemaDao = schemaDao;
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

        if (args.length != 1) {
            doPrintUsage();
            exit();
        }

        switch (args[0]) {
            case "create-schema":
                doCreateSchema();
                break;
            case "drop-schema":
                doDropSchema();
                break;
            case "help":
                doPrintUsage();
                break;
            case "version":
                doPrintVersion();
                break;
            case "foo":
                doPrintFoo();
                break;
            default:
                doPrintUsage();
                exit();
        }

        logger.info("Complete application with success");
    }

    private void doCreateSchema() {
        schemaDao.createSchema();
    }

    private void doDropSchema() {
        schemaDao.dropSchema();
    }

    private void doPrintFoo() {
        System.out.println("Foo:");
        System.out.println("  - 1: " + fooConfig.getVar1());
        System.out.println("  - 2: " + fooConfig.getVar2());
    }

    private void doPrintUsage() {
        System.out.println("Usage: vimboard-cli <command>");
        System.out.println("Available commands:");
        System.out.println("   create-schema");
        System.out.println("   drop-schema");
        System.out.println("   help");
        System.out.println("   version");
    }

    private void doPrintVersion() {
        DBVersion dbVersion = schemaDao.getVersion();
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
}
