package client_project.y2s1.team2.graphium.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TimeService {

    public String getTimeStamp(){
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = dateTime.format(now);

        return date;
    }
}
