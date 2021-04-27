package com.milkywaylhy.ex76webservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.GenericLifecycleObserver;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv= findViewById(R.id.tv);
        iv= findViewById(R.id.iv);
    }

    public void clickbtn(View view) {
        //서버에서 제공하는 텍스트파일 읽어오기
        //네트워크 작업은 별도 Thread 가 해야 함.
        Thread t=new Thread(){
            @Override
            public void run() {
                //텍스트문서의 주소
                String textUrl="http://gkdbs514.dothome.co.kr/index.html";

                try {
                    //해임달 객체 생성
                    URL url = new URL(textUrl);
                    //무지개 로드 만들기
                    InputStream is= url.openStream();
                    InputStreamReader isr= new InputStreamReader(is);//문자스트림 변경
                    BufferedReader reader= new BufferedReader(isr);//보조 문자 스트림

                    StringBuffer buffer = new StringBuffer();
                    String line= reader.readLine();
                    while(line!=null){
                        buffer.append(line);
                        line= reader.readLine();
                    }

                    //별도 Thread는 UI변경 불가
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(buffer.toString());
                        }
                    });
                    tv.setText(buffer.toString());

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

    }

    public void clickbtn2(View view) {
        //서버에서 제공하는 이미지파일 읽어오기
        //직접 읽어들이는 코딩을 하면 힘듦
        //이때 쉽게 사용하기 위해 이미지 라이브러리 사용[Glide, picasso]

        String imgUrl="http://gkdbs514.dothome.co.kr/imgs/img01.jpg";
        Picasso.get().load(imgUrl).into(iv);


//        new Thread(){
//            @Override
//            public void run() {
//                String imgUrl="http://gkdbs514.dothome.co.kr/imgs/img01.jpg";
//
//                try {
//                    URL url = new URL(imgUrl);
//                    InputStream is= url.openStream();
//
//                    //이미지를 가지는 객체 bitmap
//                    final Bitmap bm= BitmapFactory.decodeStream(is);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            iv.setImageBitmap(bm);//final을 붙여야 bm사용가능
//                        }
//                    });
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }.start();

    }
}