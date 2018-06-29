package org.okky.notification.domain.service;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.model.Notification;
import org.okky.notification.domain.model.ReplyWroteNoti;
import org.okky.notification.domain.repository.ReplyWroteNotiRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ReplyWroteNotiService {
    ReplyWroteNotiRepository repository;

    public void markRead(List<String> notificationIds) {
        List<ReplyWroteNoti> replyWroteNotis = repository.findByIdIn(notificationIds);
        replyWroteNotis.forEach(Notification::markRead);
        repository.saveAll(replyWroteNotis);
    }

    public void markUnread(List<String> notificationIds) {
        List<ReplyWroteNoti> replyWroteNotis = repository.findByIdIn(notificationIds);
        replyWroteNotis.forEach(Notification::markUnread);
        repository.saveAll(replyWroteNotis);
    }

    public void markReadAll(String ownerId) {
        List<ReplyWroteNoti> replyWroteNotis = repository.findByOwnerId(ownerId);
        replyWroteNotis.forEach(Notification::markRead);
        repository.saveAll(replyWroteNotis);
    }

    public void toggleRead(List<String> notificationIds) {
        List<ReplyWroteNoti> replyWroteNotis = repository.findByIdIn(notificationIds);
        replyWroteNotis.forEach(Notification::toggleRead);
        repository.saveAll(replyWroteNotis);
    }

    public void remove(List<String> notificationIds) {
        repository.deleteByIdIn(notificationIds);
    }
}
