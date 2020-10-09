package com.github.vimboard.inc;

import com.github.vimboard.config.settings.VimboardSettings;
import com.github.vimboard.domain.QueueDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncQueue {

    private final VimboardSettings settings;

    private IncLock lock;

    @Autowired
    public IncQueue(VimboardSettings settings, final String key) {
        this.settings = settings;
        if (settings.getAll().getQueue().getEnabled().equals(QueueDriver.FS)) {
            lock = new IncLock(key);
        }
    }
}
