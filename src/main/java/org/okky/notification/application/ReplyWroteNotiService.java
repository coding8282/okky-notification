package org.okky.notification.application;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
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
        repository.findByIdIn(notificationIds)
                .stream()
                .forEach(this::markReadInternal);
    }

    public void markUnread(List<String> notificationIds) {
        repository.findByIdIn(notificationIds)
                .stream()
                .forEach(this::unmarkReadInternal);
    }

    public void markReadAll(String ownerId) {
        repository.findByOwnerId(ownerId)
                .stream()
                .forEach(this::markReadInternal);
    }

    public void toggleRead(List<String> notificationIds) {
        repository.findByIdIn(notificationIds)
                .stream()
                .forEach(this::toggleReadInternal);
    }

    public void remove(List<String> notificationIds) {
        repository.deleteByIdIn(notificationIds);
    }

    // -------------------------------------------------
    private void markReadInternal(ReplyWroteNoti noti) {
        noti.markRead();
        repository.save(noti);
    }

    private void unmarkReadInternal(ReplyWroteNoti noti) {
        noti.markUnread();
        repository.save(noti);
    }

    private void toggleReadInternal(ReplyWroteNoti noti) {
        noti.toggleRead();
        repository.save(noti);
    }
}
