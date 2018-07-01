package org.okky.notification.domain.model.noti;

import org.junit.Test;
import org.okky.notification.TestMother;
import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.Emoter;
import org.okky.share.event.Emoted;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmotedNotiTest extends TestMother {
    @Test
    public void didEmotedByOthers_게시글_작성자가_자신의_게시글을_공감했다면_false() {
        EmotedNoti noti = fixture("m", "m");

        assertFalse("게시글 작성자가 자신의 게시글을 공감했다면 false여야 한다.", noti.didEmotedByOthers());
    }

    @Test
    public void didEmotedByOthers_다른_사람이_게시글_작성자의_게시글을_공감했다면_true() {
        EmotedNoti noti = fixture("m", "x");

        assertTrue("게시글 작성자가 자신의 게시글을 공감했다면 false여야 한다.", noti.didEmotedByOthers());
    }

    public EmotedNoti fixture(String emoterId, String writerId) {
        Emoted event = new Emoted("r-1", "a-1", emoterId, 1L, "LIKE");
        Article article = new Article("a-1", writerId, "coding8282");
        Emoter emoter = new Emoter("m-1", "coding8282");
        return new EmotedNoti(event, article, emoter);
    }
}