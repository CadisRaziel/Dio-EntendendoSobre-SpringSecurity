package dio.diospringsecurity.config;

import dio.diospringsecurity.config.SecurityDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //Classe criada para ter mais de um usuario e senha em memoria !!!!
    //para podermos definir autorização a esses usuarios e senha
    //diferente de colocar no application.properties


    //Criando os adapter e removendo do controller a anotação @PreAuthorize e fazendo ela aqui !

    @Autowired
    private SecurityDatabaseService securityService; //-> configuração de acesso a segurança que o spring security vai retornar
    //forma global de obter a credencial vai ser atraves do nosso consumo do 'securityService'
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

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
                //.formLogin() //-> tela de login que o spring gera para realizar a autenticação
                .httpBasic(); //-> client http que vai tenta autentica sem exigir tela de login (por recomendação vai tenta algumas tentativas de autenticação)
                //httpBasic-> o que quer dizer então, que teremos que usar o postman pra interagir !!
    }

    //AuthenticationManagerBuilder -> Cria uma cadeia de usuarios em memoria

    /* desativando essa geração de usuarios pois colcoaremos os usuarios em um banco local(memoria) agora
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

     */

    //Tipos de criptografia
    //{bcrypt} -> for BCryptPasswordEncoder (mais comun)
    //{noop} -> for NoOpPasswordEnconder
    //{pbkdf2} -> for Pbdkdf2PasswordEncoder
    //{scrypt} -> for SCryptPasswordEncoder
    //{sha256} -> StandardPasswordEncoder

}
