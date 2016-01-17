package introsde.finalproject.localdatabase;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;

import javax.xml.ws.Endpoint;

public class ServiceStandalone {
    public static void main(String[] args) throws IllegalArgumentException, IOException, URISyntaxException{
        String PROTOCOL = "http://";
        String HOSTNAME = InetAddress.getLocalHost().getHostAddress();
        if (HOSTNAME.equals("127.0.0.1")) {
            HOSTNAME = "localhost";
        }
        String PORT = "7003";
        String BASE_URL = "/soap/localdatabase";
        
        String endpointUrl = PROTOCOL+HOSTNAME+":"+PORT+BASE_URL;
        System.out.println("Starting Local Database Service...");
        System.out.println("--> Published. Check out "+endpointUrl+"?wsdl");
        Endpoint.publish(endpointUrl, new ServiceImpl());
    }
}
