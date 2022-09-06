package ru.job4j.serialization.java;

import javax.xml.bind.annotation.*;
import java.util.Arrays;
import java.util.Objects;

@XmlRootElement(name = "account")
@XmlAccessorType(XmlAccessType.FIELD)
public class Account {

    @XmlAttribute
    private boolean isActive;
    @XmlAttribute
    private int rating;
    @XmlAttribute
    private String username;
    private Contact contact;
    @XmlElementWrapper(name = "privileges")
    @XmlElement(name = "privilege")
    private String[] privileges;

    public Account() {
    }

    public Account(boolean isActive, int rating, String username, Contact contact, String[] privileges) {
        this.isActive = isActive;
        this.rating = rating;
        this.username = username;
        this.contact = contact;
        this.privileges = privileges;
    }

    @Override
    public String toString() {
        return "Account{"
                + "isActive=" + isActive
                + ", rating=" + rating
                + ", username='" + username + '\''
                + ", contact=" + contact
                + ", privileges=" + Arrays.toString(privileges)
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return isActive == account.isActive && rating == account.rating && username.equals(account.username)
                && contact.equals(account.contact) && Arrays.equals(privileges, account.privileges);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(isActive, rating, username, contact);
        result = 31 * result + Arrays.hashCode(privileges);
        return result;
    }
}