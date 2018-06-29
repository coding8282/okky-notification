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
import org.okky.notification.domain.model.ReplyPinnedNoti;
import org.okky.notification.domain.repository.ReplyPinnedNotiRepository;
import org.okky.notification.domain.service.ReplyPinnedNotiService;

import java.util.List;

import static java.util.Arrays.asList;
import static lombok.AccessLevel.PRIVATE;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@FieldDefaults(level = PRIVATE)
public class ReplyPinnedNotiServiceTest extends TestMother {
    @InjectMocks
    ReplyPinnedNotiService service;
    @Mock
    ReplyPinnedNotiRepository repository;
    List<String> ids;
    List<ReplyPinnedNoti> notis;

    @Before
    public void setUp() {
        ids = asList("n-1", "n-2", "n-3");
        notis = asList(
                mock(ReplyPinnedNoti.class),
                mock(ReplyPinnedNoti.class),
                mock(ReplyPinnedNoti.class)
        );
    }

    @Test
    public void markRead() {
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
        service.remove(ids);

        InOrder o = inOrder(repository);
        o.verify(repository).deleteByIdIn(ids);
    }
}