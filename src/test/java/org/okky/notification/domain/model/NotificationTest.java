package org.okky.notification.domain.model;

import org.junit.Test;
import org.okky.notification.TestMother;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class NotificationTest extends TestMother {
    @Test
    public void Notification모델_초기값_확인() {
        Notification noti = fixture();

        assertThat("read 초기값은 false.", noti.read, is(false));
        assertThat("hidden 초기값은 false.", noti.hidden, is(false));
        assertThat("readOn 초기값은 null.", noti.readOn, is(nullValue()));
        assertThat("hiddenOn 초기값은 null.", noti.hiddenOn, is(nullValue()));
        assertThat("notifiedOn 초기값은 timestamp이다.", noti.notifiedOn, is(notNullValue()));
        assertThat("메시지 소유자 아이디는 필수.", noti.ownerId, is("o-1"));
        assertThat("이벤트명은 클래스명과 같음.", noti.event, is("Notification"));
    }

    @Test
    public void markRead_값_확인() {
        Notification noti = fixture();

        assertThat("read 초기값은 false.", noti.read, is(false));
        assertThat("readOn 초기값은 null.", noti.readOn, is(nullValue()));

        noti.markRead();

        assertThat("marking 이후에는 true.", noti.read, is(true));
        assertThat("marking 이후에는 적절한 timestamp.", noti.readOn, is(notNullValue()));
    }

    @Test
    public void markUnread_값_확인() {
        Notification noti = fixture();
        noti.markRead();

        assertThat("marking 이후에는 true.", noti.read, is(true));
        assertThat("marking 이후에는 적절한 timestamp.", noti.readOn, is(notNullValue()));

        noti.markUnread();

        assertThat("unmarking 이후에는 false.", noti.read, is(false));
        assertThat("unmarking 이후에는 null.", noti.readOn, is(nullValue()));
    }

    @Test
    public void toggleRead_값_확인() {
        Notification noti = fixture();

        assertThat("read 초기값은 false.", noti.read, is(false));

        noti.toggleRead();

        assertThat("토글 이후에는 true.", noti.read, is(true));

        noti.toggleRead();

        assertThat("다시 토글 이후에는 false.", noti.read, is(false));
    }

    // ---------------------------------------------
    private Notification fixture() {
        Notification noti = new Notification("o-1");
        return noti;
    }
}