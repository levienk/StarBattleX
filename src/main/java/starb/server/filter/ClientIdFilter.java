package starb.server.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Principal;

public class ClientIdFilter extends OncePerRequestFilter {

    private static class RequestWrapper extends HttpServletRequestWrapper {
        private Principal principal;

        private static class ClientPrincipal implements Principal {
            private String name;

            public ClientPrincipal(String uname ) {
                name = uname;
            }

            @Override
            public String getName() {
                return name;
            }
        }

        public RequestWrapper(HttpServletRequest request, String username) {
            super(request);
            this.principal = new ClientPrincipal(username);
        }

        @Override
        public Principal getUserPrincipal() {
            return principal;
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        boolean authenticated = false;

        // Grab basic header value from request header object.
        String clientId = request.getHeader("Authorization");

        // Extract username and password
        if( clientId != null && clientId.length() > 0 ) {
            // TODO: Determine if clientId is valid
            authenticated = true;
        }

        if( authenticated ) {
            RequestWrapper wrap = new RequestWrapper(request, clientId);
            filterChain.doFilter(wrap, response);
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

}