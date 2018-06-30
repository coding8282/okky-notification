package org.okky.notification.domain.service;

import org.junit.Test;
import org.okky.notification.TestMother;
import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.Notification;
import org.okky.share.event.ReplyWrote;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class NotiAssemblerTest extends TestMother {
    NotiAssembler notiAssembler = new NotiAssembler();

    @Test
    public void assemble() {
        Set<String> ownerIds = new HashSet<>(Arrays.asList("o1", "o2", "o3"));
        ReplyWrote event = ReplyWrote.sample();
        Article article = Article.sample();
        List<String> foundOwnerIds = notiAssembler
                .assemble(ownerIds, event, article)
                .stream()
                .map(Notification::getOwnerId)
                .collect(toList());

        assertThat("소유자가 3명이므로 3개의 알림이 만들어져야 한다.", foundOwnerIds, containsInAnyOrder("o1", "o2", "o3"));
    }
}