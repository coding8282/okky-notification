package org.okky.notification.domain.event;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.noti.Notification;
import org.okky.notification.domain.model.noti.ReplyPinnedNoti;
import org.okky.notification.domain.repository.NotiRepository;
import org.okky.notification.domain.service.NotiAssembler;
import org.okky.notification.domain.service.NotiProxy;
import org.okky.share.event.ReplyPinned;
import org.okky.share.event.ReplyWrote;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
class ReplyEventProcessor {
    NotiRepository repository;
    NotiAssembler assembler;
    NotiProxy proxy;

    @EventListener
    void when(ReplyWrote event) {
        String articleId = event.getArticleId();
        Article article = proxy.fetchArticle(articleId);
        Set<String> ownerIds = proxy.fetchReplierIds(articleId);
        ownerIds.add(article.getWriterId());
        List<Notification> notis = assembler.assemble(ownerIds, event, article);
        repository.saveAll(notis);
    }

    @EventListener
    void when(ReplyPinned event) {
        Article article = proxy.fetchArticle(event.getArticleId());
        ReplyPinnedNoti noti = new ReplyPinnedNoti(event, article);
        if (noti.didFixedOthers())
            repository.save(noti);
    }
}
