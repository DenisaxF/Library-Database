import java.time.ZoneId;
import java.util.*;
import java.io.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {

    public String fileName = "audit.csv";
    public void fileWriter(String actionName) {
        try(PrintWriter pw = new PrintWriter(fileName)){
            String timestamp = ZonedDateTime.now(ZoneId.of("Europe/Bucharest")).format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            pw.write(actionName + " " + timestamp + "\n");
            System.out.println("S-a scris in fisier\n");
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
