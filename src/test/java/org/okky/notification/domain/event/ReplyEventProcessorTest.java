package org.okky.notification.domain.event;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.okky.notification.TestMother;
import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.reply.ReplyPinnedNoti;
import org.okky.notification.domain.repository.NotiRepository;
import org.okky.notification.domain.service.ArticleProxy;
import org.okky.notification.domain.service.NotiAssembler;
import org.okky.notification.domain.service.ReplyProxy;
import org.okky.share.event.ReplyPinned;
import org.okky.share.event.ReplyWrote;

import java.util.List;

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

    @Test
    public void ReplyWrote() {
        ReplyWrote event = mock(ReplyWrote.class);
        Article article = mock(Article.class);
        when(event.getArticleId()).thenReturn("a-1");
        when(article.getWriterId()).thenReturn("w-1");
        when(articleProxy.fetchArticle(eq("a-1"))).thenReturn(article);

        processor.when(event);

        InOrder o = inOrder(articleProxy, replyProxy, assembler, repository);
        o.verify(articleProxy).fetchArticle("a-1");
        o.verify(replyProxy).fetchReplierIds("a-1");
        o.verify(assembler).assemble(any(), eq(event), eq(article));
        o.verify(repository).saveAll(isA(List.class));
    }

    @Test
    public void ReplyPinned_게시글_작성자_본인이_답글을_고정한_경우() {
        ReplyPinned event = spy(ReplyPinned.sample());
        Article article = spy(Article.sample());
        when(event.getReplierId()).thenReturn("m");
        when(article.getWriterId()).thenReturn("m");
        when(articleProxy.fetchArticle(eq(event.getArticleId()))).thenReturn(article);

        processor.when(event);

        InOrder o = inOrder(articleProxy, repository);
        o.verify(articleProxy).fetchArticle(event.getArticleId());
        o.verify(repository, never()).save(isA(ReplyPinnedNoti.class));
    }

    @Test
    public void ReplyPinned_게시글_작성자가_아닌_다른_사람의_답글을_고정한_경우() {
        ReplyPinned event = spy(ReplyPinned.sample());
        Article article = spy(Article.sample());
        when(event.getReplierId()).thenReturn("m");
        when(article.getWriterId()).thenReturn("k");
        when(articleProxy.fetchArticle(eq(event.getArticleId()))).thenReturn(article);

        processor.when(event);

        InOrder o = inOrder(articleProxy, repository);
        o.verify(articleProxy).fetchArticle(event.getArticleId());
        o.verify(repository).save(isA(ReplyPinnedNoti.class));
    }
}