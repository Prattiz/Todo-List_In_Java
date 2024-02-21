package br.com.thiago.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.thiago.todolist.users.repository.InUserRepos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private InUserRepos inUserRepos;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();
        if(servletPath.startsWith("/tasks/")){
            var authorization = request.getHeader("Authorization");

            // encode authorization and decode it: 

            var authEncoded = authorization.substring("Basic".length()).trim();

            byte[] authDecode = Base64.getDecoder().decode(authEncoded);

            // create array with username and password: 

            var authString = new String(authDecode);
            String[] credentials = authString.split(":");

            String username = credentials[0];
            String password = credentials[1];

            var user = this.inUserRepos.findByUsername(username);

            if(user == null){
                response.sendError(401, "Unauthorized user");
            } else{
                var verifiedPassword = BCrypt.verifyer()
                    .verify(password.toCharArray(), user.getPassword());

                if(verifiedPassword.verified){
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                }else{
                    response.sendError(401, "Unauthorized password user");
                }
            }  

        } else{
            filterChain.doFilter(request, response);
        }
    } 
}
