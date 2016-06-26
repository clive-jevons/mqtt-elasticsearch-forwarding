package net.jevonsit.ingest.clientevents.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "net.jevonsit.ingest.clientevents.repository")
@ComponentScan(basePackages = {"net.jevonsit.ingest.clientevents"})
@EnableAsync
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
            .web(false)
            .run(args);
    }

}
