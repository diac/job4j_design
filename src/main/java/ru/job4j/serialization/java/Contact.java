package ru.job4j.serialization.java;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.nio.file.Files;
import java.util.Objects;

@XmlRootElement(name = "contact")
@XmlAccessorType(XmlAccessType.FIELD)
public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    @XmlAttribute
    private int zipCode;
    @XmlAttribute
    private String phone;

    public Contact() {
    }

    public Contact(int zipCode, String phone) {
        this.zipCode = zipCode;
        this.phone = phone;
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Contact{"
                + "zipCode=" + zipCode
                + ", phone='" + phone + '\''
                + '}';
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final Contact contact = new Contact(123456, "+7 (111) 111-11-11");
        System.out.println(contact);
        File tempFile = Files.createTempFile(null, null).toFile();
        try (FileOutputStream fos = new FileOutputStream(tempFile);
             ObjectOutputStream oos =
                     new ObjectOutputStream(fos)) {
            oos.writeObject(contact);
        }
        try (FileInputStream fis = new FileInputStream(tempFile);
             ObjectInputStream ois =
                     new ObjectInputStream(fis)) {
            final Contact contactFromFile = (Contact) ois.readObject();
            System.out.println(contactFromFile);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        return zipCode == contact.zipCode && phone.equals(contact.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zipCode, phone);
    }
}
