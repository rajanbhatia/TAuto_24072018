
import java.io.IOException;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

public final class PublicServerTime {

    public static Date getNTPDate() {

        String[] hosts = new String[]{
            "ntp02.oal.ul.pt", "ntp04.oal.ul.pt",  // Get ServerTime Online
            "ntp.xs4all.nl"};

        NTPUDPClient client = new NTPUDPClient();
        // We want to timeout if a response takes longer than 5 seconds
        client.setDefaultTimeout(5000);

        for (String host : hosts) {

            try {
                InetAddress hostAddr = InetAddress.getByName(host);
                System.out.println("> " + hostAddr.getHostName() + "/" + hostAddr.getHostAddress());
                TimeInfo info = client.getTime(hostAddr);
                
                //Calendar cal = new Calendar(info.getReturnTime());
                Date date = new Date(info.getReturnTime());
                return date;

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        client.close();

        return null;

    }


    public static final void main(String[] args) {

        System.out.println(getNTPDate());

    }
}