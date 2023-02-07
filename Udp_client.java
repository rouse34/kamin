
package com.example.kamin;

import static android.content.Context.WIFI_SERVICE;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Udp_client extends Thread
{
 public static String inBuf;

    byte [] data = {0};
    int udp_port=45045;
    InetAddress adder;
    DatagramSocket ds;
    DatagramSocket dsr;
/*
    public  class UDPip {
         WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE); //получение локального IP

        private  Context getApplicationContext() {
            return null;
        }


        private String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());


       //дублируем ip на всякий
       private String ipAdds[] = ipAddress.split("\\."); //режем IP на квартеты


        {
            Udp_client.ipPub = (ipAdds[0] + "." + ipAdds[1] + "." + ipAdds[2] + ".255");// меняем последний квартет на 255 для публичных udp пакетов
        }


    }
*/

    public Udp_client()

    {


        try
        {
            DatagramSocket dsr = new DatagramSocket();
            ds = new DatagramSocket();
            adder = InetAddress.getByName(MainActivity.ipPub);

        }
        catch (Exception e)
        {



        }

        start();
    }

    public void run()  {


       while (true)
       {


            byte temp = MainActivity.btndn;


            String s = "" + MainActivity.direction;
            data = s.getBytes();


            if(temp!=100 ) {



                try {
                    DatagramPacket pack = new DatagramPacket(data, data.length, adder, udp_port);
                    ds.send(pack);




                    Thread.sleep(200);

                } catch (Exception e) {


                }

            }


          /* if(temp==100 ) {
               inBuf = "recData";
               byte [] rdata = new byte[16];
              try {
                   DatagramPacket pack = new DatagramPacket(rdata, rdata.length);
                   dsr.receive(pack);

                   String recData = new String(pack.getData());
                   inBuf = recData;

                   Thread.sleep(200);

               } catch (Exception e) {


               }


           }*/

        }
    }


}
