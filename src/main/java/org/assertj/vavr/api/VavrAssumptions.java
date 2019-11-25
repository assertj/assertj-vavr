package org.assertj.vavr.api;

import io.vavr.Lazy;
import io.vavr.collection.Map;
import io.vavr.collection.Multimap;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import org.assertj.core.internal.bytebuddy.ByteBuddy;
import org.assertj.core.internal.bytebuddy.TypeCache;
import org.assertj.core.internal.bytebuddy.TypeCache.SimpleKey;
import org.assertj.core.internal.bytebuddy.dynamic.scaffold.TypeValidation;
import org.assertj.core.internal.bytebuddy.implementation.Implementation;
import org.assertj.core.internal.bytebuddy.implementation.MethodDelegation;
import org.assertj.core.internal.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.assertj.core.internal.bytebuddy.implementation.bind.annotation.RuntimeType;
import org.assertj.core.internal.bytebuddy.implementation.bind.annotation.SuperCall;
import org.assertj.core.internal.bytebuddy.implementation.bind.annotation.This;
import org.assertj.core.util.CheckReturnValue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.any;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.vavr.api.ClassLoadingStrategyFactory.classLoadingStrategy;

public class VavrAssumptions {

    /**
     * This NamingStrategy takes the original class's name and adds a suffix to distinguish it.
     * The default is ByteBuddy but for debugging purposes, it makes sense to add AssertJ as a name.
     */
    private static ByteBuddy BYTE_BUDDY = new ByteBuddy().with(TypeValidation.DISABLED)
            .with(new AuxiliaryType.NamingStrategy.SuffixingRandom("Assertj$Assumptions"));

    private static final Implementation ASSUMPTION = MethodDelegation.to(AssumptionMethodInterceptor.class);

    private static final TypeCache<SimpleKey> CACHE = new TypeCache.WithInlineExpunction<>(TypeCache.Sort.SOFT);

    private static final class AssumptionMethodInterceptor {

        @RuntimeType
        public static Object intercept(@This AbstractVavrAssert<?, ?> assertion, @SuperCall Callable<Object> proxy) throws Exception {
            try {
                Object result = proxy.call();
                if (result != assertion && result instanceof AbstractVavrAssert) {
                    final AbstractVavrAssert<?, ?> assumption = asAssumption((AbstractVavrAssert<?, ?>) result);
                    return assumption.withAssertionState(assertion);
                }
                return result;
            } catch (AssertionError e) {
                throw assumptionNotMet(e);
            }
        }
    }

    /**
     * Creates a new instance of <code>{@link EitherAssert}</code> assumption.
     *
     * @param <LEFT>  type of the left value contained in the {@link Either}.
     * @param <RIGHT> type of the right value contained in the {@link Either}.
     * @param actual  the actual value.
     * @return the created assumption for assertion object.
     */
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    public static <LEFT, RIGHT> AbstractEitherAssert<?, LEFT, RIGHT> assumeThat(Either<LEFT, RIGHT> actual) {
        return asAssumption(EitherAssert.class, Either.class, actual);
    }

    /**
     * Creates a new instance of <code>{@link LazyAssert}</code> assumption.
     *
     * @param <VALUE>    type of the value contained in the {@link Lazy}.
     * @param actual the actual value.
     * @return the created assumption for assertion object.
     */
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    public static <VALUE> AbstractLazyAssert<?, VALUE> assumeThat(Lazy<VALUE> actual) {
        return asAssumption(LazyAssert.class, Lazy.class, actual);
    }

    /**
     * Creates a new instance of <code>{@link MapAssert}</code> assumption.
     *
     * @param <K>    the type of keys in the map.
     * @param <V>    the type of values in the map.
     * @param actual the actual value.
     * @return the created assumption for assertion object.
     */
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    public static <K, V> AbstractMapAssert<?, ?, K, V> assumeThat(Map<K, V> actual) {
        return asAssumption(MapAssert.class, Map.class, actual);
    }

    /**
     * Creates a new instance of <code>{@link MultimapAssert}</code> assumption.
     *
     * @param <K>    the type of keys in the multimap.
     * @param <V>    the type of values in the multimap.
     * @param actual the actual value.
     * @return the created assumption for assertion object.
     */
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    public static <K, V> AbstractMultimapAssert<?, ?, K, V> assumeThat(Multimap<K, V> actual) {
        return asAssumption(MultimapAssert.class, Multimap.class, actual);
    }

    /**
     * Creates a new instance of <code>{@link OptionAssert}</code> assumption.
     *
     * @param <VALUE> type of the value contained in the {@link Option}.
     * @param actual  the actual value.
     * @return the created assumption for assertion object.
     */
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    public static <VALUE> AbstractOptionAssert<?, VALUE> assumeThat(Option<VALUE> actual) {
        return asAssumption(OptionAssert.class, Option.class, actual);
    }

    /**
     * Creates a new instance of <code>{@link SeqAssert}</code> assumption.
     *
     * @param <ELEMENT> type of elements contained in the {@link Seq}.
     * @param actual  the actual value.
     * @return the created assumption for assertion object.
     */
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    public static <ELEMENT> AbstractSeqAssert<?, ?, ELEMENT, ?> assumeThat(Seq<ELEMENT> actual) {
        return asAssumption(SeqAssert.class, Seq.class, actual);
    }

    /**
     * Creates a new instance of <code>{@link TryAssert}</code> assumption.
     *
     * @param <VALUE> type of the value contained in the {@link io.vavr.control.Try}.
     * @param actual    the actual value.
     * @return the created assumption for assertion object.
     */
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    public static <VALUE> AbstractTryAssert<?, VALUE> assumeThat(Try<VALUE> actual) {
        return asAssumption(TryAssert.class, Try.class, actual);
    }

    /**
     * Creates a new instance of <code>{@link ValidationAssert}</code> assumption.
     *
     * @param <INVALID> type of the value in the case of the invalid {@link Validation}.
     * @param <VALID>   type of the value in the case of the valid {@link Validation}.
     * @param actual  the actual value.
     * @return the created assumption for assertion object.
     */
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    public static <INVALID, VALID> AbstractValidationAssert<?, INVALID, VALID> assumeThat(Validation<INVALID, VALID> actual) {
        return asAssumption(ValidationAssert.class, Validation.class, actual);
    }

    private static <ASSERTION, ACTUAL> ASSERTION asAssumption(Class<ASSERTION> assertionType,
                                                              Class<ACTUAL> actualType,
                                                              Object actual) {
        return asAssumption(assertionType, array(actualType), array(actual));
    }

    private static <ASSERTION> ASSERTION asAssumption(Class<ASSERTION> assertionType,
                                                      Class<?>[] constructorTypes,
                                                      Object... constructorParams) {
        try {
            Class<? extends ASSERTION> type = createAssumptionClass(assertionType);
            Constructor<? extends ASSERTION> constructor = type.getConstructor(constructorTypes);
            return constructor.newInstance(constructorParams);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException("Cannot create assumption instance", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <ASSERTION> Class<? extends ASSERTION> createAssumptionClass(Class<ASSERTION> assertClass) {
        SimpleKey cacheKey = new SimpleKey(assertClass);
        return (Class<ASSERTION>) CACHE.findOrInsert(VavrAssumptions.class.getClassLoader(),
                cacheKey,
                () -> generateAssumptionClass(assertClass));
    }

    private static <ASSERTION> Class<? extends ASSERTION> generateAssumptionClass(Class<ASSERTION> assertionType) {
        return BYTE_BUDDY.subclass(assertionType)
                .method(any())
                .intercept(ASSUMPTION)
                .make()
                .load(VavrAssumptions.class.getClassLoader(), classLoadingStrategy(assertionType))
                .getLoaded();
    }

    private static RuntimeException assumptionNotMet(AssertionError assertionError) throws ReflectiveOperationException {
        Class<?> assumptionClass = getAssumptionClass("org.junit.AssumptionViolatedException");
        if (assumptionClass != null) return assumptionNotMet(assumptionClass, assertionError);

        assumptionClass = getAssumptionClass("org.opentest4j.TestAbortedException");
        if (assumptionClass != null) return assumptionNotMet(assumptionClass, assertionError);

        assumptionClass = getAssumptionClass("org.testng.SkipException");
        if (assumptionClass != null) return assumptionNotMet(assumptionClass, assertionError);

        throw new IllegalStateException("Assumptions require JUnit, opentest4j or TestNG on the classpath");
    }

    private static Class<?> getAssumptionClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private static RuntimeException assumptionNotMet(Class<?> exceptionClass,
                                                     AssertionError e) throws ReflectiveOperationException {
        return (RuntimeException) exceptionClass.getConstructor(String.class, Throwable.class)
                .newInstance("assumption was not met due to: " + e.getMessage(), e);
    }

    // for method that change the object under test (e.g. extracting)
    private static AbstractVavrAssert<?, ?> asAssumption(AbstractVavrAssert<?, ?> assertion) {
        // @format:off
        Object actual = assertion.actual();
        if (assertion instanceof LazyAssert) return asAssumption(LazyAssert.class, Lazy.class, actual);
        if (assertion instanceof EitherAssert) return asAssumption(EitherAssert.class, Either.class, actual);
        if (assertion instanceof MapAssert) return asAssumption(MapAssert.class, Map.class, actual);
        if (assertion instanceof OptionAssert) return asAssumption(OptionAssert.class, Option.class, actual);
        if (assertion instanceof SeqAssert) return asAssumption(SeqAssert.class, Seq.class, actual);
        if (assertion instanceof TryAssert) return asAssumption(TryAssert.class, Try.class, actual);
        if (assertion instanceof ValidationAssert) return asAssumption(ValidationAssert.class, Validation.class, actual);
        // @format:on
        // should not arrive here
        throw new IllegalArgumentException("Unsupported assumption creation for " + assertion.getClass());
    }

}
