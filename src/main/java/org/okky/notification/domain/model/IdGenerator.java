package org.okky.notification.domain.model;

import java.util.UUID;

public interface IdGenerator {
    static String nextReplyWroteNotiId() {
        return "nr-" + uuid();
    }

    static String nextReplyPinnedNotiId() {
        return "np-" + uuid();
    }

    static String nextReplyCommentedNotiId() {
        return "nc-" + uuid();
    }

    static String nextEmotedNotiId() {
        return "ne-" + uuid();
    }

    static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15);
    }
}
