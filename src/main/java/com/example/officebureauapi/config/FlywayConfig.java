package com.example.officebureauapi.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(prefix = "spring", name = "flyway.enabled", matchIfMissing = true)
public class FlywayConfig {

    @Bean
    public Flyway flywayMigrationStrategy(DataSource dataSource) {
        Flyway flyway =
            Flyway.configure()
                    .baselineOnMigrate(true)
                    .validateOnMigrate(false)
                    .locations("db/migration")
                    .dataSource(dataSource)
                    .load();
            flyway.migrate();
        return flyway;
    }
}
