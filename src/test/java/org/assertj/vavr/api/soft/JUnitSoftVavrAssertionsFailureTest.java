package org.assertj.vavr.api.soft;

import io.vavr.control.Either;
import org.junit.Test;
import org.junit.runners.model.Statement;
import org.opentest4j.MultipleFailuresError;

import java.util.List;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.mock;

public class JUnitSoftVavrAssertionsFailureTest {

//    @Rule
    public final JUnitSoftVavrAssertions softly = new JUnitSoftVavrAssertions();

    @Test
    public void should_rule_for_soft_assertion_work() {
        // GIVEN
        Either<String, Object> actual = Either.left("something");
        softly.assertThat(actual).as("contains").containsOnLeft("else");
        softly.assertThat(actual).as("is").isLeft();
        softly.assertThat(actual).as("instance").containsLeftInstanceOf(Long.class);

        // WHEN simulating the rule
        MultipleFailuresError multipleFailuresError = catchThrowableOfType(() -> softly.apply(mock(Statement.class), null).evaluate(),
                MultipleFailuresError.class);

        // THEN
        List<Throwable> failures = multipleFailuresError.getFailures();
        assertThat(failures).hasSize(2);
        assertThat(failures.get(0)).hasMessageStartingWith(format("[contains] %nExpecting:%n  <Left(something)>%nto contain:%n  <\"else\">%nbut did not."));
        assertThat(failures.get(1)).hasMessageStartingWith(format("[instance] %nExpecting:%n" +
                " <Left>%n" +
                "to contain a value that is an instance of:%n" +
                " <java.lang.Long>%n" +
                "but did contain an instance of:%n" +
                " <java.lang.String>"));
    }

}