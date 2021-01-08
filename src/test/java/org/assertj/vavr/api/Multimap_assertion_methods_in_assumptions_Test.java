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

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.*;

import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.vavr.api.BaseAssumptionRunner.assumptionRunner;
import static org.assertj.vavr.api.VavrAssumptions.assumeThat;

public class Multimap_assertion_methods_in_assumptions_Test extends BaseAssumptionsTest {

    private static final Multimap<String, String> RACES =
            HashMultimap.withSeq().of("hobbit", "frodo", "dwarf", "gimli");
    private static final BiConsumer<String, String> OK_CONSUMER = (key, value) -> assertThat(key).isLowerCase();
    private static final BiConsumer<String, String> FAIL_CONSUMER = (key, value) -> assertThat(key).isUpperCase();
    private static final Tuple2<String, String> FRODO = Tuple.of("hobbit", "frodo");
    private static final Tuple2<String, String> GIMLI = Tuple.of("dwarf", "gimli");
    private static final Tuple2<String, String> LEGOLAS = Tuple.of("elf", "legolas");

    public static Stream<AssumptionRunner<?>> provideAssumptionsRunners() {
        return Stream.of(
                assumptionRunner(RACES,
                        value -> assumeThat(value).allSatisfy(OK_CONSUMER),
                        value -> assumeThat(value).allSatisfy(FAIL_CONSUMER)),
                assumptionRunner(RACES,
                        value -> assumeThat(value).containsAnyOf(FRODO),
                        value -> assumeThat(value).containsAnyOf(LEGOLAS)),
                assumptionRunner(RACES,
                        value -> assumeThat(value).contains(FRODO),
                        value -> assumeThat(value).contains(LEGOLAS)),
                assumptionRunner(RACES,
                        value -> assumeThat(value).containsAllEntriesOf(List.of(FRODO, GIMLI)),
                        value -> assumeThat(value).containsAllEntriesOf(List.of(FRODO, LEGOLAS))),
                assumptionRunner(RACES,
                        value -> assumeThat(value).containsEntry("hobbit", "frodo"),
                        value -> assumeThat(value).containsEntry("elf", "legolas")),
                assumptionRunner(RACES,
                        value -> assumeThat(value).containsExactly(GIMLI, FRODO),
                        value -> assumeThat(value).containsExactly(FRODO)),
                assumptionRunner(RACES,
                        value -> assumeThat(value).containsKey("hobbit"),
                        value -> assumeThat(value).containsKey("man")),
                assumptionRunner(RACES,
                        value -> assumeThat(value).containsKeys("hobbit", "dwarf"),
                        value -> assumeThat(value).containsKeys("man", "elf")),
                assumptionRunner(RACES,
                        value -> assumeThat(value).containsOnly(List.of(FRODO, GIMLI)),
                        value -> assumeThat(value).containsOnly(List.of(GIMLI, LEGOLAS))),
                assumptionRunner(RACES,
                        value -> assumeThat(value).containsOnlyKeys("hobbit", "dwarf"),
                        value -> assumeThat(value).containsOnlyKeys("man", "elf")),
                assumptionRunner(RACES,
                        value -> assumeThat(value).containsValue("frodo"),
                        value -> assumeThat(value).containsValue("aragorn")),
                assumptionRunner(RACES,
                        value -> assumeThat(value).containsValues("frodo", "gimli"),
                        value -> assumeThat(value).containsValues("aragorn", "legolas")),
                assumptionRunner(RACES,
                        value -> assumeThat(value).doesNotContainEntry("elf", "legolas"),
                        value -> assumeThat(value).doesNotContainEntry("hobbit", "frodo")),
                assumptionRunner(RACES,
                        value -> assumeThat(value).doesNotContain(LEGOLAS),
                        value -> assumeThat(value).doesNotContain(FRODO)),
                assumptionRunner(RACES,
                        value -> assumeThat(value).hasSameSizeAs(new String[]{"frodo", "gimli"}),
                        value -> assumeThat(value).hasSameSizeAs(new String[]{"frodo"})),
                assumptionRunner(RACES,
                        value -> assumeThat(value).hasSameSizeAs(Array.of(FRODO, GIMLI)),
                        value -> assumeThat(value).hasSameSizeAs(Array.of(FRODO))),
                assumptionRunner(RACES,
                        value -> assumeThat(value).hasSize(2),
                        value -> assumeThat(value).hasSize(1))
        );
    }
}
