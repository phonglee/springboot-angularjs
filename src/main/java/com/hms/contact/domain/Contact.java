package com.hms.contact.domain;

/**
 * Created by phongle on 12/31/2014.
 */
public class Contact {
    private String id;
    private String name;
    private String fullName;
    private String jobTitle;
    private String email;
    private String mobile;
    private String skypeId;

    public Contact() {}

    public Contact(String name, String fullName) {
        this.name = name;
        this.fullName = name;
    }

    public static Contact parseContact(String contactLine) {
        String[] items = contactLine.split("\\|");
        if (items.length < 2) {
            throw new IllegalArgumentException("Invalid contact-line format: " + contactLine);
        }
        Contact contact = new Contact();
        contact.setId(items[0]);
        contact.setName(items[1]);
        if (items.length > 2) contact.setFullName(items[2]);
        if (items.length > 3) contact.setJobTitle(items[3]);
        if (items.length > 4) contact.setEmail(items[4]);
        if (items.length > 5) contact.setMobile(items[5]);
        if (items.length > 6) contact.setSkypeId(items[6]);
        return contact;
    }

    @Override
    public boolean equals(Object obj) {
        if (id != null && obj instanceof Contact) {
            return id.equals(((Contact) obj).name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%s|%s|%s", id, name, fullName, jobTitle, email, mobile, skypeId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSkypeId() {
        return skypeId;
    }

    public void setSkypeId(String skypeId) {
        this.skypeId = skypeId;
    }
}
