package com.fotron.draw;

import com.fotrontimes.core.startup.EnableBaseCore;
import com.fotrontimes.utils.verification.annotation.EnableSimpleVerification;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableBaseCore
@EnableSimpleVerification
public class DrawApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DrawApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DrawApplication.class);
    }
}
