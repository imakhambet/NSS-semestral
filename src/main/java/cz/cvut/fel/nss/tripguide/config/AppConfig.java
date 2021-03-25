package cz.cvut.fel.nss.tripguide.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
public class AppConfig {

    /**
     * {@link RestTemplate} can be used to communicate with web services of another application - see for example <a
     * href="http://www.baeldung.com/rest-template">http://www.baeldung.com/rest-template</a>.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * {@link MultipartResolver} is used to support file upload.
     */
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    /**
     * Overrides the default {@link ObjectMapper} bean created by Spring Boot to allow further customization.
     * <p>
     * Basic configuration could be also done in {@code application.properties}, see <a
     * href="https://docs.spring.io/spring-boot/docs/current/reference/html/howto-spring-mvc.html#howto-customize-the-jackson-objectmapper">Spring
     * Boot reference</a>.
     * <p>
     * comment by dev:
     * Override the default instance created by Spring Boot
     * Support for Java 8 Date/Time API
     * Only non-null properties are serialized
     * This is necessary for our way of working with Java 8 Date/Time API. If it were not configured, the
     * datetime object in JSON would consist of several attributes, each for the respective date/time part, i.e. year, day etc.
     * Ignore unknown properties in JSON
     *
     * @return {@code ObjectMapper} bean
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

}

