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
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CliApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CliApplication.class);

    private final ApplicationContext applicationContext;

    @Autowired
    public CliApplication(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
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
