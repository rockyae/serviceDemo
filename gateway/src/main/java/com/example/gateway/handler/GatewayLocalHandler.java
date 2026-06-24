package com.example.gateway.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class GatewayLocalHandler {

    @Bean
    public RouterFunction<ServerResponse> gatewayLocalRoutes() {
        return route(GET("/"), request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(home()))
                .andRoute(GET("/gateway"), request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(home()))
                .andRoute(GET("/gateway/routes"), request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(routesHelp()))
                .andRoute(GET("/echo/{msg}"), request -> ServerResponse.ok()
                        .bodyValue("Hello Nacos Gateway: " + request.pathVariable("msg")));
    }

    private Map<String, Object> home() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("service", "gate-way");
        body.put("port", 9000);
        body.put("description", "Spring Cloud Gateway with Nacos service discovery");
        body.put("examples", List.of(
                "GET /echo/{msg}                          -> gateway local echo",
                "GET /core/{path}                         -> lb://core (discovery locator)",
                "GET /api/core/{path}                     -> lb://core (strip /api/core)",
                "GET /actuator/gateway/routes             -> view configured routes",
                "GET /actuator/health                     -> health check"
        ));
        return body;
    }

    private Map<String, Object> routesHelp() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("core", Map.of(
                "discovery", "http://localhost:9000/core/**",
                "explicit", "http://localhost:9000/api/core/**"
        ));
        body.put("note", "Ensure target services are registered in Nacos (e.g. core on port 8082)");
        return body;
    }
}
