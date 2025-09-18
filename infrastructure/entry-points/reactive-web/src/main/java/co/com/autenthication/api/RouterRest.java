package co.com.autenthication.api;

import co.com.autenthication.api.user.dto.ExistsUserResponse;
import co.com.autenthication.api.user.dto.UserRequestDTO;
import co.com.autenthication.api.user.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import org.springframework.web.bind.annotation.RequestMethod;


@Configuration
public class RouterRest {
    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/users",
                    method = RequestMethod.POST,
                    produces = {"application/json"},
                    beanClass = Handler.class,
                    beanMethod = "registerUser",
                    operation = @Operation(
                            summary = "Registrar usuario",
                            description = "Crea un nuevo usuario",
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    required = true,
                                    description = "Datos del usuario a registrar",
                                    content = @Content(schema = @Schema(implementation = UserRequestDTO.class))
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Usuario creado exitosamente",
                                            content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
                                    ),
                                    @ApiResponse(responseCode = "400", description = "Solicitud inválida")
                            }
                    )
            ),

            @RouterOperation(
                    path = "/api/v1/users/{id}",
                    method = RequestMethod.GET,
                    produces = {"application/json"},
                    beanClass = Handler.class,
                    beanMethod = "getUserById",
                    operation = @Operation(
                            summary = "Buscar usuario por ID",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Usuario encontrado",
                                            content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
                                    ),
                                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
                            }
                    )
            ),

            @RouterOperation(
                    path = "/api/v1/users",
                    method = RequestMethod.GET,
                    produces = {"application/json"},
                    beanClass = Handler.class,
                    beanMethod = "getAllUsers",
                    operation = @Operation(
                            summary = "Listar todos los usuarios",
                            responses = @ApiResponse(
                                    responseCode = "200",
                                    description = "Lista de usuarios",
                                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))
                            )
                    )
            ),

            @RouterOperation(
                    path = "/api/v1/users/{id}",
                    method = RequestMethod.DELETE,
                    produces = {"application/json"},
                    beanClass = Handler.class,
                    beanMethod = "deleteUserById",
                    operation = @Operation(
                            summary = "Eliminar usuario por ID",
                            responses = {
                                    @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
                                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
                            }
                    )
            ),

            @RouterOperation(
                    path = "/api/v1/users/document/{numDocumentUser}",
                    method = RequestMethod.GET,
                    produces = {"application/json"},
                    beanClass = Handler.class,
                    beanMethod = "getUserByDocument",
                    operation = @Operation(
                            summary = "Buscar usuario por número de documento",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Resultado de la búsqueda",
                                            content = @Content(schema = @Schema(implementation = ExistsUserResponse.class))
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return RouterFunctions

                // Endpoints para gestión de usuarios
                .route(POST("/api/v1/users"), handler::registerUser)
                .andRoute(GET("/api/v1/users"), handler::getAllUsers)
                .andRoute(GET("/api/v1/users/{id}"), handler::getUserById)
                .andRoute(DELETE("/api/v1/users/{id}"), handler::deleteUserById)
                .andRoute(GET("/api/v1/users/document/{numDocumentUser}"), handler::getUserByDocument);


    }
}
