package com.hms.contact;

import com.hms.contact.service.ContactService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * Created by phongle on 12/31/2014.
 */
@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        ContactService contactService = applicationContext.getBean(ContactService.class);
        Resource resource = applicationContext.getResource("classpath:etc/contacts.txt");

        String filePath = resource.getFile().toString();

        contactService.loadContact(filePath);
        System.out.println("File path: "+ filePath);

        contactService.searchContacts("", 0, 10).forEach(System.out::println);

        SpringApplication.exit(applicationContext);
    }
}
