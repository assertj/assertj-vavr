package org.assertj.vavr.api;

import org.assertj.core.internal.bytebuddy.dynamic.loading.ClassInjector;
import org.assertj.core.internal.bytebuddy.dynamic.loading.ClassLoadingStrategy;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

class ClassLoadingStrategyFactory {

    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    private static final Method PRIVATE_LOOKUP_IN;

    static {
        Method privateLookupIn;
        try {
            privateLookupIn = MethodHandles.class.getMethod("privateLookupIn", Class.class, MethodHandles.Lookup.class);
        } catch (Exception e) {
            privateLookupIn = null;
        }
        PRIVATE_LOOKUP_IN = privateLookupIn;
    }

    static ClassLoadingStrategy<ClassLoader> classLoadingStrategy(Class<?> assertClass) {
        if (ClassInjector.UsingReflection.isAvailable()) {
            return ClassLoadingStrategy.Default.INJECTION;
        } else if (ClassInjector.UsingLookup.isAvailable()) {
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
