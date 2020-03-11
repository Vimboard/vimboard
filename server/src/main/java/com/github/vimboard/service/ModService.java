package com.github.vimboard.service;

import com.github.vimboard.domain.dashboard.User;
import com.github.vimboard.model.domain.UserModel;
import com.github.vimboard.repository.ModRepository;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModService {

    private final ModRepository modRepository;

    @Autowired
    public ModService(ModRepository modRepository) {
        this.modRepository = modRepository;
    }

    public List<UserModel> listUsers() {
        final var currentLocale = LocaleContextHolder.getLocale();
        final PrettyTime pt = new PrettyTime(currentLocale);
        final List<UserModel> result = new ArrayList<>();
        for (User user : modRepository.listUsers()) {
            result.add(new UserModel()
                    .setId(user.getId())
                    .setUsername(user.getUsername())
                    .setType(user.getType())
                    .setBoards(user.getBoards())
                    .setLast(user.getLast() == null
                            ? null : pt.format(user.getLast()))
                    .setAction(user.getAction())
                    .setCanBePromoted(user.getType().canBePromoted())
                    .setCanBeDemoted(user.getType().canBeDemoted())
                    .setPromoteToken("todo") // todo
                    .setDemoteToken("todo"));  // todo
        }
        return result;
    }
}