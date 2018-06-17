package org.okky.notification.domain.event;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.ReplyWroteNoti;
import org.okky.notification.domain.repository.ReplyWroteNotiRepository;
import org.okky.notification.domain.service.ArticleProxy;
import org.okky.notification.domain.service.NotiAssembler;
import org.okky.notification.domain.service.ReplyProxy;
import org.okky.share.event.ReplyWrote;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Slf4j
class ReplyWroteEventProcessor {
    ReplyWroteNotiRepository repository;
    NotiAssembler assembler;
    ArticleProxy articleProxy;
    ReplyProxy replyProxy;

    @EventListener
    @SneakyThrows
    void when(ReplyWrote event) {
        String articleId = event.getArticleId();
        Article article = articleProxy.fetchArticle(articleId);

        int page = 0;
        List<String> replierIds = replyProxy.fetchReplierIds(articleId, page);
        while (replierIds.size() > 0) {
            List<ReplyWroteNoti> notis = assembler.assemble(replierIds, event, article);
            repository.saveAll(notis);
            replierIds = replyProxy.fetchReplierIds(articleId, ++page);
        }

        logger.info("Event: {}", event.getClass().getSimpleName());
    }
}
