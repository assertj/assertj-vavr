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

import io.vavr.control.Try;
import org.assertj.core.internal.bytebuddy.dynamic.loading.ClassInjector;
import org.assertj.core.internal.bytebuddy.dynamic.loading.ClassLoadingStrategy;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

class ClassLoadingStrategyFactory {

    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    private static final Method PRIVATE_LOOKUP_IN = Try.of(
        () -> MethodHandles.class.getMethod("privateLookupIn", Class.class, MethodHandles.Lookup.class)
    ).getOrElse((Method) null);

    static ClassLoadingStrategy<ClassLoader> classLoadingStrategy(Class<?> assertClass) {
        if (ClassInjector.UsingReflection.isAvailable()) {
            return ClassLoadingStrategy.Default.INJECTION;
        } else if (ClassInjector.UsingLookup.isAvailable() && PRIVATE_LOOKUP_IN != null) {
            try {
                return ClassLoadingStrategy.UsingLookup.of(PRIVATE_LOOKUP_IN.invoke(null, assertClass, LOOKUP));
            } catch (Exception e) {
                throw new IllegalStateException("Could not access package of " + assertClass, e);
            }
        } else {
            throw new IllegalStateException("No code generation strategy available");
        }
    }

}
