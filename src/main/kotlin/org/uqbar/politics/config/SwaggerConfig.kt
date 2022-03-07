package org.uqbar.politics.config

import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun api(): GroupedOpenApi = GroupedOpenApi.builder()
        .group("org.uqbar.politics")
        .packagesToScan("org.uqbar.politics")
        .build()
}
