package com.github.vimboard.command;

import com.github.vimboard.repository.SchemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SchemaCommand {

    private final SchemaRepository schemaRepository;

    @Autowired
    public SchemaCommand(SchemaRepository schemaRepository) {
        this.schemaRepository = schemaRepository;
    }

    @ShellMethod(key = "schema-create", value = "Create vimboard database schema.")
    public void create() {
        schemaRepository.create();
    }

    @ShellMethod(key = "schema-drop", value = "Drop vimboard database schema.")
    public void drop() {
        schemaRepository.drop();
    }
}
