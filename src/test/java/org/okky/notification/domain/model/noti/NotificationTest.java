package org.okky.notification.domain.model.noti;

import org.junit.Test;
import org.okky.notification.TestMother;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class NotificationTest extends TestMother {
    @Test
    public void Notification모델_초기값_확인() {
        Notification noti = fixture();

        assertThat("read 초기값은 false.", noti.isRead(), is(false));
        assertThat("hidden 초기값은 false.", noti.isHidden(), is(false));
        assertThat("readOn 초기값은 null.", noti.getReadOn(), is(nullValue()));
        assertThat("hiddenOn 초기값은 null.", noti.getHiddenOn(), is(nullValue()));
        assertThat("notifiedOn 초기값은 timestamp이다.", noti.getNotifiedOn(), is(notNullValue()));
        assertThat("메시지 소유자 아이디는 필수.", noti.getOwnerId(), is("o-1"));
        assertThat("이벤트명은 클래스명과 같음.", noti.getEvent(), is("Notification"));
    }

    @Test
    public void markRead_값_확인() {
        Notification noti = fixture();

        assertThat("read 초기값은 false.", noti.isRead(), is(false));
        assertThat("readOn 초기값은 null.", noti.getReadOn(), is(nullValue()));

        noti.markRead();

        assertThat("marking 이후에는 true.", noti.isRead(), is(true));
        assertThat("marking 이후에는 적절한 timestamp.", noti.getReadOn(), is(notNullValue()));
    }

    @Test
    public void markUnread_값_확인() {
        Notification noti = fixture();
        noti.markRead();

        assertThat("marking 이후에는 true.", noti.isRead(), is(true));
        assertThat("marking 이후에는 적절한 timestamp.", noti.getReadOn(), is(notNullValue()));

        noti.markUnread();

        assertThat("unmarking 이후에는 false.", noti.isRead(), is(false));
        assertThat("unmarking 이후에는 null.", noti.getReadOn(), is(nullValue()));
    }

    @Test
    public void toggleRead_값_확인() {
        Notification noti = fixture();

        assertThat("read 초기값은 false.", noti.isRead(), is(false));

        noti.toggleRead();

        assertThat("토글 이후에는 true.", noti.isRead(), is(true));

        noti.toggleRead();

        assertThat("다시 토글 이후에는 false.", noti.isRead(), is(false));
    }

    // ---------------------------------------------
    private Notification fixture() {
        return new Notification("n", "o-1");
    }
}