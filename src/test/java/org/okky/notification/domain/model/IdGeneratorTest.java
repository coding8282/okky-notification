package org.okky.notification.domain.model;

import org.junit.Test;
import org.okky.notification.TestMother;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.okky.notification.domain.model.IdGenerator.nextReplyPinnedNotiId;
import static org.okky.notification.domain.model.IdGenerator.nextReplyWroteNotiId;

public class IdGeneratorTest extends TestMother {
    @Test
    public void nextReplyWroteNotiId_nr로_시작해야_함() {
        assertThat("ReplyWroteNoti 아이디는 nr-로 시작해야 한다.", nextReplyWroteNotiId(), startsWith("nr-"));
    }

    @Test
    public void nextReplyWroteNotiId_18자여야_함() {
        assertThat("ReplyWroteNoti 아이디는 18자여야 한다.", nextReplyWroteNotiId().length(), is(18));
    }

    @Test
    public void nextReplyPinnedNotiId_np로_시작해야_함() {
        assertThat("ReplyWroteNoti 아이디는 np-로 시작해야 한다.", nextReplyPinnedNotiId(), startsWith("np-"));
    }

    @Test
    public void nextReplyPinnedNotiId_18자여야_함() {
        assertThat("ReplyPinnedNoti 아이디는 18자여야 한다.", nextReplyPinnedNotiId().length(), is(18));
    }
}