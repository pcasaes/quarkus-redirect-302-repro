# Quarkus REST Client 302 GET Reproducer

Minimal Java reproducer for a Quarkus REST client following a `302` returned from a `GET`.

https://github.com/quarkusio/quarkus/issues/53914

## What it tests

- A local JDK HTTP server returns `302 Location: /target` for `GET /redirect`
- The Quarkus REST client is configured with `follow-redirects=true`
- The app endpoint `/exercise` calls the client
- The test expects the redirected response body `redirect-target-ok`

## Run

```bash
./mvnw clean verify
```

## Switch Quarkus versions

Change `quarkus.platform.version` in `pom.xml` between:

- `3.33.1`
- `3.34.6`

Then rerun:

```bash
./mvnw clean verify
```
