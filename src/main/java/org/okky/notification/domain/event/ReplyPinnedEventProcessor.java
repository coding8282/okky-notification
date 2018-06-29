package org.okky.notification.domain.event;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.ReplyPinnedNoti;
import org.okky.notification.domain.repository.ReplyPinnedNotiRepository;
import org.okky.notification.domain.service.ArticleProxy;
import org.okky.share.event.ReplyPinned;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
class ReplyPinnedEventProcessor {
    ReplyPinnedNotiRepository repository;
    ArticleProxy articleProxy;

    @EventListener
    @SneakyThrows
    void when(ReplyPinned event) {
        Article article = articleProxy.fetchArticle(event.getArticleId());
        ReplyPinnedNoti noti = new ReplyPinnedNoti(event, article);
        repository.save(noti);
    }
}
