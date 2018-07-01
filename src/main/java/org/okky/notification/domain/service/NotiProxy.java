package org.okky.notification.domain.service;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.Emoter;
import org.okky.notification.domain.model.Member;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class NotiProxy {
    RestTemplate template;

    public Member fetchMember(String memberId) {
        String url = "/members/" + memberId;
        return template.getForEntity(url, Member.class).getBody();
    }

    public Emoter fetchEmother(String emoterId) {
        String url = "/members/" + emoterId;
        return template.getForEntity(url, Emoter.class).getBody();
    }

    public Article fetchArticle(String articleId) {
        String url = "/articles/" + articleId;
        return template.getForEntity(url, Article.class).getBody();
    }

    public Set<String> fetchReplierIds(String articleId) {
        return fetchReplierIds(articleId, 0);
    }

    public Set<String> fetchReplierIds(String articleId, int page) {
        String url = format("/articles/%s/repliers?page=%d", articleId, page);
        return template.getForEntity(url, Set.class).getBody();
    }
}
