import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.net.InetAddress;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

public class TimeServerClock implements IClock{
    @Override
    public LocalDateTime now() {
        String TIME_SERVER = "time-b-g.nist.gov";
        NTPUDPClient timeClient = new NTPUDPClient();
        try {
            timeClient.open();
            timeClient.setSoTimeout(5000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        System.out.println("created new timeClient");
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(TIME_SERVER);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println("connected to server");
        TimeInfo timeInfo = null;
        try {
            System.out.println("attempting to retreive time");
            timeInfo = timeClient.getTime(inetAddress);
            //DateTimeUtils.currentTimeMillis() - timeInfo.getReturnTime();
            System.out.println("retrieved time from server");
        } catch (IOException e) {
            e.printStackTrace();
        }
        long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
        Date time = new Date(returnTime);
        LocalDateTime timeNow = LocalDateTime.ofInstant(time.toInstant(), ZoneId.systemDefault());
        System.out.println(timeNow);
        return timeNow;
    }

    public Properties retrieveTimeServers() {
        FileReader reader= null;
        try {
            reader = new FileReader("TimeServer.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties p = new Properties();
        try {
            p.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }

    public static void main(String [] args) {
        TimeServerClock clock = new TimeServerClock();
        clock.now();

    }
}
