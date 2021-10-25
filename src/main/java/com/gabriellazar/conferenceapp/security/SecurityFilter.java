package com.gabriellazar.conferenceapp.security;

import com.gabriellazar.conferenceapp.services.AttendeeService;
import com.gabriellazar.conferenceapp.services.impl.SecurityService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private JWTUtil jwtTokenUtil;
    private SecurityService securityService;

    public SecurityFilter(JWTUtil jwtTokenUtil, SecurityService securityService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.securityService = securityService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader("token");
        String username = null;
        String authToken = null;
        if (header != null ) {
            authToken = header;
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                log.error("An error occurred while fetching Username from Token :: {}", e);
            } catch (ExpiredJwtException e) {
                log.warn("The token has expired :: {}", e);
            }
        } else {
            log.warn("Couldn't find bearer string. Header will be ignored.");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = securityService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                log.info("Authenticated user :: {}",username);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(req, res);
    }
}
