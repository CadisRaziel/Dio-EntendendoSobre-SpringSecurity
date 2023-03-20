package dio.diospringsecurity.config;

import dio.diospringsecurity.model.User;
import dio.diospringsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SecurityDatabaseService implements UserDetailsService {

    //loadUserByUsername -> vai retornar uma classe ou interface de UserDetailsService do core do spring security
    //para criar esse metodo temos que interagir com o banco
    @Autowired
    private UserRepository userRepository; //injetando o userRepository
    @Override
    public UserDetails loadUserByUsername(String username)  {
        User userEntity = userRepository.findByUserName(username); //para retornar o usuario pelo username
        if(userEntity == null) { //verificamos se o usuario existe na base de dados
            throw new UsernameNotFoundException(username);
        }

        //aqui o spring se encarrega para ver as verificações de segurança que o usuario tem
        //suas permissoes
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        userEntity.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        });

        UserDetails user = new org.springframework.security.core.userdetails.User(userEntity.getUsername(),
                userEntity.getPassword(),
                authorities);

        return user;
    }
}
