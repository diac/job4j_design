package ru.job4j.serialization.java;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUsage {

    public static void main(String[] args) {
        final Account account = new Account(
                true,
                95,
                "johnsmith",
                new Contact(1234, "+1234567"),
                new String[]{"admin", "manager"}
        );
        final Gson gson = new GsonBuilder().create();
        System.out.println(gson.toJson(account));
        final String accountDuplicateJson =
                "{"
                    + "\"isActive\":true,"
                    + "\"rating\":95,"
                    + "\"username\":\"johnsmith\","
                    + "\"contact\":{\"zipCode\":1234,\"phone\":\"+1234567\"},"
                    + "\"privileges\":[\"admin\",\"manager\"]"
                + "}";
        final Account accountDuplicate = gson.fromJson(accountDuplicateJson, Account.class);
        System.out.println(accountDuplicate);
        System.out.println(account.equals(accountDuplicate));
        final String accountModeJson =
                "{"
                        + "\"isActive\":false,"
                        + "\"rating\":60,"
                        + "\"username\":\"johnsmith\","
                        + "\"contact\":{\"zipCode\":1234,\"phone\":\"+1234567\"},"
                        + "\"privileges\":[\"subscriber\"]"
                        + "}";
        final Account accountMod = gson.fromJson(accountModeJson, Account.class);
        System.out.println(accountMod);
        System.out.println(account.equals(accountMod));
    }
}
