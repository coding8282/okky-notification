package org.okky.notification.domain.model.changelog;

import org.junit.Test;
import org.okky.notification.TestMother;
import org.okky.share.execption.BadArgument;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.okky.notification.domain.model.changelog.ChangeType.FEATURE;
import static org.okky.notification.domain.model.changelog.ChangeType.FIX;

public class ChangeLogTest extends TestMother {
    @Test
    public void body가_null이면_예외() {
        expect(BadArgument.class, "바디는 필수입니다.");

        new ChangeLog(null, FEATURE);
    }

    @Test
    public void body가_0자일_때_예외() {
        expect(BadArgument.class, "바디는 필수입니다.");

        new ChangeLog(null, FEATURE);
    }

    @Test
    public void body가_201자일_때_예외() {
        expect(BadArgument.class, "내용은 200자까지만 가능합니다: 현재 201자");

        new ChangeLog("Sunt tuses attrahendam superbus, magnum pulchritudinees.Sunt tuses attrahendam superbus, magnum pulchritudinees.Sunt tuses attrahendam superbus, magnum pulchritudinees.Sunt tuses attrahendam superbus,a", FEATURE);
    }

    @Test
    public void body_trim_확인() {
        String body = "  새로운 기능  ";
        ChangeLog log = new ChangeLog(body, FEATURE);

        assertThat("trim이 되지 않았다.", log.getBody(), is(body.trim()));
    }

    @Test
    public void changeType이_null이면_예외() {
        expect(BadArgument.class, "변경 유형은 필수입니다.");

        new ChangeLog("The cockroach desires with endurance, break the cook islands before it stutters.", null);
    }

    @Test
    public void 새로_만들었다면_항상_날짜가_존재해야_함() {
        ChangeLog log = fixture();

        assertThat("항상 만들어진 날짜가 존재해야 한다.", log.getLogedOn(), is(notNullValue()));
    }

    @Test
    public void update_확인() {
        ChangeLog log = new ChangeLog("a", FEATURE);

        assertThat("바디가 다르다.", log.getBody(), is("a"));
        assertThat("변경 유형이 다르다.", log.getType(), is(FEATURE));

        log.update("bbbb", FIX);

        assertThat("새로 바꾼 바디가 다르다.", log.getBody(), is("bbbb"));
        assertThat("새로 바꾼 변경 유형이 다르다.", log.getType(), is(FIX));
    }

    // -----------------------------------
    ChangeLog fixture() {
        String body = "새로운 기능이 추가되었습니다.";
        ChangeType type = FEATURE;
        return new ChangeLog(body, type);
    }
}