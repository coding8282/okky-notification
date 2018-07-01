package org.okky.notification.application;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.model.noti.Notification;
import org.okky.notification.domain.repository.NotiRepository;
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
    NotiRepository repository;

    public List<Notification> findByOwnerId(String ownerId, Pageable pageable) {
        List<Notification> replyWroteNotis = repository.findByOwnerId(ownerId, pageable).getContent();
        List<Notification> replyPinnedNotis = repository.findByOwnerId(ownerId, pageable).getContent();
        return Stream
                .concat(replyWroteNotis.stream(), replyPinnedNotis.stream())
                .sorted()
                .limit(pageable.getPageSize())
                .collect(toList());
    }
}
