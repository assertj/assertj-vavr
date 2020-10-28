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

/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2012-2019 the original author or authors.
 */

import io.vavr.control.Either;
import org.assertj.vavr.internal.error.VavrErrorMessageFactory;

/**
 * Build error message when an {@link Either} should contain a specific value.
 *
 * @author Alex Dukhno
 * @author Michał Chmielarz
 */
class EitherShouldContain extends VavrErrorMessageFactory {

    private static final String EXPECTING_TO_CONTAIN_ON_LEFT = "%nExpecting:%n  <%s>%nto contain:%n  <%s> on the [LEFT]%nbut did not.";
    private static final String EXPECTING_TO_CONTAIN_ON_RIGHT = "%nExpecting:%n  <%s>%nto contain:%n  <%s> on the [RIGHT]%nbut did not.";
    private static final String EXPECTING_TO_CONTAIN_SAME = "%nExpecting:%n  <%s>%nto contain the instance (i.e. compared with ==):%n  <%s>%nbut did not.";
    private static final String EXPECTING_TO_CONTAIN_BUT_IS_LEFT = "%nExpecting:%n  <%s>%nto contain on right side:%n  <%s>%nbut was left sided.";
    private static final String EXPECTING_TO_CONTAIN_BUT_IS_RIGHT = "%nExpecting:%n  <%s>%nto contain on right side:%n  <%s>%nbut was right sided.";
    private static final String EXPECTING_TO_CONTAIN_ON_THE_LEFT = "%nExpecting:%n  <%s>%nto contain:%n  <%s> on the [LEFT]%nbut did not.";
    private static final String EXPECTING_TO_CONTAIN_ON_THE_RIGHT = "%nExpecting:%n  <%s>%nto contain:%n  <%s> on the [RIGHT]%nbut did not.";
    private static final String EXPECTING_ON_THE_LEFT = "%nExpecting:%n  <%s>%nto contain on the [LEFT]:%n  <%s>%nbut had <%s> on the [RIGHT].";
    private static final String EXPECTING_ON_THE_RIGHT = "%nExpecting:%n  <%s>%nto contain on the [RIGHT]:%n  <%s>%nbut had <%s> on the [LEFT].";

    private EitherShouldContain(String message, Object actual, Object expected) {
        super(message, actual, expected);

    }

    private EitherShouldContain(String format, Object... arguments) {
        super(format, arguments);
    }

    /**
     * Indicates that the provided {@link io.vavr.control.Either} does not contain the provided argument.
     *
     * @param either   the {@link io.vavr.control.Either} which contains a value.
     * @param expected the value we expect to be in the provided {@link io.vavr.control.Either}.
     * @param <LEFT>   the type of the value contained in the {@link io.vavr.control.Either} on the left.
     * @param <RIGHT>  the type of the value contained in the {@link io.vavr.control.Either} on the right.
     * @return an error message factory
     */
    static <LEFT, RIGHT> EitherShouldContain shouldContain(Either<LEFT, RIGHT> either,
                                                           Object expected) {
        return either.isLeft() ?
          new EitherShouldContain(EXPECTING_TO_CONTAIN_ON_THE_LEFT, either, expected) :
          new EitherShouldContain(EXPECTING_TO_CONTAIN_ON_THE_RIGHT, either, expected);
    }

    /**
     * Indicates that the provided {@link io.vavr.control.Either} does not contain the provided argument on expected side.
     *
     * @param either   the {@link io.vavr.control.Either} which contains a value.
     * @param expected the value we expect to be in the provided {@link io.vavr.control.Either}.
     * @param <LEFT>   the type of the value contained in the {@link io.vavr.control.Either} on the left.
     * @param <RIGHT>  the type of the value contained in the {@link io.vavr.control.Either} on the right.
     * @return an error message factory
     */
    static <LEFT, RIGHT> EitherShouldContain shouldContainOnOtherSide(Either<LEFT, RIGHT> either,
                                                                      Object expected) {
        return either.isRight()
          ? new EitherShouldContain(EXPECTING_ON_THE_LEFT, either, expected, either.get())
          : new EitherShouldContain(EXPECTING_ON_THE_RIGHT, either, expected, either.getLeft());
    }

    /**
     * Indicates that the provided {@link Either} does not contain the provided argument as right value.
     *
     * @param either        the {@link Either} which contains a value.
     * @param expectedValue the value we expect to be in the provided {@link Either} on the right side.
     * @param <LEFT>        the type of the value contained in the {@link Either} on the left side.
     * @param <RIGHT>       the type of the value contained in the {@link Either} on the right side.
     * @return an error message factory
     */
    static <LEFT, RIGHT> EitherShouldContain shouldContainOnRight(Either<LEFT, RIGHT> either, RIGHT expectedValue) {
        return either.isRight() ?
          new EitherShouldContain(EXPECTING_TO_CONTAIN_ON_RIGHT, either, expectedValue) :
          shouldContainButIsLeft(either, expectedValue);
    }

    /**
     * Indicates that the provided {@link Either} does not contain the provided argument (judging by reference
     * equality).
     *
     * @param either        the {@link Either} which contains a value.
     * @param expectedValue the value we expect to be in the provided {@link Either} on the right side.
     * @param <LEFT>        the type of the value contained in the {@link Either} on the left side.
     * @param <RIGHT>       the type of the value contained in the {@link Either} on the right side.
     * @return an error message factory
     */
    static <LEFT, RIGHT> EitherShouldContain shouldContainSameOnRight(Either<LEFT, RIGHT> either, RIGHT expectedValue) {
        return either.isRight() ?
          new EitherShouldContain(EXPECTING_TO_CONTAIN_SAME, either, expectedValue) :
          shouldContainButIsLeft(either, expectedValue);
    }

    /**
     * Indicates that the provided {@link Either} does not contain the provided argument as right value.
     *
     * @param either        the {@link Either} which contains a value.
     * @param expectedValue the value we expect to be in the provided {@link Either} on the left side.
     * @param <LEFT>        the type of the value contained in the {@link Either} on the left side.
     * @param <RIGHT>       the type of the value contained in the {@link Either} on the right side.
     * @return an error message factory
     */
    static <LEFT, RIGHT> EitherShouldContain shouldContainOnLeft(Either<LEFT, RIGHT> either,
                                                                 LEFT expectedValue) {
        return either.isLeft() ?
          new EitherShouldContain(EXPECTING_TO_CONTAIN_ON_LEFT, either, expectedValue) :
          shouldContainButIsRight(either, expectedValue);
    }

    /**
     * Indicates that the provided {@link Either} does not contain the provided argument (judging by reference
     * equality).
     *
     * @param either        the {@link Either} which contains a value.
     * @param expectedValue the value we expect to be in the provided {@link Either} on the left side.
     * @param <LEFT>        the type of the value contained in the {@link Either} on the left side.
     * @param <RIGHT>       the type of the value contained in the {@link Either} on the right side.
     * @return an error message factory
     */
    static <LEFT, RIGHT> EitherShouldContain shouldContainSameOnLeft(Either<LEFT, RIGHT> either,
                                                                     LEFT expectedValue) {
        return either.isLeft() ?
          new EitherShouldContain(EXPECTING_TO_CONTAIN_SAME, either, expectedValue) :
          shouldContainButIsRight(either, expectedValue);
    }

    /**
     * Indicates that an {@link Either} is left so it doesn't contain the expected right value.
     *
     * @param expectedRightValue the right value we expect to be in an {@link Either}.
     * @return an error message factory.
     */
    static EitherShouldContain shouldContainButIsLeft(Either<?, ?> either, Object expectedRightValue) {
        return new EitherShouldContain(EXPECTING_TO_CONTAIN_BUT_IS_LEFT, either, expectedRightValue);
    }

    /**
     * Indicates that an {@link Either} is right so it doesn't contain the expected left value.
     *
     * @param expectedRightValue the left value we expect to be in an {@link Either}.
     * @return an error message factory.
     */
    static EitherShouldContain shouldContainButIsRight(Either<?, ?> either, Object expectedRightValue) {
        return new EitherShouldContain(EXPECTING_TO_CONTAIN_BUT_IS_RIGHT, either, expectedRightValue);
    }
}

