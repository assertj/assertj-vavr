[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.assertj/assertj-vavr/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.assertj/assertj-vavr)

# assertj-vavr
Assertions for [Vavr](http://www.vavr.io/)

## Usage

### Dependency in your project:
- build.gradle:
```groovy
implementation "org.assertj:assertj-vavr:$version"
```
- pom.xml:
```xml
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-vavr</artifactId>
    <version>${version}</version>
    <scope>test</scope>
</dependency>
```

### Assertions
Starting point is `org.assertj.vavr.api.VavrAssertions` class. It contains assertions for all supported Vavr types.

## <a name="contributing"/>Want to contribute?

You are encouraged to contribute any missing, useful assertions. To do so, please read the [contributing section](https://github.com/joel-costigliola/assertj-core/blob/main/CONTRIBUTING.md) from assertj-core.
