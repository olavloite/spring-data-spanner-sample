# Spring Data Spanner Sample

Small sample application that shows how to configure the session pool and number of gRPC channels
when working with the Spring Data Spanner library.

The [sample application](src/main/java/com/google/cloud/spanner/spring/example/QuickStartSample.java) will try to execute 200 concurrent transactions.
Change the `NUM_CONCURRENT_TRANSACTIONS_` constant to increase/decrease the number of transactions to
compare behavior with more/less transactions and with different session pool settings.

## Session Pool Configuration

The session pool configuration can be added to the `application.properties` file. The specific
settings for the session pool are:

```properties
# The minimum number of sessions in the pool. The pool will always be initialized with this number
# of sessions at startup, and will never drop below this number.
spring.cloud.gcp.spanner.minSessions=100

# The maximum number of sessions in teh pool. The pool will never grow beyond this number of sessions.
spring.cloud.gcp.spanner.maxSessions=400

# The number of gRPC channels to use. One gRPC channel can handle up to 100 requests / streams
# concurrently. Set this to (at least) maxSessions / 100.
spring.cloud.gcp.spanner.numRpcChannels=4
```

Full documentation for all configuration parameters can be found here: https://docs.spring.io/spring-cloud-gcp/docs/1.1.0.M1/reference/html/_spring_data_cloud_spanner.html#_cloud_spanner_settings

