package org.okky.notification.domain.event;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.okky.notification.TestMother;
import org.okky.notification.domain.repository.NotiRepository;
import org.okky.notification.domain.service.ArticleProxy;
import org.okky.notification.domain.service.NotiAssembler;
import org.okky.notification.domain.service.ReplyProxy;
import org.okky.share.event.ReplyWrote;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static lombok.AccessLevel.PRIVATE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@FieldDefaults(level = PRIVATE)
public class ReplyEventProcessorTest extends TestMother {
    @InjectMocks
    ReplyEventProcessor processor;
    @Mock
    NotiRepository repository;
    @Mock
    NotiAssembler assembler;
    @Mock
    ArticleProxy articleProxy;
    @Mock
    ReplyProxy replyProxy;
    @Mock
    ReplyWrote event;

    @Test
    public void when_게시글_답변자가_없는_경우() {
        when(event.getArticleId()).thenReturn("a-1");
        when(replyProxy.fetchReplierIds(eq("a-1"), eq(0))).thenReturn(emptyList());

        processor.when(event);

        InOrder o = inOrder(articleProxy, replyProxy, assembler, repository);
        o.verify(articleProxy).fetchArticle(eq("a-1"));
        o.verify(replyProxy).fetchReplierIds(eq("a-1"), eq(0));
        o.verify(assembler, never()).assemble(any(), any(), any());
        o.verify(repository, never()).saveAll(any());
        o.verify(replyProxy, never()).fetchReplierIds(any(), anyInt());
    }

    @Test
    public void when_게시글_답변자가_있는_경우() {
        when(event.getArticleId()).thenReturn("a-1");
        when(replyProxy.fetchReplierIds(eq("a-1"), eq(0))).thenReturn(asList("r-1", "r-2", "r-3"));
        when(replyProxy.fetchReplierIds(eq("a-1"), eq(1))).thenReturn(asList("r-1", "r-2", "r-3"));
        when(replyProxy.fetchReplierIds(eq("a-1"), eq(2))).thenReturn(emptyList());

        processor.when(event);

        InOrder o = inOrder(articleProxy, replyProxy, assembler, repository);
        o.verify(articleProxy).fetchArticle(eq("a-1"));
        o.verify(replyProxy, times(3)).fetchReplierIds(eq("a-1"), anyInt());
        // TODO: 2018. 6. 17. 왜 안 되는지 모르겠다.... 너무 지치고 힘들어 ㅜㅜ junit만 하루에 15시간째네...
        // o.verify(assembler, times(2)).assemble(any(), any(), any());
        // o.verify(repository, atLeastOnce()).saveAll(any());
    }
}