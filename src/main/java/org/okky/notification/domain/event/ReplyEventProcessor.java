package org.okky.notification.domain.event;

import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.noti.Notification;
import org.okky.notification.domain.model.noti.ReplyPinnedNoti;
import org.okky.share.event.ReplyPinned;
import org.okky.share.event.ReplyWrote;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
class ReplyEventProcessor extends EventProcessor {
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
        if (noti.wasFixedByOthers())
            repository.save(noti);
    }
}
