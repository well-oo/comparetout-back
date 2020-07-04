package com.pepit.compareTout.security;

import com.pepit.compareTout.exception.CustomException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The JwtTokenFilter filter is applied to each API (/**) with exception of the signin token endpoint (/users/signin) and singup endpoint (/users/signup).
 *
 * This filter has the following responsibilities:
 *
 * Check for access token in Authorization header. If Access token is found in the header, delegate authentication to JwtTokenProvider otherwise throw authentication exception
 * Invokes success or failure strategies based on the outcome of authentication process performed by JwtTokenProvider
 *
 * Please ensure that chain.doFilter(request, response) is invoked upon successful authentication. You want processing of the request to advance to the next filter,
 * because very last one filter FilterSecurityInterceptor#doFilter is responsible to actually invoke method in your controller that is handling requested API resource.
 */

// We should use OncePerRequestFilter since we are doing a database call, there is no point in doing this more than once
public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (CustomException ex) {
            //this is very important, since it guarantees the user is not authenticated at all
            SecurityContextHolder.clearContext();
            httpServletResponse.sendError(ex.getHttpStatus().value(), ex.getMessage());
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
