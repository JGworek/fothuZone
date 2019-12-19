package zone.fothu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableEurekaClient
@EnableDiscoveryClient
@RestController
public class CyoaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CyoaServerApplication.class, args);
	}
	
	@Bean
	  public WebMvcConfigurer corsConfigurer() {
	    return new WebMvcConfigurer() {
	      @Override
	      public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**").allowedMethods("GET", "OPTIONS", "PUT", "POST", "PATCH",
	            "DELETE");
	      }
	    };
	  }
}