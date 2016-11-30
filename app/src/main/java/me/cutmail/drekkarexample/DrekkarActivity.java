package me.cutmail.drekkarexample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Toast;

import com.coshx.drekkar.Callback;
import com.coshx.drekkar.Drekkar;
import com.coshx.drekkar.EventBus;
import com.coshx.drekkar.WhenReady;

public class DrekkarActivity extends AppCompatActivity {

    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drekkar);

        webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("file:///android_res/raw/drekkar.html");
        webView.getSettings().setJavaScriptEnabled(true);

        Drekkar.getDefault(this, webView, new WhenReady() {
            @Override
            public void run(EventBus eventBus) {
                eventBus.post("CallFromJava");

                eventBus.register("showToast", new Callback() {
                    @Override
                    public void run(String name, final Object data) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }
}
