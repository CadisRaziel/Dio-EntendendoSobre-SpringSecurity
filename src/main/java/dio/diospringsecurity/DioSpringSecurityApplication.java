package dio.diospringsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DioSpringSecurityApplication {

	//ATENCAO (recurso para demonstração/desenvolvimento apenas)
	//no application.properties eu coloquei um usuario e uma senha do meu gosto
	//o spring entende que se tive algo ali, ele não precisa ficar gerando o proprio dele
	//e com isso ele para de gerar a senha temporaria e passa a obedecer o application.properties

	public static void main(String[] args) {
		SpringApplication.run(DioSpringSecurityApplication.class, args);
	}

}
