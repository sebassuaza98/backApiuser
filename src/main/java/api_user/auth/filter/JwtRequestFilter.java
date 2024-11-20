package api_user.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import api_user.auth.service.JwtUtilService;
import api_user.util.Constants;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    final String authorizationHeader = request.getHeader("Authorization");

    try {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            String userid = jwtUtilService.extractUsername(jwt);

            if (userid != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userid);

                if (jwtUtilService.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    } catch (io.jsonwebtoken.ExpiredJwtException ex) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(Constants.TK_EXP);
    } catch (Exception ex) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST); 
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Error de autenticación\", \"details\": \"" + ex.getMessage() + "\"}");
    }
}

}
