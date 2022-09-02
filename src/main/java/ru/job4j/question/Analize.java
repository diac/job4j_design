package ru.job4j.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Analize {

    public static Info diff(Set<User> previous, Set<User> current) {
        int changed = 0;
        int deleted = 0;
        Map<Integer, String> currentMap = new HashMap<>(current.stream()
                .collect(Collectors.toMap(User::getId, User::getName))
        );
        Predicate<User> deletedCondition = user -> !current.contains(user) && !currentMap.containsKey(user.getId());
        Predicate<User> changedCondition = user -> !current.contains(user) && currentMap.containsKey(user.getId());
        for (var user : previous) {
            deleted += deletedCondition.test(user) ? 1 : 0;
            changed += changedCondition.test(user) ? 1 : 0;
        }
        int added = current.size() - previous.size() + deleted;
        return new Info(added, changed, deleted);
    }
}
