package com.github.vimboard.command;

import com.github.vimboard.config.SettingsBean;
import com.github.vimboard.domain.DBVersion;
import com.github.vimboard.repository.SchemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ApplicationCommand {

    private final SchemaRepository schemaRepository;
    private final SettingsBean settingsBean;

    @Autowired
    public ApplicationCommand(
            SchemaRepository schemaRepository,
            SettingsBean settingsBean) {
        this.schemaRepository = schemaRepository;
        this.settingsBean = settingsBean;
    }

    @ShellMethod("Print version info.")
    public String version() {
        DBVersion dbVersion = schemaRepository.version();
        return "Database server: " + dbVersion.getServerVersion()
                + System.lineSeparator()
                + "Vimboard application: " + settingsBean.getAll().getVersion()
                + System.lineSeparator()
                + "Vimboard database: " + dbVersion.getSchemaVersion();
    }
}
