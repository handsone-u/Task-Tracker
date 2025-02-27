package io.handsone.tasktracker.security;

import io.handsone.tasktracker.exception.UnAuthorizedException;
import io.handsone.tasktracker.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

  private final JwtUtil jwtUtil;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    if (!(handler instanceof HandlerMethod handlerMethod)) {
      return true;
    }

    if (!handlerMethod.hasMethodAnnotation(AuthRequired.class) &&
        !handlerMethod.getBeanType().isAnnotationPresent(AuthRequired.class)) {
      return true;
    }

    String token = extractToken(request);
    if (token == null || !jwtUtil.validateToken(token)) {
      log.debug("Unauthorized access");
      throw new UnAuthorizedException("Unauthorized access");
    }

    return true;
  }

  private String extractToken(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }

    return null;
  }
}
