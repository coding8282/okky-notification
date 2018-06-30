package org.okky.notification.domain.event;

import lombok.experimental.FieldDefaults;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.okky.notification.TestMother;
import org.okky.share.event.ReplyWrote;
import org.springframework.context.ApplicationEventPublisher;

import static lombok.AccessLevel.PRIVATE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.class)
@FieldDefaults(level = PRIVATE)
public class DomainEventPumpTest extends TestMother {
    @InjectMocks
    DomainEventPump consumer;
    @Mock
    ApplicationEventPublisher publisher;

    // TODO: 2018. 6. 30. production에서는 정확히 작동하지만 로컬에서 작동하지 않음.
    @Ignore
    @Test
    public void consume() {
        String event = "{\"Message\":{\"id\":\"r-1\",\"articleId\":\"a-1\",\"body\":\"내용\",\"replierId\":\"m-1\",\"replierName\":\"coding8282\",\"repliedOn\":1529234472727,\"publishedOn\":1529234472727,\"eventName\":\"org.okky.share.event.ReplyWrote\",\"context\":\"okky-reply\"}}";
        consumer.consume(event);

        InOrder o = inOrder(publisher);
        o.verify(publisher).publishEvent(any(ReplyWrote.class));
    }
}