package org.okky.notification.application;

import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.okky.notification.TestMother;
import org.okky.notification.domain.model.ReplyWroteNoti;
import org.okky.notification.domain.repository.ReplyWroteNotiRepository;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static lombok.AccessLevel.PRIVATE;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@FieldDefaults(level = PRIVATE)
public class NotificationServiceTest extends TestMother {
    @InjectMocks
    NotificationService service;
    @Mock
    ReplyWroteNotiRepository repository;
    List<ReplyWroteNoti> notis;

    @Before
    public void setUp() {
        notis = Arrays.asList(
                mock(ReplyWroteNoti.class),
                mock(ReplyWroteNoti.class),
                mock(ReplyWroteNoti.class)
        );
    }

    @Test
    public void markRead() {
        List<String> ids = asList("n-1", "n-2", "n-3");
        when(repository.findByIdIn(ids)).thenReturn(notis);

        service.markRead(ids);

        InOrder o = inOrder(repository, notis.get(0), notis.get(1), notis.get(2));
        o.verify(repository).findByIdIn(ids);
        o.verify(notis.get(0)).markRead();
        o.verify(notis.get(1)).markRead();
        o.verify(notis.get(2)).markRead();
    }

    @Test
    public void markUnread() {
        List<String> ids = asList("n-1", "n-2", "n-3");
        when(repository.findByIdIn(ids)).thenReturn(notis);

        service.markUnread(ids);

        InOrder o = inOrder(repository, notis.get(0), notis.get(1), notis.get(2));
        o.verify(repository).findByIdIn(ids);
        o.verify(notis.get(0)).markUnread();
        o.verify(notis.get(1)).markUnread();
        o.verify(notis.get(2)).markUnread();
    }

    @Test
    public void markReadAll() {
        when(repository.findByOwnerId("m-1")).thenReturn(notis);

        service.markReadAll("m-1");

        InOrder o = inOrder(repository, notis.get(0), notis.get(1), notis.get(2));
        o.verify(repository).findByOwnerId("m-1");
        o.verify(notis.get(0)).markRead();
        o.verify(notis.get(1)).markRead();
        o.verify(notis.get(2)).markRead();
    }

    @Test
    public void toggleRead() {
        List<String> ids = asList("n-1", "n-2", "n-3");
        when(repository.findByIdIn(ids)).thenReturn(notis);

        service.toggleRead(ids);

        InOrder o = inOrder(repository, notis.get(0), notis.get(1), notis.get(2));
        o.verify(repository).findByIdIn(ids);
        o.verify(notis.get(0)).toggleRead();
        o.verify(notis.get(1)).toggleRead();
        o.verify(notis.get(2)).toggleRead();
    }

    @Test
    public void remove() {
        List<String> ids = asList("n-1", "n-2", "n-3");
        service.remove(ids);

        InOrder o = inOrder(repository);
        o.verify(repository).deleteByIdIn(ids);
    }
}