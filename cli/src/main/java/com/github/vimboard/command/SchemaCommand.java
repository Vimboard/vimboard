package com.github.vimboard.command;

import com.github.vimboard.service.SchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SchemaCommand {

    private final SchemaService schemaService;

    @Autowired
    public SchemaCommand(SchemaService schemaService) {
        this.schemaService = schemaService;
    }

    @ShellMethod(key = "schema-create", value = "Create vimboard database schema.")
    public void create() {
        schemaService.create();
    }

    @ShellMethod(key = "schema-drop", value = "Drop vimboard database schema.")
    public void drop() {
        schemaService.drop();
    }
}
