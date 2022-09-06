package ru.job4j.serialization.xml;

import ru.job4j.serialization.java.Account;
import ru.job4j.serialization.java.Contact;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class Main {

    public static void main(String[] args) throws Exception {
        final Account account = new Account(
                true,
                95,
                "johnsmith",
                new Contact(1234, "+1234567", null),
                new String[]{"admin", "manager"}
        );
        /* Получаем контекст для доступа к АПИ */
        JAXBContext context = JAXBContext.newInstance(Account.class);
        /* Создаем сериализатор */
        Marshaller marshaller = context.createMarshaller();
        /* Указываем, что нам нужно форматирование */
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        String xml = "";
        try (StringWriter writer = new StringWriter()) {
            /* Сериализуем */
            marshaller.marshal(account, writer);
            xml = writer.getBuffer().toString();
            System.out.println(xml);
        }
        /* Для десериализации нам нужно создать десериализатор */
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try (StringReader reader = new StringReader(xml)) {
            /* десериализуем */
            Account result = (Account) unmarshaller.unmarshal(reader);
            System.out.println(result);
        }

    }
}
