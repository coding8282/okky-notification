package org.okky.notification.domain.service;

import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.ReplyWroteNoti;
import org.okky.share.event.ReplyWrote;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE)
public class NotiAssembler {
    public List<ReplyWroteNoti> assemble(List<String> ownerIds, ReplyWrote event, Article article) {
        return ownerIds
                .stream()
                .map(ownerId -> ReplyWroteNoti
                        .builder()
                        .ownerId(ownerId)
                        .event(event)
                        .article(article)
                        .build())
                .filter(ReplyWroteNoti::isEligible)
                .collect(toList());
    }
}
