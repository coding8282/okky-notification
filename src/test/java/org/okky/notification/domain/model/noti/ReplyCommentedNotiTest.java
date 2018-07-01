package org.okky.notification.domain.model.noti;

import org.junit.Test;
import org.okky.notification.TestMother;
import org.okky.notification.domain.model.Reply;
import org.okky.share.event.ReplyCommented;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ReplyCommentedNotiTest extends TestMother {
    @Test
    public void wasCommentedByOthers_답글_작성자_자신이_코멘트를_남겼다면_false() {
        ReplyCommentedNoti noti = fixture("m", "m");

        assertFalse("답글 작성자 자신이 코멘트를 남겼다면 false여야 한다.", noti.wasCommentedByOthers());
    }

    @Test
    public void wasCommentedByOthers_답글_작성자가_아닌_다른_사람이_코멘트를_남겼다면_true() {
        ReplyCommentedNoti noti = fixture("m", "x");

        assertTrue("답글 작성자가 아닌 다른 사람이 코멘트를 남겼다면 true여야 한다.", noti.wasCommentedByOthers());
    }

    ReplyCommentedNoti fixture(String replierId, String commenterId) {
        ReplyCommented event = new ReplyCommented("i", "a", "b", commenterId, "n", 1L, null);
        Reply reply = new Reply("i", "a", replierId, "n");
        return new ReplyCommentedNoti(event, reply);
    }
}