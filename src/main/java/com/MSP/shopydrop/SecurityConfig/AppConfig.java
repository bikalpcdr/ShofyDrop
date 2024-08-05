package com.MSP.shopydrop.SecurityConfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class AppConfig {
    @Value("${DB_URL")
    private String dbUrl;

    @Value("${DB_USERNAME}")
    private String dbUsername;

    @Value("${DB_PASSWORD}")
    private String dbPassword;

    @Value("${DB_API_DOC_PATH}")
    private String dbApiPath;

    @Value("${DB_SWAGGER_UI_PATH}")
    private String dbSwaggerPath;
}
