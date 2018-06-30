package org.okky.notification.application;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.model.Notification;
import org.okky.notification.domain.model.ReplyPinnedNoti;
import org.okky.notification.domain.model.ReplyWroteNoti;
import org.okky.notification.domain.repository.ReplyPinnedNotiRepository;
import org.okky.notification.domain.repository.ReplyWroteNotiRepository;
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
    ReplyWroteNotiRepository replyWroteNotiRepository;
    ReplyPinnedNotiRepository replyPinnedNotiRepository;

    public List<Notification> findByOwnerId(String ownerId, Pageable pageable) {
        List<ReplyWroteNoti> replyWroteNotis = replyWroteNotiRepository.findByOwnerId(ownerId, pageable).getContent();
        List<ReplyPinnedNoti> replyPinnedNotis = replyPinnedNotiRepository.findByOwnerId(ownerId, pageable).getContent();
        return Stream
                .concat(replyWroteNotis.stream(), replyPinnedNotis.stream())
                .sorted()
                .limit(pageable.getPageSize())
                .collect(toList());
    }
}
