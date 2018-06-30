package org.okky.notification.application;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.model.Notification;
import org.okky.notification.domain.repository.NotiRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class NotificationApplicationService {
    NotiRepository repository;

    public void markRead(List<String> notificationIds) {
        List<Notification> notis = repository.findByIdIn(notificationIds);
        notis.forEach(Notification::markRead);
        repository.saveAll(notis);
    }

    public void markUnread(List<String> notificationIds) {
        List<Notification> notis = repository.findByIdIn(notificationIds);
        notis.forEach(Notification::markUnread);
        repository.saveAll(notis);
    }

    public void markReadAll(String ownerId) {
        List<Notification> replyPinnedNotis = repository.findByOwnerId(ownerId);
        replyPinnedNotis.forEach(Notification::markRead);
        repository.saveAll(replyPinnedNotis);
    }

    public void toggleRead(List<String> notificationIds) {
        List<Notification> notis = repository.findByIdIn(notificationIds);
        notis.forEach(Notification::toggleRead);
        repository.saveAll(notis);
    }

    public void remove(List<String> notificationIds) {
        repository.deleteByIdIn(notificationIds);
    }
}
