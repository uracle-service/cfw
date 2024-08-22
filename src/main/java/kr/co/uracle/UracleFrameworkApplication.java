package kr.co.uracle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class UracleFrameworkApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure (SpringApplicationBuilder builder) {
        return builder.sources(UracleFrameworkApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(UracleFrameworkApplication.class, args);
    }

}
