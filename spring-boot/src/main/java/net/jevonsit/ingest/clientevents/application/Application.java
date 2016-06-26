package net.jevonsit.ingest.clientevents.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.bmw.carsharing.ingest.clientevents.repository")
@ComponentScan(basePackages = {"com.bmw.carsharing.ingest.clientevents"})
@EnableAsync
//@ComponentScan(basePackages = {"com.bmw.carsharing.ingest.clientevents", "com.bmw.carsharing.ingest.clientevents.service", "com.bmw.carsharing.ingest.clientevents.repository"})
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
            .web(false)
            .run(args);
    }

}
