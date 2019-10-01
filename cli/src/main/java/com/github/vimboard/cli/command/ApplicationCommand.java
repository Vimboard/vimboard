package com.github.vimboard.cli.command;

import com.github.vimboard.cli.domain.DBVersion;
import com.github.vimboard.cli.service.SchemaService;
import com.github.vimboard.version.ApplicationVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ApplicationCommand {

    private final SchemaService schemaService;

    @Autowired
    public ApplicationCommand(SchemaService schemaService) {
        this.schemaService = schemaService;
    }

    @ShellMethod("Print version info.")
    public String version() {
        DBVersion dbVersion = schemaService.version();
        return "Database server: " + dbVersion.getServerVersion()
                + System.lineSeparator()
                + "Vimboard application: " + ApplicationVersion.get()
                + System.lineSeparator()
                + "Vimboard database: " + dbVersion.getSchemaVersion();
    }
}
