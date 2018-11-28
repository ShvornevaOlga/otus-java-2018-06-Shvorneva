package ru.otus.l161;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("SpringBeans.xml");
        ClientMain clientMain =  context.getBean(ClientMain.class);
        clientMain.start();
    }
}
