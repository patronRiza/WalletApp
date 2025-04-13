package ru.prj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

//@SpringBootApplication
//@PropertySource(value = {
//        "file:./app/config/db.env",
//        "file:./app/config/app.env",
//        "file:./app/config/application.properties"
//}, ignoreResourceNotFound = true)
//public class WalletApp {
//    public static void main(String[] args) {
//        SpringApplication.run(WalletApp.class, args);
//    }
//}
@SpringBootApplication
public class WalletApp {
    public static void main(String[] args) {
        SpringApplication.run(WalletApp.class, args);
    }
}
