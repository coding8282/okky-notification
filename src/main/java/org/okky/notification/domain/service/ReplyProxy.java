package org.okky.notification.domain.service;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ReplyProxy {
    RestTemplate template;

    public Set<String> fetchReplierIds(String articleId) {
        return fetchReplierIds(articleId, 0);
    }

    public Set<String> fetchReplierIds(String articleId, int page) {
        String url = format("/articles/%s/repliers?page=%d", articleId, page);
        return template.getForEntity(url, Set.class).getBody();
    }
}
