package com.example.demo;

import java.io.File;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import dalvik.system.DexClassLoader;
import com.example.interfaces.Idynamic;

public class MainActivity extends ActionBarActivity {
	
	private Idynamic lib;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 Button showBannerBtn = (Button) findViewById(R.id.show_banner_btn);
		 /**ʹ��DexClassLoader��ʽ������*/  
	        //dexѹ���ļ���·��(������apk,jar,zip��ʽ)     
	        //File.separator  windows��\ ,unix��/
	        String dexPath =Environment.getExternalStorageDirectory().toString() + File.separator +"Dynamic_temp.jar";  
	        //dex��ѹ�ͷź��Ŀ¼  
	        //String dexOutputDir = getApplicationInfo().dataDir;  
	        //String dexOutputDirs = Environment.getExternalStorageDirectory().toString();  
	         File dexOutputDirs = getDir("temp", Context.MODE_PRIVATE);
	        //����DexClassLoader  
	        //��һ����������dexѹ���ļ���·��  
	        //�ڶ�����������dex��ѹ�����ŵ�Ŀ¼  
	        //��������������C/C++�����ı��ؿ��ļ�Ŀ¼,����Ϊnull  
	        //���ĸ�����������һ�����������  
	        DexClassLoader cl = new DexClassLoader(dexPath,dexOutputDirs.getAbsolutePath(),null,getClassLoader());  
	        //������  
	        try {  
	            //com.dynamic.impl.Dynamic�Ƕ�̬����  
	            //ʹ��DexClassLoader������  
	            Class libProviderClazz = cl.loadClass("com.example.impl.Dynamic");  
	            //ʹ��PathClassLoader������  
	            //Class libProviderClazz = pcl.loadClass("com.dynamic.impl.Dynamic");  
	            lib = (Idynamic) libProviderClazz.newInstance();
	            if(lib != null){  
	                lib.init(MainActivity.this);  
	            }  
	        } catch (Exception exception) {  
	            exception.printStackTrace();  
	        }  
	        /**����ֱ���ö�̬���еķ���*/  
	        showBannerBtn.setOnClickListener(new View.OnClickListener() {  
	            public void onClick(View view) {  
	               if(lib != null){  
	                   lib.showBanner();
	               }else{  
	                 Toast.makeText(getApplicationContext(), "�����ʧ��", 1500).show();  
	               }  
	            }  
	        });  
	}
	
}
