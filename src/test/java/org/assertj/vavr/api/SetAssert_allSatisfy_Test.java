package org.assertj.vavr.api;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

public class SetAssert_allSatisfy_Test {

    private static final Consumer<String> EMPTY_CONSUMER = element -> {};

    @Test
    void should_pass_if_Set_is_empty() {
        assertThat(HashSet.<String>empty()).allSatisfy(EMPTY_CONSUMER);
    }

    @Test
    void should_pass_if_all_Set_entries_satisfy_consumer() {
        final Set<String> actual = HashSet.of("value1", "value2");

        assertThat(actual).allSatisfy(element -> {
            assertThat(element).startsWith("value");
        });
    }

    @Test
    void should_fail_if_consumer_is_null() {
        assertThatThrownBy(
                () -> assertThat(HashSet.empty()).allSatisfy(null)
        )
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The Consumer<T> expressing the assertions requirements must not be null");
    }

    @Test
    void should_fail_when_Set_is_null() {
        assertThatThrownBy(
                () -> assertThat((Set<String>) null).allSatisfy(EMPTY_CONSUMER)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldNotBeNull().create());
    }

    @Test
    void should_fail_if_all_Set_elements_do_not_satisfy_consumer() {
        final Set<String> actual = HashSet.of("value1", "value2");

        assertThatThrownBy(
                () -> assertThat(actual).allSatisfy(element -> {
                    assertThat(element).isEqualTo("value1");
                })
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\n" +
                        "Expecting all elements of:\n" +
                        "  <[\"value1\", \"value2\"]>\n" +
                        "to satisfy given requirements, but these elements did not:\n" +
                        "\n" +
                        "  <\"value2\"> error: \n" +
                        "Expecting:\n" +
                        " <\"value2\">\n" +
                        "to be equal to:\n" +
                        " <\"value1\">\n" +
                        "but was not.");
    }
}
