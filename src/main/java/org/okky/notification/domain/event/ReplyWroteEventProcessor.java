package org.okky.notification.domain.event;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.ReplyWroteNoti;
import org.okky.notification.domain.repository.ReplyWroteNotiRepository;
import org.okky.share.event.ReplyWrote;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Function;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Slf4j
class ReplyWroteEventProcessor {
    ReplyWroteNotiRepository repository;
    RestTemplate template;

    @EventListener
    @SneakyThrows
    void when(ReplyWrote event) {
        Article article = template.getForEntity("/articles/" + event.getArticleId(), Article.class).getBody();
        int page = 0;
        List<String> ownerIds;
        do {
            String url = format("/articles/%s/repliers?page=%d", event.getArticleId(), page++);
            ownerIds = template.getForEntity(url, List.class).getBody();
            List<ReplyWroteNoti> notis = ownerIds
                    .stream()
                    .map(mapper(event, article))
                    .filter(ReplyWroteNoti::isEligible)
                    .collect(toList());
            repository.saveAll(notis);
        } while (!ownerIds.isEmpty());

        logger.info("Event: {}", event.getClass().getSimpleName());
    }

    // ------------------------------------------------
    private Function<String, ReplyWroteNoti> mapper(ReplyWrote event, Article article) {
        return ownerId -> ReplyWroteNoti
                .builder()
                .ownerId(ownerId)
                .event(event)
                .article(article)
                .build();
    }
}
