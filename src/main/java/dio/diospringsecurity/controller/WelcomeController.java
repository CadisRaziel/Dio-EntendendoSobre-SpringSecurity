package dio.diospringsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @GetMapping
    public String welcome(){
        return "Welcome to my spring boot web api";
    }

    //Atenção, eu comentei os @PreAuthorize pois quando utilizamos a 'configure adapter', nos setamos eles
    //na classe de webSecutiryConfig

    //'MANAGERS', 'USERS' -> o nome é caseSentitive e tem que ser exatamente igual ao colocado na classe WebSecurityConfig
    //.roles("USERS") - .roles("MANAGERS");

    @GetMapping("/users")
    //@PreAuthorize("hasAnyRole('MANAGERS', 'USERS')") // hasAnyRole -> mais de um
    //estamos setando quem vamos permitir entre nessa rota
    //ou seja quem vai te autorização para acessar essa rota, então os 'role' que vão ser aceitos são os
    //managers(admin) e users(usuarios)
    public String users(){
        return "Authorized user (usuarios)";
    }

    @GetMapping("/managers")
    //@PreAuthorize("hasRole('MANAGERS')") // hasRole -> somente um (aqui só os admins podem acessar essa rota)
    public String managers(){
        return "Authorized manager (admins)";
    }
}
