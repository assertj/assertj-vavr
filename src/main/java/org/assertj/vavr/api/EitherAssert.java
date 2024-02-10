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
package org.assertj.vavr.api;

import io.vavr.control.Either;

/**
 * Assertions for {@link io.vavr.control.Either}.
 *
 * @param <LEFT>  type of the value on the left contained in the {@link io.vavr.control.Either}.
 * @param <RIGHT> type of the value on the right contained in the {@link io.vavr.control.Either}.
 * @author Alex Dukhno
 */
public class EitherAssert<LEFT, RIGHT> extends AbstractEitherAssert<EitherAssert<LEFT, RIGHT>, LEFT, RIGHT> {
    EitherAssert(Either<LEFT, RIGHT> actual) {
        super(actual, EitherAssert.class);
    }
}
