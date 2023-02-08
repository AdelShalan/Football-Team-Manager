package com.teamanager.gateway.config;

import com.teamanager.gateway.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    AuthenticationFilter filter;
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("PLAYER-SERVICE", r -> r.path("/api/v1/player/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://PLAYER-SERVICE"))
                .route("MANAGER-SERVICE", r -> r.path("/api/v1/manager/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://MANAGER-SERVICE"))
                .route("TEAM-SERVICE", r -> r.path("/api/v1/team/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://TEAM-SERVICE"))
                .route("CONTRACT-SERVICE", r -> r.path("/api/v1/contract/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://CONTRACT-SERVICE"))
                .route("AUTH-SERVICE", r -> r.path("/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://AUTH-SERVICE"))
                .build();
    }

}
