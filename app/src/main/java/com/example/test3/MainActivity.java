package com.example.test3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    EditText edit;
    TextView text;

    String Key = "LhJ8ZI%2B5mq2PSlEJf%2FqFKk%2FS9vSYq4tHg0UTKPhddcpzm9cKrFkdIoOVfV%2Bm0HIPkMkIwu0eR1Jag16zh33i%2Fg%3D%3D           //&numOfRows=10&pageNo=1";
    String data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = (EditText)findViewById(R.id.edit);
        text = (TextView)findViewById(R.id.text);
    }


    public void mOnClick (View v){

        switch(v.getId()){
            case R.id.button :

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        data = getXmlData();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text.setText(data);
                            }
                        });
                    }
                }).start();
                break;
        }
    }
    String getXmlData(){

        StringBuffer buffer = new StringBuffer();

        String str = edit.getText().toString();
        String location = null;

        //key = LhJ8ZI%2B5mq2PSlEJf%2FqFKk%2FS9vSYq4tHg0UTKPhddcpzm9cKrFkdIoOVfV%2Bm0HIPkMkIwu0eR1Jag16zh33i%2Fg%3D%3D
        // 전체 주소 :"http://apis.data.go.kr/6260000/BusanTblFnrstrnStusService/getTblFnrstrnStusInfo?serviceKey="
        // + Key
        //+"addJibun="+location
        // +"addrJibun="+location


        String queryUrl = "http://apis.data.go.kr/6260000/BusanTblFnrstrnStusService/getTblFnrstrnStusInfo?serviceKey=LhJ8ZI%2B5mq2PSlEJf%2FqFKk%2FS9vSYq4tHg0UTKPhddcpzm9cKrFkdIoOVfV%2Bm0HIPkMkIwu0eR1Jag16zh33i%2Fg%3D%3D&numOfRows=10&pageNo=1";
        try {
            URL url = new URL(queryUrl);
            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8"));

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT :
                        buffer.append ("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG :
                        tag = xpp.getName();

                        if(tag.equals("item"));
                        else if(tag.equals("addrJibun")){
                            buffer.append("주소 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("bsnsSector")){
                            buffer.append("구분 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("bsnsNm")){
                            buffer.append("식당 이름 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        break;
                    case XmlPullParser.TEXT :
                        break;
                    case XmlPullParser.END_TAG :
                        tag = xpp.getName();

                        if (tag.equals("item")) buffer.append("\n");
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {

        }

        buffer.append("파싱 끝\n");

        return buffer.toString();
    }
}
