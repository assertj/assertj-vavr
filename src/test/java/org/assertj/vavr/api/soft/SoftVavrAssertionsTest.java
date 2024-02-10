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
 * Copyright 2017-2024 the original author or authors.
 */
package org.assertj.vavr.api.soft;

import io.vavr.Lazy;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.MultipleFailuresError;

import static org.assertj.vavr.api.soft.SoftVavrAssertionsHelper.runSoftlyAssertions;
import static org.assertj.vavr.api.soft.SoftVavrAssertionsHelper.verifyErrors;
import static org.junit.jupiter.api.Assertions.fail;

class SoftVavrAssertionsTest {

    @Test
    void all_assertions_should_pass() {
        SoftVavrAssertions softly = new SoftVavrAssertions();
        softly.assertThat(Option.some("value")).contains("value");
        softly.assertThat(List.of(1, 2)).containsOnly(1, 2);
        softly.assertAll();
    }

    @Test
    void should_be_able_to_catch_exceptions_thrown_by_all_proxied_methods() {
        Assertions.setRemoveAssertJRelatedElementsFromStackTrace(false);

        // required to check error message in catch section
        Lazy<Double> evaluated = Lazy.of(Math::random);
        Double evaluatedVal = evaluated.get();

        try {
            SoftVavrAssertions softly = new SoftVavrAssertions();
            runSoftlyAssertions(evaluated, softly);
            softly.assertAll();
        } catch (MultipleFailuresError e) {
            verifyErrors(evaluatedVal, e);
            return;
        }

        fail("Should not reach here");
    }

}
