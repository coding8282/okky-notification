package org.okky.notification.domain.event;

import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.Scrapper;
import org.okky.notification.domain.model.noti.ArticleScrappedNoti;
import org.okky.share.event.ArticleScrapped;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class ArticleEventProcessor extends EventProcessor {
    @EventListener
    void when(ArticleScrapped event) {
        Scrapper scrapper = proxy.fetchScrapper(event.getScrapperId());
        Article article = proxy.fetchArticle(event.getArticleId());
        ArticleScrappedNoti noti = new ArticleScrappedNoti(event, article, scrapper);
        if (noti.wasScrappedByOthers())
            repository.save(noti);
    }
}
