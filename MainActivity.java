package com.example.kamin;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import androidx.appcompat.app.AlertDialog;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.CollationElementIterator;

public class MainActivity extends AppCompatActivity {


    public static String inBufr;
    public static byte btndn = 100;
    private boolean flag;
    private Object v;
    private boolean fla;
    public static  String direction;

    public static String ipPub;
    public static String ipPu;
    ImageButton image;
    TextView TextView;
    int[] drawable={R.drawable.frieoff,R.drawable.frielow,R.drawable.friemid, R.drawable.friehigh};//картинки на главной странице огонь
    int i=0;

/*
    public  class UDPip {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE); //получение локального IP

        private Context getApplicationContext() {
            return null;
        }


        private String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());


        //дублируем ip на всякий
        public String ipAdds[] = ipAddress.split("\\."); //режем IP на квартеты
        {
        MainActivity.ipPu = (ipAdds[0] + "." + ipAdds[1] + "." + ipAdds[2] + ".255");
        }
       // {
       //     ipPub = ("192.168.31.255");// меняем последний квартет на 255 для публичных udp пакетов
       // }


    }

*/


    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE); //получение локального IP



        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());


        //дублируем ip на всякий
        String[] ipAdds = ipAddress.split("\\."); //режем IP на квартеты
        {
            MainActivity.ipPu = (ipAdds[0] + "." + ipAdds[1] + "." + ipAdds[2] + ".255");
        }


        ipPub = ipPu;


        new Udp_receive();
        new Udp_client();
        inBufr=Udp_receive.inBuf;
        byte hate = 0;

        if (inBufr=="HATEON"){
           hate = 1;
       }


    ImageButton imageButton2 = (ImageButton) findViewById(R.id.imageButton2);// вкл/выкл обогрев


        {
            if (hate == 0) {
                imageButton2.setImageResource(R.drawable.obogrev1);
            }
            if (hate == 1) {
                imageButton2.setImageResource(R.drawable.obogrev2);
            }
        }

        byte finalHate1 = hate;
        imageButton2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {


                if (finalHate1 == 0) {
                    direction = "HATEON";
                    btndn = 1;
                    imageButton2.setImageResource(R.drawable.obogrev1);
                    btndn = 100;

                }

                if (finalHate1 == 1) {
                    direction = "HATEOFF";
                    btndn = 1;
                    imageButton2.setImageResource(R.drawable.obogrev2);
                    btndn = 100;
                }

            }



        });

        final ImageButton imageButton6 = (ImageButton) findViewById(R.id.imageButton6); // вкл/выкл вентилятор
        imageButton6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if (flag) {
                    imageButton6.setImageResource(R.drawable.ven);
                 //   direction = 6;
                }
                else
                    // возвращаем первую картинку
                    imageButton6.setImageResource(R.drawable.vent3);
                flag = !flag;
                direction = "ON";
            }
        });

        final ImageButton imageButton7 = (ImageButton) findViewById(R.id.imageButton7); // вкл/выкл розетки на камине
        imageButton7.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                if (flag)
                    imageButton7.setImageResource(R.drawable.roz1);

                else
                    // возвращаем первую картинку
                    imageButton7.setImageResource(R.drawable.plug2);
                flag = !flag;
            }
        });

        final ImageButton imageButton9 = (ImageButton) findViewById(R.id.imageButton9); // вкл/выкл обогрев звук
        imageButton9.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                if (flag)
                    imageButton9.setImageResource(R.drawable.z1);

                else
                    // возвращаем первую картинку
                    imageButton9.setImageResource(R.drawable.z2);
                flag = !flag;
            }
        });


        final ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton); // картинки на главной странице огонь меняются
        imageButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                i++;
                if (i==drawable.length)
                    i=0;
                imageButton.setImageResource(drawable[i]);
            }
        });





        final ImageButton imageButton11 = (ImageButton) findViewById(R.id.imageButton11); //выход из приложения
        imageButton11.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Выход из приложения");
                builder.setMessage("Вы действительно хотите выйти?");
                builder.setNegativeButton("НЕТ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        });
                builder.setPositiveButton("ДА",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                 finish();
                            }
                        });
                builder.show();
            }
        });

        final ImageButton imageButton10 = (ImageButton) findViewById(R.id.imageButton10); //переход на др стр настройки
        imageButton10.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }


        });

     final TextView TextView7 = (TextView) findViewById(R.id.textView7);

       TextView7.setText(Udp_receive.inBuf);
      //  textView7.setOnClickListener(new View.OnClickListener()
      //  {
      //      @Override
      //      public void onClick(View view)

      //      {

                //   TextView.setText(Integer.parseInt(byte.toString(direction)));
                //TextView = findViewById(R.id.textView7);
                //TextView.setText(Integer.toString(direction));
      //      }
      //  });


    }

   // public void exit(View view) {
  //  }
};





















