package org.okky.notification.domain.event;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.Scrapper;
import org.okky.notification.domain.model.noti.ArticleScrappedNoti;
import org.okky.notification.domain.repository.NotiRepository;
import org.okky.notification.domain.service.NotiProxy;
import org.okky.share.event.ArticleScrapped;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
class ArticleEventProcessor {
    NotiRepository repository;
    NotiProxy proxy;

    @EventListener
    void when(ArticleScrapped event) {
        Scrapper scrapper = proxy.fetchScrapper(event.getScrapperId());
        Article article = proxy.fetchArticle(event.getArticleId());
        ArticleScrappedNoti noti = new ArticleScrappedNoti(event, article, scrapper);
        if (noti.wasScrappedByOthers())
            repository.save(noti);
    }
}
