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
 * Copyright 2012-2017 the original author or authors.
 */
package org.assertj.vavr.error;

import org.assertj.core.error.BasicErrorMessageFactory;

import static java.lang.String.format;

/**
 * Build error message when a value should not be present in an {@link io.vavr.control.Try}.
 *
 * @author Bartlomiej Kuczynski
 */
public class TryShouldBeFailure extends BasicErrorMessageFactory {

    private TryShouldBeFailure() {
        super(format("%nExpecting Try to be a Failure, but wasn't"));
    }

    /**
     * Indicates that a value should not be present in an non-empty {@link io.vavr.control.Try}.
     *
     * @return a error message factory.
     * @throws NullPointerException if Try is null.
     */
    public static TryShouldBeFailure shouldBeFailure() {
        return new TryShouldBeFailure();
    }
}
