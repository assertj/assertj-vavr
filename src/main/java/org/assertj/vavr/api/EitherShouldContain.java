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
 * Copyright 2012-2018 the original author or authors.
 */

import io.vavr.control.Either;

import org.assertj.core.error.BasicErrorMessageFactory;

/**
 * Build error message when an {@link Either}
 * should contain a specific right value.
 *
 * @author Michał Chmielarz
 */
class EitherShouldContain extends BasicErrorMessageFactory {

    private static final String EXPECTING_TO_CONTAIN = "%nExpecting:%n  <%s>%nto contain:%n  <%s>%nbut did not.";
    private static final String EXPECTING_TO_CONTAIN_SAME = "%nExpecting:%n  <%s>%nto contain the instance (i.e. compared with ==):%n  <%s>%nbut did not.";
    private static final String EXPECTING_TO_CONTAIN_BUT_IS_LEFT = "%nExpecting:%n  <%s>%nto contain on right side:%n  <%s>%nbut was left sided.";
    private static final String EXPECTING_TO_CONTAIN_BUT_IS_RIGHT = "%nExpecting:%n  <%s>%nto contain on right side:%n  <%s>%nbut was right sided.";

    private EitherShouldContain(String message, Object actual, Object expected) {
        super(message, actual, expected);
    }

    private EitherShouldContain(String message, Object expected) {
        super(message, expected);
    }

    /**
     * Indicates that the provided {@link Either} does not contain the provided argument as right value.
     *
     * @param either        the {@link Either} which contains a value.
     * @param expectedValue the value we expect to be in the provided {@link Either} on the right side.
     * @param <LEFT>        the type of the value contained in the {@link Either} on the left side.
     * @param <RIGHT>       the type of the value contained in the {@link Either} on the right side.
     *
     * @return an error message factory
     */
    static <LEFT, RIGHT> EitherShouldContain shouldContainOnRight(Either<LEFT, RIGHT> either, RIGHT expectedValue) {
        return either.isRight() ?
          new EitherShouldContain(EXPECTING_TO_CONTAIN, either, expectedValue) :
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
     *
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
     *
     * @return an error message factory
     */
    static <LEFT, RIGHT> EitherShouldContain shouldContainOnLeft(Either<LEFT, RIGHT> either,
                                                                  LEFT expectedValue) {
        return either.isLeft() ?
            new EitherShouldContain(EXPECTING_TO_CONTAIN, either, expectedValue) :
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
     *
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
     *
     * @return an error message factory.
     */
    static EitherShouldContain shouldContainButIsLeft(Either<?, ?> either, Object expectedRightValue) {
        return new EitherShouldContain(EXPECTING_TO_CONTAIN_BUT_IS_LEFT, either, expectedRightValue);
    }

    /**
     * Indicates that an {@link Either} is right so it doesn't contain the expected left value.
     *
     * @param expectedRightValue the left value we expect to be in an {@link Either}.
     *
     * @return an error message factory.
     */
    static EitherShouldContain shouldContainButIsRight(Either<?, ?> either, Object expectedRightValue) {
        return new EitherShouldContain(EXPECTING_TO_CONTAIN_BUT_IS_RIGHT, either, expectedRightValue);
    }
}

