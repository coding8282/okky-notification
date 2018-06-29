package org.okky.notification.application;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.model.Notification;
import org.okky.notification.domain.model.ReplyPinnedNoti;
import org.okky.notification.domain.model.ReplyWroteNoti;
import org.okky.notification.domain.repository.ReplyPinnedNotiRepository;
import org.okky.notification.domain.repository.ReplyWroteNotiRepository;
import org.okky.notification.domain.service.ReplyPinnedNotiService;
import org.okky.notification.domain.service.ReplyWroteNotiService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class NotificationQueryService {
    ReplyWroteNotiService replyWroteNotiService;
    ReplyPinnedNotiService replyPinnedNotiService;
    ReplyWroteNotiRepository replyWroteNotiRepository;
    ReplyPinnedNotiRepository replyPinnedNotiRepository;

    public List<Notification> findByOwnerId(String ownerId, Pageable pageable) {
        List<ReplyWroteNoti> replyWroteNotis = replyWroteNotiRepository.findByOwnerId(ownerId, pageable).getContent();
        List<ReplyPinnedNoti> replyPinnedNotis = replyPinnedNotiRepository.findByOwnerId(ownerId, pageable).getContent();
        Stream<Notification> notis = Stream.concat(replyPinnedNotis.stream(), replyPinnedNotis.stream());
        return notis.collect(toList());
    }

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
