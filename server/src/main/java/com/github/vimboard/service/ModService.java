package com.github.vimboard.service;

import com.github.vimboard.domain.Group;
import com.github.vimboard.domain.dashboard.User;
import com.github.vimboard.model.domain.UserModel;
import com.github.vimboard.repository.ModRepository;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ModService {

    private final ModRepository modRepository;
    private final SecurityService securityService;

    @Autowired
    public ModService(
            ModRepository modRepository,
            SecurityService securityService) {
        this.modRepository = modRepository;
        this.securityService = securityService;
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
                    .setPromoteToken(securityService.makeSecureLinkToken(
                            "/users/" + user.getId() + "/promote"))
                    .setDemoteToken(securityService.makeSecureLinkToken(
                            "/users/" + user.getId() + "/demote")));
        }
        return result;
    }

    public Group promote(Group current, boolean isPromote) {
        if (isPromote && !current.canBePromoted()
                || !isPromote && !current.canBeDemoted()) {
            return null;
        }
        final Group[] enumGroups = Group.values();
        final Group[] sortedGroups = new Group[enumGroups.length];
        System.arraycopy(enumGroups, 0, sortedGroups, 0, enumGroups.length);
        Arrays.sort(sortedGroups, (o1, o2) -> isPromote
                ? o1.getId() - o2.getId()
                : o2.getId() - o1.getId());
        for (Group group : sortedGroups) {
            if (isPromote && group.getId() > current.getId()
                    || !isPromote && group.getId() < current.getId()) {
                return group;
            }
        }
        return null;
    }
}
