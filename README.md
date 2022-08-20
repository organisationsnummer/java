# Organisationsnummer

[![Test](https://github.com/organisationsnummer/java/actions/workflows/test.yml/badge.svg?branch=master)](https://github.com/organisationsnummer/java/actions/workflows/test.yml)

Validate Swedish organization numbers.

Follows version 1.1 of the [specification](https://github.com/organisationsnummer/meta#package-specification-v11).

## Example

```php
import dev.organisationsnummer.*;

Organisationsnummer.valid("202100-5489");
//=> true
```

See [https://github.com/organisationsnummer/java/blob/master/src/test/java/OrganisationsnummerTest.java](OrganisationsnummerTest.java) for more examples.

## License

MIT
