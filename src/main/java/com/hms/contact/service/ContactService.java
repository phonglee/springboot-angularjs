package com.hms.contact.service;

import com.hms.contact.domain.Contact;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by phongle on 12/31/2014.
 */
@Service
public class ContactService {
    private final AtomicInteger idGeneration = new AtomicInteger(1000);
    private final Map<String, Contact> storage = new HashMap<String, Contact>();

    public long loadContact(String filePath) throws IOException {
        long totalRecords = 0;
        try (InputStream inputStream = new FileInputStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            totalRecords = bufferedReader
                    .lines()
                    .map(Contact::parseContact)
                    .peek(this::saveContact)
                    .count();

        }

        return totalRecords;
    }

    public List<Contact> searchContacts(String keyword, int page, int pageSize) {
        String keywordlc = null != keyword ? keyword.toLowerCase() : "";
        return storage.values()
                .stream()
                .filter(contact -> {
                    return false || contact.getId().contains(keywordlc)
                            || contact.getName().contains(keywordlc)
                            || contact.getEmail().contains(keywordlc)
                            || contact.getFullName().contains(keywordlc)
                            || contact.getJobTitle().contains(keywordlc)
                            || contact.getEmail().contains(keywordlc)
                            || contact.getMobile().contains(keywordlc)
                            || contact.getSkypeId().contains(keywordlc);
                })
                .skip(page * pageSize)
                .limit(pageSize)
                .sorted(Comparator.comparing(Contact::getName))
                .collect(Collectors.toList());
    }

    public Contact saveContact(Contact contact) {
        if (null == contact.getId()) {
            contact.setId(String.valueOf(idGeneration.incrementAndGet()));
        }
        return storage.put(contact.getId(), contact);
    }
    public Contact getContact(String id ) {
        return storage.get(id);
    }

    public void deleteContact(String...ids) {
        for (String id : ids) {
            storage.remove(id);
        }
    }

    public void deleteAllContacts() {
        storage.clear();
    }
}
