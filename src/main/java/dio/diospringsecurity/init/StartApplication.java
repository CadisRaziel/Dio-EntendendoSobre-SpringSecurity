package dio.diospringsecurity.init;

import dio.diospringsecurity.model.User;
import dio.diospringsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class StartApplication implements CommandLineRunner {

    //Classe para definir um usuario principal(INICIAL) quando iniciar o spring vai ser com ele

    @Autowired
    private UserRepository repository;
    @Transactional
    @Override
    public void run(String... args) throws Exception {
        User user = repository.findByUserName("admin");
        if(user == null) {
            user = new User();
            user.setName("ADMIN");
            user.setUsername("admin");
            user.setPassword("master123");
            user.getRoles().add("MANAGERS");
            repository.save(user);
        }
        user = repository.findByUserName("user");
        if(user == null) {
            user = new User();
            user.setName("USER");
            user.setUsername("vitu");
            user.setPassword("vitu123");
            user.getRoles().add("USERS");
            repository.save(user);
        }
    }
}
