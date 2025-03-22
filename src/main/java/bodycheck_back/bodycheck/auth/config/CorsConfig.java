package bodycheck_back.bodycheck.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

   @SuppressWarnings("null")
   @Override
   public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**")
            .allowedOriginPatterns(
                  "http://*:4200",
                  "http://localhost:[*]",
                  "https://bodycheck-fv8uivmlf-alvgm98s-projects.vercel.app",
                  "https://bodycheck.es")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
   }
}
