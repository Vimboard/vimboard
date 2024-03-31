package com.github.vimboard.service;

import com.github.vimboard.domain.ModLog;
import com.github.vimboard.model.ModLogModel;
import com.github.vimboard.repository.ModLogRepository;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModLogService {

    private final ModLogRepository modLogRepository;

    @Autowired
    public ModLogService(ModLogRepository modLogRepository) {
        this.modLogRepository = modLogRepository;
    }

    public List<ModLogModel> preview(int mod, long limit) {
        final var currentLocale = LocaleContextHolder.getLocale();
        final PrettyTime pt = new PrettyTime(currentLocale);
        final List<ModLogModel> result = new ArrayList<>();
        for (ModLog modLog : modLogRepository.preview(mod, limit)) {
            result.add(new ModLogModel()
                    .setMod(modLog.getMod())
                    .setIp(modLog.getIp())
                    .setBoard(modLog.getBoard())
                    .setTime(modLog.getTime())
                    .setLast(pt.format(modLog.getTime()))
                    .setText(modLog.getText()));
        }
        return result;
    }
}
