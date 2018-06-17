package org.okky.notification.domain.service;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.model.Article;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ArticleProxy {
    RestTemplate template;

    public Article fetchArticle(String articleId) {
        return template.getForEntity("/articles/" + articleId, Article.class).getBody();
    }
}
