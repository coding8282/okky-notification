package org.okky.notification.domain.repository;

import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.okky.notification.TestMother;
import org.okky.notification.domain.model.changelog.ChangeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.okky.notification.domain.model.changelog.ChangeType.FEATURE;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataMongoTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@FieldDefaults(level = PRIVATE)
public class ChangeLogRepositoryTest extends TestMother {
    @Autowired
    ChangeLogRepository repository;

    @Test
    public void existsById_존재하는_경우_true() {
        ChangeLog log = fixture();
        repository.save(log);
        boolean exists = repository.existsById(log.getId());

        assertThat("존재하므로 true여야 한다.", exists, is(true));
    }

    @Test
    public void existsById_존재하지_않는_경우_false() {
        boolean exists = repository.existsById("non-exists-id");

        assertThat("존재하지 않으므로 false여야 한다.", exists, is(false));
    }

    @Test
    public void saveAndFind() {
        ChangeLog log = fixture();
        repository.save(log);
        ChangeLog found = repository.findById(log.getId()).get();

        assertThat("같은 엔티티가 나와야 한다.", found, is(log));
    }

    @Test
    public void findAll_logedOn으로_정렬() {
        ChangeLog log0 = fixture();
        ChangeLog log1 = fixture();
        ChangeLog log2 = fixture();
        repository.save(log0);
        repository.save(log1);
        repository.save(log2);
        List<ChangeLog> found = repository
                .findAll(of(0, 10, DESC, "logedOn"))
                .getContent();

        assertThat("같은 엔티티가 나와야 한다.", found.get(0), is(log2));
        assertThat("같은 엔티티가 나와야 한다.", found.get(1), is(log1));
        assertThat("같은 엔티티가 나와야 한다.", found.get(2), is(log0));
    }

    @Test
    public void deleteById() {
        ChangeLog log = fixture();
        repository.save(log);
        boolean exists = repository.existsById(log.getId());
        assertThat("삭제 전에는 존재해야 한다.", exists, is(true));

        repository.deleteById(log.getId());
        exists = repository.existsById(log.getId());
        assertThat("삭제 후에는 존재해서는 안 된다.", exists, is(false));
    }

    // ----------------------
    @SneakyThrows
    ChangeLog fixture() {
        TimeUnit.MILLISECONDS.sleep(50);  // logedOn에 시간차를 두기 위하여 일부러 딜레이
        return new ChangeLog("새로운 기능이 추가되었습니다.", FEATURE);
    }
}