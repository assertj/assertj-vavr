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
 * Copyright 2017-2022 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Either;

import java.util.stream.Stream;

import static org.assertj.vavr.api.BaseAssumptionRunner.assumptionRunner;
import static org.assertj.vavr.api.VavrAssumptions.assumeThat;

public class Either_assertion_methods_in_assumptions_Test extends BaseAssumptionsTest {

    private static final String IT_S_GOOD = "It's good";
    private static final String BAD_ONE = "Bad one";

    public static Stream<AssumptionRunner<?>> provideAssumptionsRunners() {
        return Stream.of(
                assumptionRunner(Either.right(IT_S_GOOD),
                        value -> assumeThat(value).isRight(),
                        value -> assumeThat(value).isLeft()),
                assumptionRunner(Either.right(IT_S_GOOD),
                        value -> assumeThat(value).containsOnRight(IT_S_GOOD),
                        value -> assumeThat(value).containsOnRight(BAD_ONE)),
                assumptionRunner(Either.right(IT_S_GOOD),
                        value -> assumeThat(value).containsRightSame(IT_S_GOOD),
                        value -> assumeThat(value).containsRightSame(new String("It's good"))),
                assumptionRunner(Either.right(IT_S_GOOD),
                        value -> assumeThat(value).containsRightInstanceOf(String.class),
                        value -> assumeThat(value).containsRightInstanceOf(Integer.class)),
                assumptionRunner(Either.left(BAD_ONE),
                        value -> assumeThat(value).isLeft(),
                        value -> assumeThat(value).isRight()),
                assumptionRunner(Either.left(BAD_ONE),
                        value -> assumeThat(value).containsOnLeft(BAD_ONE),
                        value -> assumeThat(value).containsOnLeft(IT_S_GOOD)),
                assumptionRunner(Either.left(BAD_ONE),
                        value -> assumeThat(value).containsLeftSame(BAD_ONE),
                        value -> assumeThat(value).containsLeftSame(new String("Bad one"))),
                assumptionRunner(Either.left(BAD_ONE),
                        value -> assumeThat(value).containsLeftInstanceOf(String.class),
                        value -> assumeThat(value).containsLeftInstanceOf(Integer.class))
        );
    }

}
