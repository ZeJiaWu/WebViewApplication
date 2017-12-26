package com.example.webviewapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainFirstActivity extends AppCompatActivity {
   // private  String uri1 = "http://www.baidu.com";
    private WebView webView;
    private ProgressDialog prodialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_first);
//        Uri uri = Uri.parse(uri1);
//        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
//        startActivity(intent);
//        init();
    }

    private void init() {
        webView =(WebView) findViewById(R.id.webview);
        webView.loadUrl("http://www.baidu.com");//网洛文件
        webView.requestFocus();//获取焦点
        //网络页面设置
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {//设置WEB视图客户端
                //让用其WebView方式去访问
                view.loadUrl(url);
                return true;
            }
        });
        //支持JavaScript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);//设置JAVA脚本启用
        //缓存
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//--------加载缓存其他网络
        //让其有加载进度条
        webView.setWebChromeClient(new WebChromeClient(){//设置WEB浏览器客户端
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress == 100){
                    closeDialog();
                }else {
                    openDialog(newProgress);
                }
            }
        });
    }

    private void openDialog(int newProgress) {
        if(prodialog == null){
            prodialog = new ProgressDialog(MainFirstActivity.this);
            prodialog.setTitle("正在加载");
            prodialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            prodialog.setProgress(newProgress);
            prodialog.show();
        }else {
            prodialog.setProgress(newProgress);
        }
    }

    private void closeDialog() {
        if(prodialog != null && prodialog.isShowing()){
            prodialog.dismiss();
            prodialog = null;
        }
    }
    //让其能返回
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//在关键
        if(keyCode ==KeyEvent.KEYCODE_BACK){//关键节点--回键码
            if(webView.canGoBack()){
                Toast.makeText(this,webView.getUrl(),Toast.LENGTH_SHORT).show();
                webView.goBack();
                return true;
            }else {
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
