package com.github.vimboard.command;

import com.github.vimboard.config.VimboardVersion;
import com.github.vimboard.domain.DBVersion;
import com.github.vimboard.repository.SchemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ApplicationCommand {

    private final SchemaRepository schemaRepository;

    @Autowired
    public ApplicationCommand(SchemaRepository schemaRepository) {
        this.schemaRepository = schemaRepository;
    }

    @ShellMethod("Print version info.")
    public String version() {
        DBVersion dbVersion = schemaRepository.version();
        return "Database server: " + dbVersion.getServerVersion()
                + System.lineSeparator()
                + "Vimboard application: " + VimboardVersion.get()
                + System.lineSeparator()
                + "Vimboard database: " + dbVersion.getSchemaVersion();
    }
}
