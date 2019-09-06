package com.github.vimboard.cli;

import com.github.vimboard.cli.dao.SchemaDao;
import com.github.vimboard.cli.domain.DBVersion;
import com.github.vimboard.version.ApplicationVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner, ExitCodeGenerator {

    private int exitCode = 0;
    private final SchemaDao schemaDao;

    @Autowired
    public Application(SchemaDao schemaDao) {
        this.schemaDao = schemaDao;
    }

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(
                SpringApplication.run(Application.class, args)));
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }

    @Override
    public void run(String... args) {
        if (args.length != 1) {
            doPrintUsage();
            exitCode = 1;
            return;
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
            default:
                doPrintUsage();
                exitCode = 1;
        }
    }

    private void doCreateSchema() {
        schemaDao.createSchema();
    }

    private void doDropSchema() {
        schemaDao.dropSchema();
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
}
