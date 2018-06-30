package org.okky.notification.application;

import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.okky.notification.TestMother;
import org.okky.notification.domain.model.Notification;
import org.okky.notification.domain.model.reply.ReplyPinnedNoti;
import org.okky.notification.domain.model.reply.ReplyWroteNoti;
import org.okky.notification.domain.repository.NotiRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@FieldDefaults(level = PRIVATE)
public class NotificationQueryServiceTest extends TestMother {
    @InjectMocks
    NotificationQueryService service;
    @Mock
    NotiRepository repository;
    List<ReplyWroteNoti> replyWroteNotis;
    List<ReplyPinnedNoti> replyPinnedNotis;

    @Before
    public void setUp() {
        ReplyWroteNoti n1 = repyWroteNotiFixture();
        ReplyWroteNoti n2 = repyWroteNotiFixture();
        ReplyWroteNoti n3 = repyWroteNotiFixture();
        replyWroteNotis = asList(n1, n3, n2);
        ReplyPinnedNoti m1 = replyPinnedNotiFixture();
        ReplyPinnedNoti m2 = replyPinnedNotiFixture();
        ReplyPinnedNoti m3 = replyPinnedNotiFixture();
        replyPinnedNotis = asList(m3, m1, m2);
    }

    @Test
    public void findByOwnerId_내림차순으로_정렬되어_있어야_함() {
        Page replyWroteNotiPage = mock(Page.class);
        Page replyPinnedNotiPage = mock(Page.class);
        when(repository.findByOwnerId(eq("o"), any(Pageable.class))).thenReturn(replyWroteNotiPage);
        when(repository.findByOwnerId(eq("o"), any(Pageable.class))).thenReturn(replyPinnedNotiPage);
        when(replyWroteNotiPage.getContent()).thenReturn(replyWroteNotis);
        when(replyPinnedNotiPage.getContent()).thenReturn(replyPinnedNotis);

        List<Notification> o = service.findByOwnerId("o", PageRequest.of(0, 10));

        for (int i = 0; i < o.size() - 1; i++) {
            Notification previous = o.get(i);
            Notification next = o.get(i + 1);
            assertThat("내림차순으로 정렬되어 있지 않다.", previous.getNotifiedOn(), greaterThanOrEqualTo(next.getNotifiedOn()));
        }
    }

    // ----------------------------------------------------
    @SneakyThrows
    private ReplyPinnedNoti replyPinnedNotiFixture() {
        TimeUnit.MILLISECONDS.sleep(300);
        return ReplyPinnedNoti.sample();
    }

    @SneakyThrows
    private ReplyWroteNoti repyWroteNotiFixture() {
        TimeUnit.MILLISECONDS.sleep(300);
        return ReplyWroteNoti.sample();
    }
}