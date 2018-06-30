package org.okky.notification.domain.model;

import org.junit.Test;
import org.okky.notification.TestMother;
import org.okky.notification.domain.model.reply.ReplyWroteNoti;
import org.okky.share.event.ReplyWrote;

import static java.lang.System.currentTimeMillis;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.okky.notification.domain.model.reply.ReplyWroteNotiContext.*;

public class ReplyWroteNotiTest extends TestMother {
    @Test
    public void body는_최대_100자까지만_끊음() {
        ReplyWrote event = eventFixture();
        Article article = articleFixture();
        ReplyWroteNoti noti = new ReplyWroteNoti("o-1", event, article);

        assertThat("최대 100자까지만 보관한다.", noti.getReplyBody().length(), is(lessThanOrEqualTo(100)));
    }

    @Test
    public void self_context_확인() {
        ReplyWrote event = eventFixture();
        Article article = articleFixture();
        ReplyWroteNoti noti = new ReplyWroteNoti("m-1", event, article);

        assertThat("context는 SELF여야 함", noti.getContext(), is(SELF));
    }

    @Test
    public void YOURS_context_확인() {
        ReplyWrote event = eventFixture();
        Article article = articleFixture();
        ReplyWroteNoti noti = new ReplyWroteNoti("m-9", event, article);

        assertThat("context는 YOURS여야 함", noti.getContext(), is(YOURS));
    }

    @Test
    public void EACH_OTHER_context_확인() {
        ReplyWrote event = eventFixture();
        Article article = articleFixture();
        ReplyWroteNoti noti = new ReplyWroteNoti("m-100", event, article);

        assertThat("context는 YOURS여야 함", noti.getContext(), is(EACH_OTHER));
    }

    @Test
    public void ADVISORY_context_확인() {
        ReplyWrote event = eventFixture();
        Article article = articleFixture("m-1");
        ReplyWroteNoti noti = new ReplyWroteNoti("m-3", event, article);

        assertThat("context는 ADVISORY여야 함", noti.getContext(), is(ADVISORY));
    }

    // ------------------------------------
    private ReplyWrote eventFixture() {
        String id = "r-1";
        String articleId = "a-1";
        String body = "I believe the problem is that JUnit comes bundled with an older copy of Hamcrest (1.1) as signatures in later version of"; //120자
        String replierId = "m-1";
        String replierName = "coding8282";
        long repliedOn = currentTimeMillis();
        Long updatedOn = null;
        Long acceptedOn = null;
        ReplyWrote event = new ReplyWrote(id, articleId, body, replierId, replierName, repliedOn, updatedOn, acceptedOn);
        return event;
    }

    private Article articleFixture() {
        String id = "a-1";
        String writerId = "m-9";
        String writerName = "coding8282";
        Article article = new Article(id, writerId, writerName);
        return article;
    }

    private Article articleFixture(String writerId) {
        String id = "a-1";
        String writerName = "coding8282";
        Article article = new Article(id, writerId, writerName);
        return article;
    }
}