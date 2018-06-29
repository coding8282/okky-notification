package org.okky.notification.domain.model;

import java.util.UUID;

interface IdPrefixGenerator {
    static String nextReplyWroteNotiId() {
        return "nr" + uuid();
    }

    static String nextReplyPinnedNotiId() {
        return "np-" + uuid();
    }

    static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15);
    }
}
