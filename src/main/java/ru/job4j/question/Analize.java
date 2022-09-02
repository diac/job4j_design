package ru.job4j.question;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class Analize {

    public static Info diff(Set<User> previous, Set<User> current) {
        int added = 0;
        int changed = 0;
        int deleted = 0;
        Set<Integer> previousIds = new HashSet<>(
                previous.stream()
                        .map(User::getId)
                        .toList()
        );
        Set<Integer> currentIds = new HashSet<>(
                current.stream()
                        .map(User::getId)
                        .toList()
        );
        Set<User> union = new HashSet<>(previous);
        union.addAll(current);
        Predicate<User> addedCondition = user -> !previous.contains(user) && !previousIds.contains(user.getId());
        Predicate<User> deletedCondition = user -> !current.contains(user) && !currentIds.contains(user.getId());
        Predicate<User> changedCondition = user -> !current.contains(user) && currentIds.contains(user.getId());
        for (var user : union) {
            added += addedCondition.test(user) ? 1 : 0;
            deleted += deletedCondition.test(user) ? 1 : 0;
            changed += changedCondition.test(user) ? 1 : 0;
        }
        return new Info(added, changed, deleted);
    }
}
