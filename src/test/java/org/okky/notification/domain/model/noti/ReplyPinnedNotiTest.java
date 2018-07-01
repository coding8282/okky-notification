package org.okky.notification.domain.model.noti;

import org.junit.Test;
import org.okky.notification.TestMother;
import org.okky.notification.domain.model.Article;
import org.okky.share.event.ReplyPinned;

import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ReplyPinnedNotiTest extends TestMother {
    @Test
    public void wasFixedByOthers_게시글_작성자와_답글_작성자가_같다면_false() {
        ReplyPinnedNoti noti = fixture("m1", "m1");

        assertFalse("게시글 작성자와 답글 작성자의 id가 같으므로 false여야 한다.", noti.wasFixedByOthers());
    }

    @Test
    public void wasFixedByOthers_게시글_작성자와_답글_작성자가_다르다면_true() {
        ReplyPinnedNoti noti = fixture("m1", "m9");

        assertTrue("게시글 작성자와 답글 작성자의 id가 같으므로 true여야 한다.", noti.wasFixedByOthers());
    }

    // -------------------------------------
    ReplyPinnedNoti fixture(String replierId, String articleWriterId) {
        ReplyPinned event = new ReplyPinned("r", "a", replierId, "n", "pm", currentTimeMillis());
        Article article = new Article("a", articleWriterId, "n");
        return new ReplyPinnedNoti(event, article);
    }
}