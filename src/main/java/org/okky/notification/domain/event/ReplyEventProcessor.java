package org.okky.notification.domain.event;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.Notification;
import org.okky.notification.domain.model.reply.ReplyPinnedNoti;
import org.okky.notification.domain.repository.NotiRepository;
import org.okky.notification.domain.service.ArticleProxy;
import org.okky.notification.domain.service.NotiAssembler;
import org.okky.notification.domain.service.ReplyProxy;
import org.okky.share.event.ReplyPinned;
import org.okky.share.event.ReplyWrote;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
class ReplyEventProcessor {
    NotiRepository repository;
    NotiAssembler assembler;
    ArticleProxy articleProxy;
    ReplyProxy replyProxy;

    @EventListener
    void when(ReplyWrote event) {
        String articleId = event.getArticleId();
        Article article = articleProxy.fetchArticle(articleId);

        int page = 0;
        List<String> replierIds = replyProxy.fetchReplierIds(articleId, page);
        while (replierIds.size() > 0) {
            List<Notification> notis = assembler.assemble(replierIds, event, article);
            repository.saveAll(notis);
            replierIds = replyProxy.fetchReplierIds(articleId, ++page);
        }
    }

    @EventListener
    void when(ReplyPinned event) {
        Article article = articleProxy.fetchArticle(event.getArticleId());
        ReplyPinnedNoti noti = new ReplyPinnedNoti(event, article);
        if (noti.didFixedOthers())
            repository.save(noti);
    }
}
