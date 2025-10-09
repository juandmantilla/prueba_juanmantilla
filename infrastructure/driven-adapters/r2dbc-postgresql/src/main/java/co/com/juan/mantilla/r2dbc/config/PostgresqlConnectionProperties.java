package co.com.juan.mantilla.r2dbc.config;

import io.r2dbc.postgresql.client.SSLMode;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "adapters.r2dbc")
public record PostgresqlConnectionProperties(
        String host,
        Integer port,
        String database,
        String schema,
        String username,
        String password,
        SSLMode sslmode) {
}
