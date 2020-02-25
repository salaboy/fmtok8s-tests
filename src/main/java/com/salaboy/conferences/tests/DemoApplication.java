package com.salaboy.conferences.tests;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

@Controller
class TestController {
    @Value("${version:0}")
    private String version;

    @GetMapping("/info")
    public String infoWithVersion() {
        return "Tests v" + version;
    }

    private static final String CONFERENCE_C4P = "http://fmtok8s-c4p";

    private static final String CONFERENCE_EMAIL = "http://fmtok8s-email";

    private static final String CONFERENCE_AGENDA = "http://fmtok8s-agenda";

    @GetMapping("/")
    public void test() {
        RestTemplate restTemplate = new RestTemplate();

        String agendaInfo = "N/A";
        String c4pInfo = "N/A";
        String emailInfo = "N/A";


        ResponseEntity<String> agenda = restTemplate.getForEntity(CONFERENCE_AGENDA + "/info", String.class);
        agendaInfo = agenda.getBody();
        if(agendaInfo == null || agendaInfo.equals("")){
            throw new IllegalStateException("Agenda Service not ready");
        }


        ResponseEntity<String> c4p = restTemplate.getForEntity(CONFERENCE_C4P + "/info", String.class);
        c4pInfo = c4p.getBody();
        if(c4pInfo == null || c4pInfo.equals("")){
            throw new IllegalStateException("C4P Service not ready");
        }

        ResponseEntity<String> email = restTemplate.getForEntity(CONFERENCE_EMAIL + "/info", String.class);
        emailInfo = email.getBody();
        if(emailInfo == null || emailInfo.equals("")){
            throw new IllegalStateException("Email Service not ready");
        }

        //@TODO: actually create objects and send data to validate that services are interacting.



    }


}
