package com.salaboy.conferences.tests;

import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner, ApplicationContextAware {


    private static final String CONFERENCE_C4P = "http://fmtok8s-c4p";

    private static final String CONFERENCE_EMAIL = "http://fmtok8s-email";

    private static final String CONFERENCE_AGENDA = "http://fmtok8s-agenda";

    private ApplicationContext applicationContext;

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        String agendaInfo = "N/A";
        String c4pInfo = "N/A";
        String emailInfo = "N/A";

        try {
            ResponseEntity<String> c4p = restTemplate.getForEntity(CONFERENCE_C4P + "/info", String.class);
            c4pInfo = c4p.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            SpringApplication.exit(applicationContext, () -> 1);
        }
        if (c4pInfo == null || c4pInfo.equals("")) {
            System.err.println("C4P Service is not Ready");
            SpringApplication.exit(applicationContext, () -> 1);
        } else {
            System.out.println(">> C4P Service Info: " + c4pInfo);
        }


        try {
            ResponseEntity<String> agenda = restTemplate.getForEntity(CONFERENCE_AGENDA + "/info", String.class);
            agendaInfo = agenda.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            SpringApplication.exit(applicationContext, () -> 2);
        }
        if (agendaInfo == null || agendaInfo.equals("")) {
            System.err.println("Agenda Service is not Ready");
            SpringApplication.exit(applicationContext, () -> 2);
        } else {
            System.out.println(">> Agenda Service Info: " + agendaInfo);
        }


        try {
            ResponseEntity<String> email = restTemplate.getForEntity(CONFERENCE_EMAIL + "/info", String.class);
            emailInfo = email.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            SpringApplication.exit(applicationContext, () -> 3);
        }
        if (emailInfo == null || emailInfo.equals("")) {
            System.err.println("Email Service is not Ready");
            SpringApplication.exit(applicationContext, () -> 3);
        } else {
            System.out.println(">> Email Service Info: " + emailInfo);
        }

        //@TODO: actually create objects and send data to validate that services are interacting.

        SpringApplication.exit(applicationContext, () -> 0);
    }

    @Bean
    DemoListener demoListenerBean() {
        return new DemoListener();
    }

    private static class DemoListener {
        @EventListener
        public void exitEvent(ExitCodeEvent event) {
            System.out.println("Exit code: " + event.getExitCode());
            System.exit(event.getExitCode());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}


