package ru.job4j.generic;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RoleStoreTest {

    @Test
    public void whenAddAndFindThenNameIsAdmin() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Admin"));
        Role result = store.findById("1");
        assertThat(result.getName()).isEqualTo("Admin");
    }

    @Test
    public void whenAddAndFindThenUserIsNull() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Admin"));
        Role result = store.findById("10");
        assertThat(result).isNull();
    }

    @Test
    public void whenAddDuplicateAndFindRoleNameIsAdmin() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Admin"));
        store.add(new Role("1", "Manager"));
        Role result = store.findById("1");
        assertThat(result.getName()).isEqualTo("Admin");
    }

    @Test
    public void whenReplaceThenRoleNameIsManager() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Admin"));
        store.replace("1", new Role("1", "Manager"));
        Role result = store.findById("1");
        assertThat(result.getName()).isEqualTo("Manager");
    }

    @Test
    public void whenNoReplaceRoleThenNoChangeRoleName() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Admin"));
        store.replace("10", new Role("10", "Manager"));
        Role result = store.findById("1");
        assertThat(result.getName()).isEqualTo("Admin");
    }

    @Test
    public void whenDeleteThenUserIsNull() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Admin"));
        store.delete("1");
        Role result = store.findById("1");
        assertThat(result).isNull();
    }

    @Test
    public void whenNoDeleteThenRoleNameIsAdmin() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Admin"));
        store.delete("10");
        Role result = store.findById("1");
        assertThat(result.getName()).isEqualTo("Admin");
    }

    @Test
    public void whenReplaceOkThenTrue() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Admin"));
        boolean result = store.replace("1", new Role("1", "Manager"));
        assertThat(result).isTrue();
    }

    @Test
    public void whenReplaceNotOkThenFalse() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Admin"));
        boolean result = store.replace("10", new Role("10", "Manager"));
        assertThat(result).isFalse();
    }

    @Test
    public void whenDeleteOkThenTrue() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Admin"));
        boolean result = store.delete("1");
        assertThat(result).isTrue();
    }

    @Test
    public void whenDeleteNotOkThenFalse() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Admin"));
        boolean result = store.delete("33");
        assertThat(result).isFalse();
    }
}