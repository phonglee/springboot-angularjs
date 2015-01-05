package com.hms.contact.service;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import com.hms.contact.Application;
import com.hms.contact.domain.Contact;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by phongle on 12/31/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ContactServiceTest {

    @Autowired
    private ContactService contactService;

    @Before
    public void startUp() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("etc/contacts-test.txt").getFile());

        contactService.loadContact(file.toString());
    }

    @After
    public void tearDown() {
        contactService.deleteAllContacts();
    }

    @Test
    public void testSearchContacts() {
        List<Contact> contactList;
        contactList = contactService.searchContacts("",0, 10);
        assertThat(contactList.size(), is(equalTo(5)));

        contactList = contactService.searchContacts("00", 0, 10);
        assertThat(contactList.size(), is(equalTo(5)));

        contactList = contactService.searchContacts("name1", 0, 10);
        assertThat(contactList.size(), is(equalTo(1)));
        assertThat(contactList.get(0).getId(), is(equalTo("001")));
    }

    @Test
    public void testSaveContact() {
        Contact contact = new Contact("new name","new fullname");
        contactService.saveContact(contact);

        List<Contact> contactList = contactService.searchContacts("new name", 0, 10);
        assertThat(contactList.size(), is(equalTo(1)));
        assertThat(contactList.get(0).getName(), is(equalTo("new name")));

        contact = contactService.getContact("001");
        assertNotNull(contact);
        contact.setFullName("update fullname");
        contactService.saveContact(contact);
        contact = contactService.getContact("001");
        assertThat(contact.getFullName(), is(equalTo("update fullname")));
    }

    @Test
    public void testDeleteContact() {
        List<Contact> contactList = contactService.searchContacts("", 0 , 10);
        assertThat(contactList.size(), is(equalTo(5)));

        contactService.deleteContact("001", "002");
        contactList = contactService.searchContacts("", 0, 10);
        assertThat(contactList.size(), is(equalTo(3)));

        contactList.forEach(contact -> {
            assertThat(contact.getId(), is(not("001")));
            assertThat(contact.getId(), is(not("002")));
        });

    }
}
