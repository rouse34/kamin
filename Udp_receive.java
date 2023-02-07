package com.example.kamin;
//*
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.net.*;
import java.io.*;
import java.util.*;
//import java.net.DatagramSocket;
import java.net.InetAddress;

public class Udp_receive extends Thread {

    public static String inBuf;
    byte[] rdata = {0};
    int udp_port = 45045;
    // InetAddress adder;
    DatagramSocket ds;

    public Udp_receive() {

        try {
            ds = new DatagramSocket(45045);
            //  adder = InetAddress.getByName(MainActivity.ipPub);
        } catch (Exception e) {}

        start();
    }

    public void run() {
        byte temp = MainActivity.btndn;
        MainActivity.inBufr = inBuf;
        //while (true) {
        //  byte temp = MainActivity.btndn;
        // if (temp == 100) {
        // }
        DatagramPacket receivingPacket = new DatagramPacket(rdata, rdata.length);
        try {

            ds.receive(receivingPacket);

            String recData = new String(receivingPacket.getData());

            inBuf = recData ;
            Thread.sleep(200);

        } catch (Exception e) {}
        //}
    }
}