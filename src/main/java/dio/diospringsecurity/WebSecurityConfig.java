package dio.diospringsecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //Classe criada para ter mais de um usuario e senha em memoria !!!!
    //para podermos definir autorização a esses usuarios e senha
    //diferente de colocar no application.properties


    //Criando os adapter e removendo do controller a anotação @PreAuthorize e fazendo ela aqui !
    @Override
    protected void configure(HttpSecurity http) throws  Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll() //->rota inicial, permitir todos
                .antMatchers("/login").permitAll() //->rota login permitir todos
                //.antMatchers(HttpMethod.POST, "/login").permitAll() //->posso dizer tambem que na minha rota login só é permitido metodos POST
                .antMatchers("/managers").hasAnyRole("MANAGERS") //-> rota admins, somente menegers
                .antMatchers("/users").hasAnyRole("USERS", "MANAGERS") //-> rota users, somente usuarios e admins
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();
    }

    //AuthenticationManagerBuilder -> Cria uma cadeia de usuarios em memoria
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                //USUARIO1
                .withUser("vitu")
                .password("{noop}vitu123") //{noop} -> Estrategia de criptografia
                .roles("USERS")
                .and()
                //USUARIO2
                .withUser("admin")
                .password("{noop}master123") //{noop} -> Estrategia de criptografia
                .roles("MANAGERS");
    }

    //Tipos de criptografia
    //{bcrypt} -> for BCryptPasswordEncoder (mais comun)
    //{noop} -> for NoOpPasswordEnconder
    //{pbkdf2} -> for Pbdkdf2PasswordEncoder
    //{scrypt} -> for SCryptPasswordEncoder
    //{sha256} -> StandardPasswordEncoder

}
