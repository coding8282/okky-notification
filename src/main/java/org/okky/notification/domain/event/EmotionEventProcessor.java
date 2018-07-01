package org.okky.notification.domain.event;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.Emoter;
import org.okky.notification.domain.model.noti.EmotedNoti;
import org.okky.notification.domain.repository.NotiRepository;
import org.okky.notification.domain.service.NotiProxy;
import org.okky.share.event.Emoted;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
class EmotionEventProcessor {
    NotiRepository repository;
    NotiProxy proxy;

    @EventListener
    void when(Emoted event) {
        Article article = proxy.fetchArticle(event.getTargetId());
        Emoter emoter = proxy.fetchEmoter(event.getEmoterId());
        EmotedNoti noti = new EmotedNoti(event, article, emoter);
        if (noti.didEmotedByOthers())
            repository.save(noti);
    }
}
