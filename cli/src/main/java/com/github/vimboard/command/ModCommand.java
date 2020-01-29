package com.github.vimboard.command;

import com.github.vimboard.domain.Group;
import com.github.vimboard.domain.Mod;
import com.github.vimboard.repository.ModRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Arrays;

@ShellComponent
public class ModCommand {

    private final ModRepository modRepository;

    @Autowired
    public ModCommand(ModRepository modRepository) {
        this.modRepository = modRepository;
    }

    @ShellMethod(key = "mod-alter", value = "Alter moderator.")
    public String alter(String username, String password,
            Group type, String boards) {
        String[] boardsArr = boards.isEmpty()
                ? new String[] {}
                : boards.split(",");
        Mod mod = modRepository.alter(username, password, type, boardsArr);
        if (mod == null) {
            return "Mod not found";
        }
        return modToString(mod);
    }

    @ShellMethod(key = "mod-create", value = "Create moderator.")
    public String create(String username, String password,
            Group type, String boards) {
        String[] boardsArr = boards.isEmpty()
                ? new String[] {}
                : boards.split(",");
        Mod mod = modRepository.create(username, password, type, boardsArr);
        if (mod == null) {
            return "User not found";
        }
        return modToString(mod);
    }

    @ShellMethod(key = "mod-drop", value = "Drop moderator.")
    public String drop(String username) {
        Mod mod = modRepository.drop(username);
        if (mod == null) {
            return "User not found";
        }
        return modToString(mod);
    }

    @ShellMethod(key = "mod-list", value = "List all moderators.")
    public String list() {
        StringBuilder sb = new StringBuilder();
        for (Mod mod : modRepository.list()) {
            if (sb.length() > 0) {
                sb.append(System.lineSeparator());
            }
            sb.append(modToString(mod));
        }
        return sb.toString();
    }

    private String modToString(Mod mod) {
        return mod.getId() + "(" + mod.getUsername() + ", "
                + mod.getType() + ", "
                + Arrays.toString(mod.getBoards()) + ")";
    }
}
