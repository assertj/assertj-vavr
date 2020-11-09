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
package org.assertj.vavr.internal.error;

import io.vavr.control.Either;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import org.assertj.core.presentation.StandardRepresentation;

public class VavrRepresentation extends StandardRepresentation {

    @Override
    public String toStringOf(Object object) {
        if (object == null) return null;
        if (object instanceof Either) return toStringOf((Either<?, ?>) object);
        if (object instanceof Try) return toStringOf((Try<?>) object);
        if (object instanceof Validation) return toStringOf((Validation<?, ?>) object);
        return super.toStringOf(object);
    }

    private String toStringOf(Either<?, ?> either) {
        return either.toString();
    }

    private String toStringOf(Try<?> aTry) {
        return aTry.fold(
                v -> v == null ? "null" : String.format("Failure(%s)", v),
                v -> v == null ? "null" : String.format("Success(%s)", v));
    }

    private String toStringOf(Validation<?, ?> validation) {
        return validation.fold(
                v -> v == null ? "null" : String.format("Invalid(%s)", v),
                v -> v == null ? "null" : String.format("Valid(%s)", v));
    }

}
