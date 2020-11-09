package org.assertj.vavr.api.soft;

import io.vavr.control.Either;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.model.Statement;
import org.opentest4j.MultipleFailuresError;

import java.util.List;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.mock;

public class JUnitSoftVavrAssertionsSuccessTest {

    @Rule
    public final JUnitSoftVavrAssertions softly = new JUnitSoftVavrAssertions();

    @Test
    public void should_rule_for_soft_assertion_work() {
        // GIVEN
        Either<String, Object> actual = Either.left("something");
        softly.assertThat(actual).as("contains").containsOnLeft("something");
        softly.assertThat(actual).as("is").isLeft();
        softly.assertThat(actual).as("instance").containsLeftInstanceOf(String.class);
    }

}