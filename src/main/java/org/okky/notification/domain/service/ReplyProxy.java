package org.okky.notification.domain.service;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ReplyProxy {
    RestTemplate template;

    public List<String> fetchReplierIds(String articleId, int page) {
        return template.getForEntity(format("/articles/%s/repliers?page=%d", articleId, page), List.class).getBody();
    }
}
