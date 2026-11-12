# Organisationsnummer

[![Test](https://github.com/organisationsnummer/java/actions/workflows/test.yml/badge.svg?branch=main)](https://github.com/organisationsnummer/java/actions/workflows/test.yml)

Validate Swedish organization numbers.

Follows version 1.1 of the [specification](https://github.com/organisationsnummer/meta#package-specification-v11).

## Installation

The package is published on [Maven Central](https://central.sonatype.com/artifact/dev.organisationsnummer/organisationsnummer).

Maven:

```xml
<dependency>
  <groupId>dev.organisationsnummer</groupId>
  <artifactId>organisationsnummer</artifactId>
  <version>1.2.1</version>
</dependency>
```

Gradle:

```groovy
dependencies {
    implementation "dev.organisationsnummer:organisationsnummer:1.2.1"
}
```

## Example

```java
import dev.organisationsnummer.*;

Organisationsnummer.valid("202100-5489");
//=> true
```

See [OrganisationsnummerTest.java](https://github.com/organisationsnummer/java/blob/main/src/test/java/OrganisationsnummerTest.java) for more examples.

## In memoriam

Fredrik "Frozzare" Forsmo (1991-2026) was the initiator, co-founder and a core contributor of the organisationsnummer project. This library carries his work. He is missed.

## License

MIT
