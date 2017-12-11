import java.io.*;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.net.InetAddress;
import java.time.ZoneId;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

public class TimeServerClock implements IClock{
    private Properties properties;
    private int propertyIndex;
    private String [] propertyKeys;
    private boolean timeRetrieved;
    private InetAddress inetAddress;
    private TimeInfo timeInfo;
    private NTPUDPClient timeClient;
    private long timeBeforeServerQuery;
    private long returnTime;

    public TimeServerClock() {
        properties = new Properties();
    }

    @Override
    public LocalDateTime now() {
        retrieveTimeServers();
        createTimeOut();
        setInetAddress();
        retrieveCurrentServerTime(timeClient);
        return formatCurrentTime();
    }

    private LocalDateTime formatCurrentTime() {
        removeTimeLatency();
        Date time = new Date(returnTime);
        LocalDateTime timeNow = LocalDateTime.ofInstant(time.toInstant(), ZoneId.systemDefault());
        System.out.println(timeNow);
        return timeNow;
    }

    private void removeTimeLatency() {
        returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
        returnTime = returnTime + ((returnTime - timeBeforeServerQuery) * 2);
    }

    private void retrieveCurrentServerTime(NTPUDPClient timeClient) {
        System.out.println("connected to server");
        while (!timeRetrieved) {
            try {
                System.out.println("attempting to retrieve time");
                timeBeforeServerQuery = System.currentTimeMillis();
                timeInfo = timeClient.getTime(inetAddress);
                timeRetrieved = true;
                System.out.println("retrieved time from server");
            }
            catch (IOException e) {
                if (propertyIndex <= propertyKeys.length) {
                    propertyIndex++;
                    System.out.println("current server has timed out. We are now changing servers");
                    setInetAddress();
                }
                else {
                    System.out.println("Servers are down");
                    System.exit(1);
                }
            }
        }
    }

    private void createTimeOut() {
        timeClient = new NTPUDPClient();
        try {
            timeClient.open();
            timeClient.setSoTimeout(5000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        System.out.println("created new timeClient");
    }

    private void setInetAddress() {
        try {
            System.out.println("we are now accessing the server at index " + propertyIndex);
            inetAddress = InetAddress.getByName(propertyKeys[propertyIndex]);
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void retrieveTimeServers() {
        loadProperties();
        String [] props = new String[properties.size()];
        Enumeration e = properties.propertyNames();
        int i = 0;
        while(e.hasMoreElements()){
            String key = (String) e.nextElement();
            props[i] = properties.getProperty(key);
            i++;
        }
        propertyKeys = props;
    }

    private void loadProperties() {
        FileInputStream fileInput = null;
        File file = new File("C:\\Users\\Mark\\IdeaProjects\\MCO152\\hw6\\src\\TimeServer.properties");
        try {
            fileInput = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("reader exists");
        try {
            properties.load(fileInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args) {
        TimeServerClock clock = new TimeServerClock();
        clock.now();

    }
}
