[![build](https://github.com/assertj/assertj-vavr/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/assertj/assertj-vavr/actions/workflows/build.yml)
[![Maven Central Version](https://img.shields.io/maven-central/v/org.assertj/assertj-vavr)](https://central.sonatype.com/artifact/org.assertj/assertj-vavr/versions)
[![javadoc](https://javadoc.io/badge2/org.assertj/assertj-vavr/javadoc.svg)](https://javadoc.io/doc/org.assertj/assertj-vavr)

# assertj-vavr

AssertJ assertions for [Vavr](https://www.vavr.io/) types.

## Installation

**Gradle:**
```groovy
testImplementation "org.assertj:assertj-vavr:$version"
```

**Maven:**
```xml
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-vavr</artifactId>
    <version>${version}</version>
    <scope>test</scope>
</dependency>
```

## Usage

All assertions are available via the `VavrAssertions.assertThat()` static factory:

```java
import static org.assertj.vavr.api.VavrAssertions.assertThat;
```

### Supported types

| Vavr type      | Assertion class       |
|----------------|-----------------------|
| `Option<T>`    | `OptionAssert`        |
| `Either<L, R>` | `EitherAssert`        |
| `Try<T>`       | `TryAssert`           |
| `Validation<E, T>` | `ValidationAssert` |
| `Lazy<T>`      | `LazyAssert`          |
| `Seq<T>`       | `SeqAssert`           |
| `Set<T>`       | `SetAssert`           |
| `Map<K, V>`    | `MapAssert`           |
| `Multimap<K, V>` | `MultimapAssert`    |

### Option

```java
assertThat(Option.of("hello")).isDefined();
assertThat(Option.none()).isEmpty();
assertThat(Option.of("hello")).contains("hello");
assertThat(Option.of("hello")).containsInstanceOf(String.class);
assertThat(Option.of("hello")).hasValueSatisfying(v -> assertThat(v).startsWith("he"));
```

### Either

```java
assertThat(Either.right("ok")).isRight();
assertThat(Either.left("error")).isLeft();
assertThat(Either.right("ok")).containsRight("ok");
assertThat(Either.left("error")).containsLeft("error");
assertThat(Either.right("ok")).hasRightValueSatisfying(v -> assertThat(v).isEqualTo("ok"));
```

### Try

```java
assertThat(Try.success("ok")).isSuccess();
assertThat(Try.failure(new RuntimeException())).isFailure();
assertThat(Try.success("ok")).contains("ok");
assertThat(Try.success("ok")).hasValueSatisfying(v -> assertThat(v).isEqualTo("ok"));
assertThat(Try.failure(new RuntimeException("oops"))).failReasonHasMessage("oops");
```

### Validation

```java
assertThat(Validation.valid("ok")).isValid();
assertThat(Validation.invalid("error")).isInvalid();
assertThat(Validation.valid("ok")).containsValid("ok");
assertThat(Validation.invalid("error")).containsInvalid("error");
```

### Lazy

```java
assertThat(Lazy.of(() -> "value")).isNotEvaluated();

Lazy<String> evaluated = Lazy.of(() -> "value");
evaluated.get();
assertThat(evaluated).isEvaluated();
```

### Seq

```java
assertThat(List.of(1, 2, 3)).contains(1, Index.atIndex(0));
assertThat(List.of(1, 2, 3)).isSorted();
assertThat(List.of(3, 1, 2)).containsExactlyInAnyOrder(1, 2, 3);
```

### Map

```java
assertThat(HashMap.of("key", "value")).containsKey("key");
assertThat(HashMap.of("key", "value")).containsValue("value");
assertThat(HashMap.of("key", "value")).containsEntry("key", "value");
assertThat(HashMap.empty()).isEmpty();
```

### Soft assertions

Use `SoftVavrAssertions` to collect all failures instead of stopping at the first one:

```java
SoftVavrAssertions softly = new SoftVavrAssertions();
softly.assertThat(Option.of("hello")).contains("hello");
softly.assertThat(Try.success("ok")).isSuccess();
softly.assertAll();
```

### Assumptions

Use `VavrAssumptions` to skip tests when preconditions are not met:

```java
import static org.assertj.vavr.api.VavrAssumptions.assumeThat;

assumeThat(Option.of("hello")).isDefined(); // test is skipped if Option is empty
```

## Contributing

Contributions are welcome. Please follow the [assertj-core contributing guide](https://github.com/assertj/assertj/blob/main/CONTRIBUTING.md).
