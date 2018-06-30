package org.okky.notification.domain.repository;

import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.okky.notification.TestMother;
import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.Notification;
import org.okky.notification.domain.model.reply.ReplyWroteNoti;
import org.okky.share.event.ReplyWrote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.lang.System.currentTimeMillis;
import static java.util.Arrays.asList;
import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataMongoTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@FieldDefaults(level = PRIVATE)
public class NotiRepositoryTest extends TestMother {
    @Autowired
    NotiRepository repository;

    @Test
    public void saveAllAndCountByOwnerIdAndReadIsFalse() {
        ReplyWroteNoti nt1 = fixture("m-1", false);
        ReplyWroteNoti nt2 = fixture("m-1", false);
        ReplyWroteNoti nt3 = fixture("m-1", false);
        ReplyWroteNoti nt4 = fixture("m-1", true);

        repository.saveAll(asList(nt1, nt2, nt3, nt4));
        long count = repository.countByOwnerIdAndReadIsFalse("m-1");

        assertEquals("안 읽은 알림은 3개이다.", 3L, count);
    }

    @Test
    public void findByOwnerId() {
        ReplyWroteNoti nt1 = fixture("m-1", false);
        ReplyWroteNoti nt2 = fixture("m-1", false);
        ReplyWroteNoti nt3 = fixture("m-2", false);

        repository.saveAll(asList(nt1, nt2, nt3));
        List<Notification> notis = repository.findByOwnerId("m-1");

        assertThat("알림은 2개이어야 한다.", notis, hasSize(2));
    }

    @Test
    public void findByOwnerId_첫번째_페이징() {
        repository.saveAll(asList(
                fixture("m-1", false),
                fixture("m-1", false),
                fixture("m-1", false),
                fixture("m-1", false),
                fixture("m-1", false),

                fixture("m-1", false),
                fixture("m-1", false),
                fixture("m-1", false),
                fixture("m-1", false),
                fixture("m-1", false),

                fixture("m-1", false)
        ));
        Page<Notification> page = repository.findByOwnerId("m-1", PageRequest.of(0, 5));

        assertEquals("전체 페이지 개수는 3개이다.", 3, page.getTotalPages());
        assertEquals("알림은 5개이다.", 5, page.getNumberOfElements());
    }

    @Test
    public void findByOwnerId_마지막_페이징() {
        repository.saveAll(asList(
                fixture("m-1", false),
                fixture("m-1", false),
                fixture("m-1", false),
                fixture("m-1", false),
                fixture("m-1", false),

                fixture("m-1", false)
        ));
        Page<Notification> page = repository.findByOwnerId("m-1", PageRequest.of(1, 5));

        assertEquals("전체 페이지 개수는 2개이다.", 2, page.getTotalPages());
        assertEquals("알림은 5개이다.", 1, page.getNumberOfElements());
    }

    @Test
    public void findByOwnerId_없는_페이지_페이징() {
        repository.saveAll(asList(
                fixture("m-1", false),
                fixture("m-1", false),
                fixture("m-1", false),
                fixture("m-1", false),
                fixture("m-1", false),

                fixture("m-1", false),
                fixture("m-1", false),
                fixture("m-1", false),
                fixture("m-1", false),
                fixture("m-1", false),

                fixture("m-1", false)
        ));
        Page<Notification> page = repository.findByOwnerId("m-1", PageRequest.of(100, 5));

        assertEquals("전체 페이지 개수는 3개이다.", 3, page.getTotalPages());
        assertEquals("알림은 0개이다.", 0, page.getNumberOfElements());
    }

    @Test
    public void findByOwnerId_음수_페이지_페이징은_예외() {
        expect(IllegalArgumentException.class);

        repository.saveAll(asList(fixture("m-1", false)));
        repository.findByOwnerId("m-1", PageRequest.of(-100, 5));
    }

    @Test
    public void findByIdIn() {
        ReplyWroteNoti nt0 = fixture("m-1", false);
        ReplyWroteNoti nt1 = fixture("m-1", false);
        ReplyWroteNoti nt2 = fixture("m-1", false);
        ReplyWroteNoti nt3 = fixture("m-2", false);
        ReplyWroteNoti nt4 = fixture("m-2", false);
        repository.saveAll(asList(nt0, nt1, nt2, nt3, nt4));

        List<Notification> notis = repository.findByIdIn(asList(nt1.getId(), nt3.getId(), nt4.getId()));
        assertThat("1,2,3번 중 포함하지 않는 알림이 있음.", notis, containsInAnyOrder(nt1, nt3, nt4));
    }

    @Test
    public void findByIdIn2() {
        Notification nt0 = fixture("m-1", false);
        Notification nt1 = fixture("m-1", false);
        Notification nt2 = fixture("m-1", false);
        Notification nt3 = fixture("m-2", false);
        Notification nt4 = fixture("m-2", false);
        repository.saveAll(asList(nt0, nt1, nt2, nt3, nt4));

        List<Notification> byIdIn = repository.findByIdIn(asList(nt1.getId(), nt3.getId(), nt4.getId()));
        System.out.println(byIdIn);

        List<Notification> notis = repository.findByIdIn(asList(nt1.getId(), nt3.getId(), nt4.getId()));
        assertThat("1,2,3번 중 포함하지 않는 알림이 있음.", notis, containsInAnyOrder(nt1, nt3, nt4));
    }

    @Test
    public void deleteByIdIn() {
        Notification nt0 = fixture("m-1", false);
        Notification nt1 = fixture("m-1", false);
        Notification nt2 = fixture("m-1", false);
        Notification nt3 = fixture("m-2", false);
        Notification nt4 = fixture("m-2", false);
        repository.saveAll(asList(nt0, nt1, nt2, nt3, nt4));
    }

    // ----------------------------------------------------------
    @SneakyThrows
    private ReplyWroteNoti fixture(String ownerId, boolean read) {
        Thread.sleep(50); // TODO: 2018. 6. 17. ReplyWrote이벤트의 repliedOn이 컴퓨터 시간으로 만들어지기 때문에 테스트를 위해 약간의 딜레이 필요.
        String id = "r-1";
        String articleId = "a-1";
        String body = "내용";
        String replierId = "m-1";
        String replierName = "coding8282";
        long repliedOn = currentTimeMillis();
        Long updatedOn = null;
        Long acceptedOn = null;
        ReplyWrote event = new ReplyWrote(id, articleId, body, replierId, replierName, repliedOn, updatedOn, acceptedOn);

        String id1 = "a-1";
        String writerId = "m-3";
        String writerName = "coding8282";
        Article article = new Article(id1, writerId, writerName);

        ReplyWroteNoti noti = new ReplyWroteNoti(ownerId, event, article);
        if (read)
            noti.markRead();
        return noti;
    }
}