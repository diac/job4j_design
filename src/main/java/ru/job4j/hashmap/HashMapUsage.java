package ru.job4j.hashmap;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class HashMapUsage {

    public static void main(String[] args) {
        Map<User, Object> map = new HashMap<>();
        User john = new User("John", 2, new GregorianCalendar(1980, Calendar.FEBRUARY, 20));
        int johnHashCode = john.hashCode();
        int johnHash = johnHashCode ^ (johnHashCode >>> 16);
        int johnBucket = johnHash & 15;
        User bob = new User("Bob", 3, new GregorianCalendar(1983, Calendar.APRIL, 2));
        int bobHashCode = bob.hashCode();
        int bobHash = bobHashCode ^ (bobHashCode >>> 16);
        int bobBucket = bobHash & 15;
        map.put(john, new Object());
        map.put(bob, new Object());
        System.out.println(map);
        System.out.printf("John - хешкод: %s, хэш: %s, бакет: %s\n",
                johnHashCode, johnHash, johnBucket);
        System.out.printf("Bob - хешкод: %s, хэш: %s, бакет: %s",
                bobHashCode, bobHash, bobBucket);
    }
}
