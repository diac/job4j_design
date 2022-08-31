package ru.job4j.hashmap;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HashMapUsage {

    public static void main(String[] args) {
        Map<User, Object> map = new HashMap<>();
        Calendar birthday = Calendar.getInstance();
        User john = new User("John", 2, birthday);
        int johnHashCode = john.hashCode();
        int johnHash = johnHashCode ^ (johnHashCode >>> 16);
        int johnBucket = johnHash & 15;
        User john2 = new User("John", 2, birthday);
        int john2HashCode = john2.hashCode();
        int john2Hash = john2HashCode ^ (john2HashCode >>> 16);
        int john2Bucket = john2Hash & 15;
        map.put(john, new Object());
        map.put(john2, new Object());
        System.out.println(map);
        System.out.printf("John - хэшкод: %s, хэш: %s, бакет: %s\n",
                johnHashCode, johnHash, johnBucket);
        System.out.printf("John2 - хэшкод: %s, хэш: %s, бакет: %s\n",
                john2HashCode, john2Hash, john2Bucket);
        System.out.println("John equals John2? " + john.equals(john2));
    }
}
