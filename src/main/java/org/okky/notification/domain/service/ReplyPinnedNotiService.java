package org.okky.notification.domain.service;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.model.Notification;
import org.okky.notification.domain.model.ReplyPinnedNoti;
import org.okky.notification.domain.repository.ReplyPinnedNotiRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ReplyPinnedNotiService {
    ReplyPinnedNotiRepository repository;

    public void markRead(List<String> notificationIds) {
        List<ReplyPinnedNoti> replyPinnedNotis = repository.findByIdIn(notificationIds);
        replyPinnedNotis.forEach(Notification::markRead);
        repository.saveAll(replyPinnedNotis);
    }

    public void markUnread(List<String> notificationIds) {
        List<ReplyPinnedNoti> replyPinnedNotis = repository.findByIdIn(notificationIds);
        replyPinnedNotis.forEach(Notification::markUnread);
        repository.saveAll(replyPinnedNotis);
    }

    public void markReadAll(String ownerId) {
        List<ReplyPinnedNoti> replyPinnedNotis = repository.findByOwnerId(ownerId);
        replyPinnedNotis.forEach(Notification::markRead);
        repository.saveAll(replyPinnedNotis);
    }

    public void toggleRead(List<String> notificationIds) {
        List<ReplyPinnedNoti> replyPinnedNotis = repository.findByIdIn(notificationIds);
        replyPinnedNotis.forEach(Notification::toggleRead);
        repository.saveAll(replyPinnedNotis);
    }

    public void remove(List<String> notificationIds) {
        repository.deleteByIdIn(notificationIds);
    }
}
