package id.delta.webview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawer;
    WebView webview;
    Toolbar toolbar;
    String weburl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        content();
        contentWeb();
    }

    void content(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.drawable.ic_menu);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer.setDrawerListener(new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close));

        final NavigationView navView = (NavigationView) findViewById(R.id.navigation_view);
        navView.setNavigationItemSelectedListener(this);
    }

    void sharePost() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", "Silahkan Kunjungi");
        intent.putExtra("android.intent.extra.TEXT", weburl);
        startActivity(Intent.createChooser((Intent) intent, (CharSequence) "Dibagikan menggunakan Aplikasi WebView"));
    }

    void contentWeb(){
        webview = (WebView) findViewById(R.id.webview);
        webview.loadUrl("http://www.dsttr.site/");
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setWebViewClient(new MyCustomWebViewClient());
    }

    private class MyCustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if(Uri.parse(url).getHost().endsWith("dsttr.site")) {
                return false;
            }


         //   view.loadUrl(url);
            weburl = view.getUrl();
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mDrawer.closeDrawers();
        final int mItemId = item.getItemId();
        mDrawer.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (mItemId) {
                    case R.id.nav_home:
                        break;
                    case R.id.nav_fb:
                        startBrowserIntent("https://www.facebook.com/DJ.FDFRS",MainActivity.this);
                        break;
                    case R.id.nav_reload:
                        break;
                    case R.id.nav_about:
                        break;
                    case R.id.nav_exit:
                        MainActivity.this.finish();
                        break;
                }
            }
        }, 75);
        return true;
    }


    public static void startBrowserIntent(final String baseUrl, Context activity) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(baseUrl));
        activity.startActivity(browserIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            sharePost();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
