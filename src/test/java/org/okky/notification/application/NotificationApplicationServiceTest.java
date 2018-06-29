package org.okky.notification.application;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.okky.notification.TestMother;
import org.okky.notification.domain.service.ReplyPinnedNotiService;
import org.okky.notification.domain.service.ReplyWroteNotiService;

import java.util.List;

import static java.util.Arrays.asList;
import static lombok.AccessLevel.PRIVATE;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.class)
@FieldDefaults(level = PRIVATE)
public class NotificationApplicationServiceTest extends TestMother {
    @InjectMocks
    NotificationApplicationService service;
    @Mock
    ReplyWroteNotiService replyWroteNotiService;
    @Mock
    ReplyPinnedNotiService replyPinnedNotiService;

    @Test
    public void markRead() {
        List<String> ids = asList("n-1", "n-2", "n-3");
        service.markRead(ids);

        InOrder o = inOrder(replyWroteNotiService, replyPinnedNotiService);
        o.verify(replyWroteNotiService).markRead(ids);
        o.verify(replyPinnedNotiService).markRead(ids);
    }

    @Test
    public void markUnread() {
        List<String> ids = asList("n-1", "n-2", "n-3");
        service.markUnread(ids);

        InOrder o = inOrder(replyWroteNotiService, replyPinnedNotiService);
        o.verify(replyWroteNotiService).markUnread(ids);
        o.verify(replyPinnedNotiService).markUnread(ids);
    }

    @Test
    public void markReadAll() {
        service.markReadAll("o");

        InOrder o = inOrder(replyWroteNotiService, replyPinnedNotiService);
        o.verify(replyWroteNotiService).markReadAll("o");
        o.verify(replyPinnedNotiService).markReadAll(eq("o"));
    }

    @Test
    public void toggleRead() {
        List<String> ids = asList("n-1", "n-2", "n-3");
        service.toggleRead(ids);

        InOrder o = inOrder(replyWroteNotiService, replyPinnedNotiService);
        o.verify(replyWroteNotiService).toggleRead(ids);
        o.verify(replyPinnedNotiService).toggleRead(ids);
    }

    @Test
    public void remove() {
        List<String> ids = asList("n-1", "n-2", "n-3");
        service.remove(ids);

        InOrder o = inOrder(replyWroteNotiService, replyPinnedNotiService);
        o.verify(replyWroteNotiService).remove(ids);
        o.verify(replyPinnedNotiService).remove(ids);
    }
}