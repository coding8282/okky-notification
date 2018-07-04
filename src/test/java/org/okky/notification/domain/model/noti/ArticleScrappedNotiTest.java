package org.okky.notification.domain.model.noti;

import org.junit.Test;
import org.okky.notification.TestMother;
import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.Scrapper;
import org.okky.share.event.ArticleScrapped;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArticleScrappedNotiTest extends TestMother {
    @Test
    public void wasScrappedByOthers_게시글_작성자와_스크랩한_사용자가_같다면_false() {
        ArticleScrappedNoti noti = fixture("m1", "m1");

        assertFalse("게시글 작성자와 스크랩한 사용자가 같으므로 false여야 한다.", noti.wasScrappedByOthers());
    }

    @Test
    public void wasScrappedByOthers_게시글_작성자와_스크랩한_사용자가_다르면_true() {
        ArticleScrappedNoti noti = fixture("m1", "m2");

        assertTrue("게시글 작성자와 스크랩한 사용자가 다르므로 true여야 한다.", noti.wasScrappedByOthers());
    }

    ArticleScrappedNoti fixture(String w, String s) {
        ArticleScrapped event = new ArticleScrapped("r-1", "a-1", "s-1", 0L);
        Article article = new Article("a-1", w, "coding8282");
        Scrapper scrapper = new Scrapper(s, "m-3", "n", "m", "d", false, 0L);
        return new ArticleScrappedNoti(event, article, scrapper);
    }
}