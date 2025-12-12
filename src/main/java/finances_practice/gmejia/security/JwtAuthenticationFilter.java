package finances_practice.gmejia.security;

import finances_practice.gmejia.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component //Genera una instancia en memoria (Singleton) y guarda en un contenedor (ApplicationContext)
@RequiredArgsConstructor //Genera un constructor con argumentos para todos los FINALS
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //OncePerRequestFilter garantiza que el metodo doFilterInteral se ejecute una sola vez por cada peticion HTTP

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override //Sobreescribe un metodo de la clase Padre
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain //Es el que permite visualizar si ya termino para pasar al otro filtro
            ) throws ServletException, IOException{

        // 1. Obtener el header de autorización
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 2. Validar Bearer
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); //No lo bloquea a pesar de no tener correctamente el encabezado, lo hace mas adelante
            return;
        }

        // 3. Extraer el token
        jwt = authHeader.substring(7); //Corta los 7 primeros caracteres que son el Bearer y el espacio que mantiene a la derecha

        // 4. Extraemos el email
        userEmail = jwtService.extractUsername(jwt);

        // 5. Si hay email y el usuario no está autenticado todavía
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Buscar al usuario en la BD
            var userEntity = userRepository.findByEmail(userEmail).orElse(null);

            if (userEntity != null && jwtService.isTokenValid(jwt, userEntity.getEmail())) {

                String roleName = getRoleName(userEntity.getRoleId());

                // Crear objeto UserDetails de Spring Security (Mapeo rápido)
                UserDetails userDetails = User.builder()
                        .username(userEntity.getEmail())
                        .password(userEntity.getPassword())
                        .roles(roleName)
                        .build();

                // Crear el objeto de autenticación
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                //Agrega metadatos como la IP de donde vino la Peticion, util para auditar
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. Autenticacion
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);

    }

    private String getRoleName(Integer roleId) {
        if (roleId == null) return "Usuario";

        return switch (roleId) {
            case 1 -> "Administrador";
            case 2 -> "Usuario";
            default -> "Usuario";
        };
    }
}
