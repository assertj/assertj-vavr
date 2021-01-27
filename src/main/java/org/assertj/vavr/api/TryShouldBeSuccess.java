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

import org.assertj.core.error.BasicErrorMessageFactory;
import java.io.StringWriter;

import static java.lang.String.format;

/**
 * Build error message when a value should be present in an {@link io.vavr.control.Try}.
 *
 * @author Grzegorz Piwowarek
 */
class TryShouldBeSuccess extends BasicErrorMessageFactory {

    private TryShouldBeSuccess(Class<? extends Throwable> clazz, String message, StackTraceElement[] stackTrace) {
        super(format("%nExpecting Try to be a Success, but was a Failure:" +
                "%n- exception class: %s" +
                "%n- message: %s" +
                "%n- stack trace:\n%s", clazz.getName(), message, asString(stackTrace)));
    }

    private static String asString(StackTraceElement[] stackTrace) {
        StringWriter s = new StringWriter();
        for (StackTraceElement traceElement : stackTrace)
            s.append("\tat ").append(String.valueOf(traceElement)).append("\n");
        return s.toString();
    }

    /**
     * Indicates that a value should be present in an empty {@link io.vavr.control.Try}.
     *
     * @return a error message factory.
     * @throws java.lang.NullPointerException if Try is null.
     */
    static TryShouldBeSuccess shouldBeSuccess(Class<? extends Throwable> clazz, String message, StackTraceElement[] stackTrace) {
        return new TryShouldBeSuccess(clazz, message, stackTrace);
    }
}
