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
		 /**使用DexClassLoader方式加载类*/  
	        //dex压缩文件的路径(可以是apk,jar,zip格式)     
	        //File.separator  windows是\ ,unix是/
	        String dexPath =Environment.getExternalStorageDirectory().toString() + File.separator +"Dynamic_temp.jar";  
	        //dex解压释放后的目录  
	        //String dexOutputDir = getApplicationInfo().dataDir;  
	        //String dexOutputDirs = Environment.getExternalStorageDirectory().toString();  
	         File dexOutputDirs = getDir("temp", Context.MODE_PRIVATE);
	        //定义DexClassLoader  
	        //第一个参数：是dex压缩文件的路径  
	        //第二个参数：是dex解压缩后存放的目录  
	        //第三个参数：是C/C++依赖的本地库文件目录,可以为null  
	        //第四个参数：是上一级的类加载器  
	        DexClassLoader cl = new DexClassLoader(dexPath,dexOutputDirs.getAbsolutePath(),null,getClassLoader());  
	        //加载类  
	        try {  
	            //com.dynamic.impl.Dynamic是动态类名  
	            //使用DexClassLoader加载类  
	            Class libProviderClazz = cl.loadClass("com.example.impl.Dynamic");  
	            //使用PathClassLoader加载类  
	            //Class libProviderClazz = pcl.loadClass("com.dynamic.impl.Dynamic");  
	            lib = (Idynamic) libProviderClazz.newInstance();
	            if(lib != null){  
	                lib.init(MainActivity.this);  
	            }  
	        } catch (Exception exception) {  
	            exception.printStackTrace();  
	        }  
	        /**下面分别调用动态类中的方法*/  
	        showBannerBtn.setOnClickListener(new View.OnClickListener() {  
	            public void onClick(View view) {  
	               if(lib != null){  
	                   lib.showBanner();
	               }else{  
	                 Toast.makeText(getApplicationContext(), "类加载失败", 1500).show();  
	               }  
	            }  
	        });  
	}
	
}
