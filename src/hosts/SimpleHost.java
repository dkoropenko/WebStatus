package hosts;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

/**
 * Created by Koropenkods on 21.03.16.
 */
public class SimpleHost implements Runnable{
    private String ip;
    private int port;
    private String type;
    private String comment;
    private InetAddress adress;
    private String hostName;
    private boolean status;


    public SimpleHost(String ip, int port, String type, String comment) throws UnknownHostException {
        this.ip = ip;
        this.port = port;
        this.type = type;
        this.comment = comment;
        adress = InetAddress.getByName(ip);
    }

    public String getIp() {
        return ip;
    }
    public int getPort() {
        return port;
    }
    public String getType() {
        return type;
    }
    public String getHostName() throws UnknownHostException, InterruptedException {
        return adress.getHostName();
    }
    public String getComment() {
        return comment;
    }

    public boolean getStatus() throws IOException {
        if (adress.isReachable(3000))
            return true;
        return false;
    }

    public String toString(){
        String result = getIp() +" - "+ getPort() +" - "+ getType() +" - "+ getComment() +" - "+ status +" - "+ hostName;

        return result;
    }

    @Override
    public void run() {
        try {
            hostName = getHostName();
            status = getStatus();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
