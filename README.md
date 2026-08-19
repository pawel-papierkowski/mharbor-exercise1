# Exercise

This miniproject solves programming task - see `TASK.md` for description. Both step 1 and step 2 are done.

## Technical considerations

### Conventions

This task calculates journey price, so it could be part of larger `journey` feature, so this is how I put it in project. In real project it could contain many other journey-related services, DTOs, entities, repositories, events etc.

### API

Base endpoint in controller is `/api/v1/journey`.

In theory, for cost resolution we should use `GET` per REST convention, but `GET` does not support body, and we would have to use query parameters or path parameters.

I prefer using JSON in body everywhere applicable for consistency. For example, retrieving data from table while using filters/pagination/other options is significantly more convenient when you use JSON in body instead of very long and fragile list of query parameters in URL.

### Service itself

We use simple constants for discount rate and distance.
In real project, it likely would be in separate service next to `JourneyCostService` that would query database for current/applicable discount values, as presumably discounts and other price-related parameters would change every so often.

We use `Float` (wrapper for `float` to properly detect null in request) instead of `BigDecimal` to keep it simple (we handle `float` precision issues).

## New business requirement

According to step 2 of task, customer called TransX receives an additional **5% discount on the final journey cost**. It is NOT part of distance discount, but discount that is applied separately at end.

As with previous discount, we use simple constants. Again, in real project we would probably have `customer` feature that uses customer table and associated table with discount and validity period for given customer.

Notes:
- Customer name given in request is case-sensitive.
- Rounding is now done at end of calculations. I decided to not do it in every stage. Should not impact results anyway.