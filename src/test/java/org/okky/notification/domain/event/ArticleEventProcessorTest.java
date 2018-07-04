package org.okky.notification.domain.event;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.Scrapper;
import org.okky.notification.domain.model.noti.ArticleScrappedNoti;
import org.okky.share.event.ArticleScrapped;

import static lombok.AccessLevel.PRIVATE;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@FieldDefaults(level = PRIVATE)
public class ArticleEventProcessorTest extends EventProcessorTestMother {
    @InjectMocks
    ArticleEventProcessor processor;

    @Test
    public void ArticleScrapped_게시글_작성자_본인이_스크랩한_경우_알림하지_않음() {
        ArticleScrapped event = spy(ArticleScrapped.sample());
        Scrapper scrapper = spy(Scrapper.sample());
        Article article = spy(Article.sample());
        when(article.getWriterId()).thenReturn("m");
        when(scrapper.getId()).thenReturn("m");
        when(proxy.fetchScrapper(event.getScrapperId())).thenReturn(scrapper);
        when(proxy.fetchArticle(event.getArticleId())).thenReturn(article);

        processor.when(event);

        InOrder o = inOrder(proxy, repository);
        o.verify(proxy).fetchScrapper(event.getScrapperId());
        o.verify(proxy).fetchArticle(event.getArticleId());
        o.verifyNoMoreInteractions();
    }

    @Test
    public void ArticleScrapped_다른_사람이_스크랩한_경우_알림() {
        ArticleScrapped event = spy(ArticleScrapped.sample());
        Scrapper scrapper = spy(Scrapper.sample());
        Article article = spy(Article.sample());
        when(article.getWriterId()).thenReturn("m");
        when(scrapper.getId()).thenReturn("x");
        when(proxy.fetchScrapper(event.getScrapperId())).thenReturn(scrapper);
        when(proxy.fetchArticle(event.getArticleId())).thenReturn(article);

        processor.when(event);

        InOrder o = inOrder(proxy, repository);
        o.verify(proxy).fetchScrapper(event.getScrapperId());
        o.verify(proxy).fetchArticle(event.getArticleId());
        o.verify(repository).save(isA(ArticleScrappedNoti.class));
        o.verifyNoMoreInteractions();
    }
}