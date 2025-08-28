package co.com.autenthication.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;


@Configuration
public class RouterRest {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return RouterFunctions.route(GET("/api/usecase/path"), handler::listenGETUseCase)
                .andRoute(POST("/api/usecase/otherpath"), handler::listenPOSTUseCase)
                .andRoute(GET("/api/otherusercase/path"), handler::listenGETOtherUseCase)

                // Endpoints para gestión de usuarios
                .andRoute(POST("/api/v1/users"), handler::registerUser)
                .andRoute(GET("/api/v1/users"), handler::getAllUsers)
                .andRoute(GET("/api/v1/users/{id}"), handler::getUserById)
                .andRoute(DELETE("/api/v1/users/{id}"), handler::deleteUserById);
    }
}
