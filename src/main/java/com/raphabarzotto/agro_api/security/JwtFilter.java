package com.raphabarzotto.agro_api.security;

import com.raphabarzotto.agro_api.staff.service.PersonService;
import com.raphabarzotto.agro_api.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * The type Jwt filter.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {
  private final TokenService tokenService;
  private final PersonService personService;

  @Autowired
  public JwtFilter(TokenService tokenService, PersonService personService) {
    this.tokenService = tokenService;
    this.personService = personService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    // tenta extrair o token
    Optional<String> token = extractToken(request);

    // verifica se ele existe
    if (token.isPresent()) {
      // se existir, validamos o token
      String subject = tokenService.validateToken(token.get());

      // se o token for válido (não houve exceção), encontramos a pessoa associada
      UserDetails userDetails = personService.loadUserByUsername(subject);

      // informamos o Spring Security que a pessoa está autenticada
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // continuamos com a cadeia de filtros de qualquer forma
    filterChain.doFilter(request, response);
  }

  // extrai o token da request, usa na de cima
  private Optional<String> extractToken(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");

    if (authHeader == null) {
      return Optional.empty();
    }

    return Optional.of(
        authHeader.replace("Bearer ", "")
    );
  }
}
