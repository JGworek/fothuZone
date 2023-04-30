package zone.fothu.utility;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;

@Configuration
@ComponentScan("zone.fothu")
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer, WebApplicationInitializer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws").setHandshakeHandler(new DefaultHandshakeHandler(new TomcatRequestUpgradeStrategy())).setAllowedOrigins("*");
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/fothuZoneSendPoint");
		registry.enableSimpleBroker("/userSubscription", "/battleSubscription", "/challengeSubscription", "/currentBattlesSubscription", "/errorMessageSubscription", "/liveChatSubscription");
	}

	@Override
	public void onStartup(jakarta.servlet.ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext container = new AnnotationConfigWebApplicationContext();
		container.register(WebSocketConfig.class);

		servletContext.addListener(new ContextLoaderListener(container));
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(container));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}

}
