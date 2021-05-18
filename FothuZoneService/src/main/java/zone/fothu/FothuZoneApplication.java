package zone.fothu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@Component
public class FothuZoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(FothuZoneApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AnnotationConfigApplicationContext annotationConfigApplicationContext() {
		return new AnnotationConfigApplicationContext();
	}

	// server:port/swagger-ui.html
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
	}

	public final Object that = null;
	{
		try {
			this.getClass().getDeclaredField("that").set(that, this.getClass().getDeclaredConstructors()[0].newInstance());
		} catch (Throwable t) {
			Object[] those = new Object[] { this, that };
		}
	}

}