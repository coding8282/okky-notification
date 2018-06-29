package org.okky.notification.application;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.service.ReplyPinnedNotiService;
import org.okky.notification.domain.service.ReplyWroteNotiService;
import org.springframework.stereotype.Service;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class NotificationApplicationService {
    ReplyWroteNotiService replyWroteNotiService;
    ReplyPinnedNotiService replyPinnedNotiService;

    public void markRead(List<String> notificationIds) {
        replyWroteNotiService.markRead(notificationIds);
        replyPinnedNotiService.markRead(notificationIds);
    }

    public void markUnread(List<String> notificationIds) {
        replyWroteNotiService.markUnread(notificationIds);
        replyPinnedNotiService.markUnread(notificationIds);
    }

    public void markReadAll(String ownerId) {
        replyWroteNotiService.markReadAll(ownerId);
        replyPinnedNotiService.markReadAll(ownerId);
    }

    public void toggleRead(List<String> notificationIds) {
        replyWroteNotiService.toggleRead(notificationIds);
        replyPinnedNotiService.toggleRead(notificationIds);
    }

    public void remove(List<String> notificationIds) {
        replyWroteNotiService.remove(notificationIds);
        replyPinnedNotiService.remove(notificationIds);
    }
}
