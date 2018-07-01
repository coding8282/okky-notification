package org.okky.notification.domain.event;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.Emoter;
import org.okky.notification.domain.model.emotion.EmotedNoti;
import org.okky.share.event.Emoted;

import static lombok.AccessLevel.PRIVATE;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@FieldDefaults(level = PRIVATE)
public class EmotionEventProcessorTest extends EventProcessorTestMother {
    @InjectMocks
    EmotionEventProcessor processor;

    @Test
    public void Emoted_게시글_작성자_본인이_공감한_경우() {
        Emoted event = spy(Emoted.sample());
        Article article = spy(Article.sample());
        Emoter emoter = spy(Emoter.sample());
        when(event.getEmoterId()).thenReturn("m");
        when(article.getWriterId()).thenReturn("m");
        when(proxy.fetchArticle(anyString())).thenReturn(article);
        when(proxy.fetchEmoter(anyString())).thenReturn(emoter);

        processor.when(event);

        InOrder o = inOrder(proxy, repository);
        o.verify(proxy).fetchArticle(anyString());
        o.verify(repository, never()).save(any());
    }

    @Test
    public void Emoted_게시글_작성자가_아닌_다른_사람이_공감한_경우() {
        Emoted event = spy(Emoted.sample());
        Article article = spy(Article.sample());
        Emoter emoter = spy(Emoter.sample());
        when(event.getEmoterId()).thenReturn("m");
        when(article.getWriterId()).thenReturn("x");
        when(proxy.fetchArticle(anyString())).thenReturn(article);
        when(proxy.fetchEmoter(anyString())).thenReturn(emoter);

        processor.when(event);

        InOrder o = inOrder(proxy, repository);
        o.verify(proxy).fetchArticle(anyString());
        o.verify(repository).save(any(EmotedNoti.class));
    }
}