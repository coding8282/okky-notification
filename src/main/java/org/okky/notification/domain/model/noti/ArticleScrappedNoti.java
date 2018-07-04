package org.okky.notification.domain.model.noti;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.IdGenerator;
import org.okky.notification.domain.model.Scrapper;
import org.okky.share.event.ArticleScrapped;

import static lombok.AccessLevel.PRIVATE;
import static org.okky.share.util.JsonUtil.toPrettyJson;

@NoArgsConstructor(access = PRIVATE)
@FieldDefaults(level = PRIVATE)
@Getter
public class ArticleScrappedNoti extends Notification {
    String articleId;
    String articleWriterId;
    String scrapperId;
    String scrapperName;
    long scrappedOn;

    public ArticleScrappedNoti(ArticleScrapped event, Article article, Scrapper scrapper) {
        super(IdGenerator.nextArticleScrappedNotiId(), article.getWriterId());
        this.articleId = event.getArticleId();
        this.articleWriterId = article.getWriterId();
        this.scrapperId = scrapper.getId();
        this.scrapperName = scrapper.getNickName();
        this.scrappedOn = event.getScrappedOn();
    }

    // ---------------------------------------------------
    public static void main(String[] args) {
        System.out.println(toPrettyJson(sample()));
    }

    public static ArticleScrappedNoti sample() {
        ArticleScrapped event = ArticleScrapped.sample();
        Article article = Article.sample();
        Scrapper scrapper = Scrapper.sample();
        return new ArticleScrappedNoti(event, article, scrapper);
    }

    /**
     * 다른 사람의 게시글을 스크랩했는지 여부.
     */
    public boolean wasScrappedByOthers() {
        return !articleWriterId.equals(scrapperId);
    }
}
