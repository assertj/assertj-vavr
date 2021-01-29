/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2017-2021 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.assertj.core.api.Condition;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Index.atIndex;
import static org.assertj.vavr.api.BaseAssumptionRunner.assumptionRunner;
import static org.assertj.vavr.api.VavrAssumptions.assumeThat;

public class Seq_assertion_methods_in_assumptions_Test extends BaseAssumptionsTest {

    private static final Seq<String> NAMES = List.of("bilbo", "frodo", "gandalf");
    private static final Condition<String> FRODO_CONDITION = new Condition<>(str -> str.equals("frodo"), "contains frodo");
    private static final Consumer<String> FRODO_CONSUMER = str -> assertThat(str).isEqualTo("frodo");

    public static Stream<AssumptionRunner<?>> provideAssumptionsRunners() {
        return Stream.of(
                assumptionRunner(NAMES,
                        value -> assumeThat(value).has(FRODO_CONDITION, atIndex(1)),
                        value -> assumeThat(value).has(FRODO_CONDITION, atIndex(0))),
                assumptionRunner(NAMES,
                        value -> assumeThat(value).satisfies(FRODO_CONSUMER, atIndex(1)),
                        value -> assumeThat(value).satisfies(FRODO_CONSUMER, atIndex(0))),
                assumptionRunner(NAMES,
                        value -> assumeThat(value).contains("frodo", atIndex(1)),
                        value -> assumeThat(value).contains("frodo", atIndex(0))),
                assumptionRunner(NAMES,
                        value -> assumeThat(value).doesNotContain("frodo", atIndex(0)),
                        value -> assumeThat(value).doesNotContain("frodo", atIndex(1))),
                assumptionRunner(NAMES,
                        value -> assumeThat(value).isSortedAccordingTo(Comparator.naturalOrder()),
                        value -> assumeThat(value).isSortedAccordingTo(Comparator.reverseOrder()))
        );
    }

}
