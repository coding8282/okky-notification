package org.okky.notification.domain.event;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.okky.notification.domain.model.Reply;
import org.okky.notification.domain.model.noti.ReplyCommentedNoti;
import org.okky.share.event.ReplyCommented;

import static lombok.AccessLevel.PRIVATE;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@FieldDefaults(level = PRIVATE)
public class CommentEventProcessorTest extends EventProcessorTestMother {
    @InjectMocks
    CommentEventProcessor processor;

    @Test
    public void ReplyCommented_답글_작성자가_본인이_코멘트한_경우_알림하지_않음() {
        ReplyCommented event = spy(ReplyCommented.sample());
        Reply reply = spy(Reply.sample());
        when(event.getCommenterId()).thenReturn("m");
        when(reply.getReplierId()).thenReturn("m");
        when(proxy.fetchReply(anyString())).thenReturn(reply);

        processor.when(event);

        InOrder o = inOrder(proxy, repository);
        o.verify(proxy).fetchReply(anyString());
        o.verifyNoMoreInteractions();
    }

    @Test
    public void ReplyCommented_다른_사람이_코멘트한_경우_알림() {
        ReplyCommented event = spy(ReplyCommented.sample());
        Reply reply = spy(Reply.sample());
        when(event.getCommenterId()).thenReturn("m");
        when(reply.getReplierId()).thenReturn("x");
        when(proxy.fetchReply(anyString())).thenReturn(reply);

        processor.when(event);

        InOrder o = inOrder(proxy, repository);
        o.verify(proxy).fetchReply(anyString());
        o.verify(repository).save(isA(ReplyCommentedNoti.class));
        o.verifyNoMoreInteractions();
    }
}