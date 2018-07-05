package org.okky.notification.domain.event;

import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.Emoter;
import org.okky.notification.domain.model.noti.EmotedNoti;
import org.okky.share.event.Emoted;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class EmotionEventProcessor extends EventProcessor {
    @EventListener
    void when(Emoted event) {
        Article article = proxy.fetchArticle(event.getTargetId());
        Emoter emoter = proxy.fetchEmoter(event.getEmoterId());
        EmotedNoti noti = new EmotedNoti(event, article, emoter);
        if (noti.wasEmotedByOthers())
            repository.save(noti);
    }
}
