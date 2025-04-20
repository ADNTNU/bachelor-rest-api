package no.ntnu.gr10.bachelor_rest_api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.ntnu.gr10.bachelor_rest_api.dto.ErrorResponse;
import no.ntnu.gr10.bachelor_rest_api.dto.JwtPayload;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFIlter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public JwtAuthenticationFIlter(JwtUtil jwtUtil){
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(
          HttpServletRequest httpServletRequest,
          HttpServletResponse httpServletResponse,
          FilterChain filterChain
  )throws IOException, ServletException {
    try {
      String jwtToken = getJwtFromRequest(httpServletRequest);

      if (jwtToken != null) {
        JwtPayload clientId = jwtUtil.verifyTokenAndGetPayload(jwtToken);

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        clientId.companyId(),
                        null,
                        clientId.authorities()
                );

        SecurityContextHolder.getContext().setAuthentication(auth);

        //UserDetails userDetails = customUserDetailsService.loadUserByUsername(clientId);

        //if(!userDetails.isEnabled()){
        //  throw new UserIsDisabled("Account has been disabled");
        //}

        //registerUserAsAuthenticated(httpServletRequest, userDetails);
      }
      filterChain.doFilter(httpServletRequest, httpServletResponse);

    } catch (JwtException | IllegalArgumentException ex) {
      writeJsonError(httpServletResponse, HttpStatus.UNAUTHORIZED, "Invalid JWT token");
    } catch (UsernameNotFoundException ex) {
      writeJsonError(httpServletResponse, HttpStatus.NOT_FOUND, "User not found");
    }//catch (UserIsDisabled ex) {
      //writeJsonError(httpServletResponse, HttpStatus.UNAUTHORIZED, "User has been deactivated");
   // }
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    final String BEARER_PREFIX = "Bearer ";
    String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(BEARER_PREFIX.length()); // Remove "Bearer " prefix
    }
    return null;
  }

  private void writeJsonError(HttpServletResponse response, HttpStatus status, String message) {
    try {
      response.setContentType("application/json");
      response.setStatus(status.value());

      ErrorResponse errorResponse = new ErrorResponse(message);
      String json = objectMapper.writeValueAsString(errorResponse);

      response.getWriter().write(json);
    } catch (Exception e) {
      //log.error("Error writing JSON error response: {}. Original error: {}", e.getMessage(), message);
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      try {
        response.getWriter().write("Internal server error");
      } catch (IOException ioException) {
        //log.error("Error writing internal server error response: {}", ioException.getMessage());
      }
    }
  }

}
