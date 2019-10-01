package com.github.vimboard.cli.command;

import com.github.vimboard.cli.domain.User;
import com.github.vimboard.cli.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class UserCommand {

    private final UserService userService;

    @Autowired
    public UserCommand(UserService userService) {
        this.userService = userService;
    }

    @ShellMethod(key = "user-alter", value = "Alter user.")
    public String alter(String username, String password) {
        User user = userService.alter(username, password);
        if (user == null) {
            return "User not found";
        }
        return userToString(user);
    }

    @ShellMethod(key = "user-create", value = "value = Create user.")
    public String create(String username, String password) {
        User user = userService.create(username, password);
        if (user == null) {
            return "User not found";
        }
        return userToString(user);
    }

    @ShellMethod(key = "user-drop", value = "Drop user.")
    public String drop(String username) {
        User user = userService.drop(username);
        if (user == null) {
            return "User not found";
        }
        return userToString(user);
    }

    @ShellMethod(key = "user-list", value = "List all users")
    public String list() {
        StringBuilder sb = new StringBuilder();
        for (User user : userService.list()) {
            if (sb.length() > 0) {
                sb.append(System.lineSeparator());
            }
            sb.append(userToString(user));
        }
        return sb.toString();
    }

    private String userToString(User user) {
        return user.getUsername() + "(" + user.getId() + ")";
    }
}
