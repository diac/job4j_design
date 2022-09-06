package ru.job4j.serialization.json;

import org.json.JSONObject;
import ru.job4j.serialization.java.Account;
import ru.job4j.serialization.java.Contact;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        final Contact contact = new Contact(1234, "+1234567", null);
        /* JSONObject напрямую методом put */
        final Account account = new Account(
                true,
                95,
                "johnsmith",
                contact,
                new String[]{"admin", "manager"}
        );
        contact.setAccounts(List.of(account));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("isActive", account.isActive());
        jsonObject.put("rating", account.getRating());
        jsonObject.put("username", account.getUsername());
        jsonObject.put("contact", new JSONObject(account.getContact()));
        jsonObject.put("privileges", account.getPrivileges());

        /* Выведем результат в консоль */
        System.out.println(jsonObject);

        /* Преобразуем объект person в json-строку */
        System.out.println(new JSONObject(account));
    }
}
