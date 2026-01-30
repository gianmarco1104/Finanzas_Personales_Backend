package finances_practice.gmejia.security;


import finances_practice.gmejia.dto.ErrorDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

//AuthenticationEntryPoint -> Maneja el error cuando el usuario no esta autenticado
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper(); //Herramienta de libreria JSON

    //Commence es el metodo de Spring que detecta cuando alguien entra sin token
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
        throws IOException, ServletException{
        //Codigo de estado
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        //Tipo de contenido de JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        //ErrorDto
        ErrorDto errorDto = ErrorDto.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("Unathorized")
                .message("Acceso denegado. Debes iniciar sesion o tu token es invalido")
                .build();

        response.getWriter().write(objectMapper.writeValueAsString(errorDto));
            }
}
