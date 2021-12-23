package com.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import com.tcp.SerialListener;

public class SemiSendHttp01 {
	SerialListener serial;
   
   class SendThread extends Thread{

      String btn;
      
      
      String urlstr = "http://192.168.0.158/semi/view/ledview/ledtest.mc";
      URL url = null;
      HttpURLConnection con = null;

      BufferedReader br = null;
      
      public SendThread() {
         
      }
      
      public SendThread(String btn) {
         this.btn = serial.send_btn(btn);
         
      }
      
      @Override
      public void run() {
         try {
            if(url.equals(new URL(urlstr + "?btn="+btn))) {
            	con = (HttpURLConnection) url.openConnection();
                con.setReadTimeout(5000);
                con.setRequestMethod("GET");
                con.getInputStream();
            }
//            url = new URL(urlstr + "?btn="+btn);
//            con = (HttpURLConnection) url.openConnection();
//            con.setReadTimeout(5000);
//            con.setRequestMethod("POST");
//            con.getInputStream();

            br = new BufferedReader(
                  new InputStreamReader(
                        con.getInputStream()));

            String str = "";
            //str = br.readLine();
            //System.out.println(str);
            while ((str = br.readLine()) != null) {
               if(str.equals("")) {
                  continue;
               }
               System.out.println(str.trim());
            }

         } catch (Exception e) {
            e.printStackTrace();
         } finally {
            con.disconnect();
            if (br != null) {
               try {
                  br.close();
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }

         }
      }
      
      
   }
   
   
   
   public void sendData(String btn) {
      SendThread st = new SendThread(btn);
      st.start();
   }
   
   public static void main(String[] args) {
//	   Random r = new Random(50);
//      while(true) {
//         int t = r.nextInt(50);
//         int h = r.nextInt(50);
//         SemiSendHttp01 shttp = new SemiSendHttp01();
//         shttp.sendData(t,h);
//         try {
//            Thread.sleep(3000);
//         } catch (InterruptedException e) {
//            e.printStackTrace();
//         }
//      }
	   SemiSendHttp01 shttp = new SemiSendHttp01();
	   
   }

}