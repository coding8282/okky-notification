package org.okky.notification.domain.model.changelog;

import org.okky.share.domain.ValueObject;
import org.okky.share.execption.BadArgument;

import java.util.Arrays;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.okky.share.domain.AssertionConcern.assertArgNotEmpty;

public enum ChangeType implements ValueObject {
    FEATURE,
    FIX,
    IMPROVMENT,
    REGRESSION,
    OTHERS,;

    public static ChangeType parse(String value) {
        try {
            assertArgNotEmpty(value, "변경 유형은 필수입니다.");
            return valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            String possibleValues = String.join(",", Arrays
                    .stream(values())
                    .map(ChangeType::name)
                    .collect(Collectors.toList()));
            throw new BadArgument(format("'%s'는 지원하지 않는 변경 유형입니다. '%s'만 가능합니다.", value, possibleValues));
        }
    }
}
