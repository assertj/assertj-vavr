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
 * Copyright 2017-2020 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.Lazy;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.vavr.api.VavrAssumptions.assumeThat;
import static org.assertj.vavr.api.BaseAssumptionRunner.assumptionRunner;

public class Lazy_assertion_methods_in_assumptions_Test extends BaseAssumptionsTest {

    public static Stream<AssumptionRunner<?>> provideAssumptionsRunners() {
        return Stream.of(
                assumptionRunner(evaluated(Math::random),
                        value -> assumeThat(value).isEvaluated(),
                        value -> assumeThat(value).isNotEvaluated()),
                assumptionRunner(notEvaluated(Math::random),
                        value -> assumeThat(value).isNotEvaluated(),
                        value -> assumeThat(value).isEvaluated())
        );
    }

    private static Lazy<Double> evaluated(Supplier<Double> random) {
        final Lazy<Double> evaluated = Lazy.of(random);
        evaluated.get();
        return evaluated;
    }

    private static Lazy<Double> notEvaluated(Supplier<Double> random) {
        return Lazy.of(random);
    }

}
