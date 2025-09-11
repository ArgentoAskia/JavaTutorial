package services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ServerTimeService {

    public String serverTimes(){
        final LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTimeFormatter.format(now);
    }
}
