package org.okky.notification.domain.model.changelog;

import org.junit.Test;
import org.okky.notification.TestMother;
import org.okky.share.execption.BadArgument;

import static org.junit.Assert.assertEquals;
import static org.okky.notification.domain.model.changelog.ChangeType.*;

public class ChangeTypeTest extends TestMother {
    @Test
    public void parse_변경_유형이_null이면_예외() {
        expect(BadArgument.class, "변경 유형은 필수입니다.");

        ChangeType.parse(null);
    }

    @Test
    public void parse_변경_유형이_빈문자열이면_예외() {
        expect(BadArgument.class, "변경 유형은 필수입니다.");

        ChangeType.parse("");
    }

    @Test
    public void parse_COURIUS는_지원하지_않는_변경_유형() {
        expect(BadArgument.class, "'COURIUS'는 지원하지 않는 변경 유형입니다. 'FEATURE,FIX,IMPROVMENT,REGRESSION,OTHERS'만 가능합니다.");

        ChangeType.parse("COURIUS");
    }

    @Test
    public void parse_소문자를_줘도_분석_성공() {
        ChangeType type = ChangeType.parse("FEATURE");

        assertEquals("대소문자를 섞어서 줘도 분석에 성공해야 한다.", FEATURE, type);
    }

    @Test
    public void parse_대소문자를_섞어서_줘도_분석_성공() {
        ChangeType type = ChangeType.parse("IMPRovMENT");

        assertEquals("대소문자를 섞어서 줘도 분석에 성공해야 한다.", IMPROVMENT, type);
    }

    @Test
    public void parse_trim_확인() {
        ChangeType type = ChangeType.parse("  OTHERS\t\t");

        assertEquals("앞뒤 불필요한 공백 문자는 제거해야 한다.", OTHERS, type);
    }
}