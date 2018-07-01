package org.okky.notification.domain.service;

import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.noti.Notification;
import org.okky.notification.domain.model.noti.ReplyWroteNoti;
import org.okky.share.event.ReplyWrote;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
public class NotiAssembler {
    public List<Notification> assemble(Set<String> ownerIds, ReplyWrote event, Article article) {
        return ownerIds
                .stream()
                .map(ownerId -> new ReplyWroteNoti(ownerId, event, article))
                .filter(noti -> !noti.wasRepliedByMyself())
                .collect(toList());
    }
}
