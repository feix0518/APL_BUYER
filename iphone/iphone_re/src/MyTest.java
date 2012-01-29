import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;



public class MyTest {
	public static void main(String[] args) {   
        try {   
            System.setProperty("http.proxyHost", "proxy.sunjapan.com.cn");   
            System.setProperty("http.proxyPort", "8080");   
            URL url=new URL("http://www.baidu.com");   
            URLConnection uc = url.openConnection();   
            String encoded = new String(Base64.encode(new String("zhouhl:102813").getBytes()));   
            uc.setRequestProperty("Proxy-Authorization", "Basic " + encoded);   
            uc.connect();   
            BufferedReader rd = new BufferedReader(new InputStreamReader(uc.getInputStream()));   
            String line;   
            while ((line = rd.readLine()) != null) {   
                System.out.println(line);   
            }   
            rd.close();   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
	}
}
