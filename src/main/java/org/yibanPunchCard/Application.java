package org.yibanPunchCard;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yibanPunchCard.service.PunchService;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        PunchService.punch();
    }
}
