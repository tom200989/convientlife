package com.convientlife.convientlife.ue.frag;

import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.convientlife.convientlife.R;
import com.convientlife.convientlife.bean.MovieBean;
import com.hiber.hiber.RootFrag;
import com.hiber.tools.Lgg;
import com.hiber.tools.layout.PercentRelativeLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.request.UriRequest;
import org.xutils.x;

import butterknife.BindView;

/*
 * Created by Administrator on 2019/3/10 0010.
 */
public class Frag_dianyingpiao extends RootFrag {
    @BindView(R.id.iv_movie_back)
    ImageView ivMovieBack;// 回退
    @BindView(R.id.rl_banner)
    PercentRelativeLayout rlBanner;// 菜单栏
    @BindView(R.id.wv_movie)
    WebView wvMovie;// H5
    private Callback.Cancelable cancelable;

    @Override
    public int onInflateLayout() {
        return R.layout.frag_dianyingpiao;
    }

    @Override
    public void initViewFinish() {
        super.initViewFinish();
        ivMovieBack.setOnClickListener(v -> onBackPresss());
        // new Thread(this::getH5Url).start();
        setWebView(wvMovie, "http://dianying.nuomi.com");
    }

    private void getH5Url() {
        RequestParams rp = new RequestParams("http://v.juhe.cn/wepiao/query?key=9e92f84b417d306d3d29da0c2a5eb986");
        cancelable = x.http().get(rp, new Callback.CommonCallback<String>() {
            @Override
            public void responseBody(UriRequest uriRequest) {

            }

            @Override
            public void onSuccess(String json) {
                Lgg.t("dianyingpiao").ii(json);
                MovieBean movieBean = JSONObject.parseObject(json, MovieBean.class);
                if (movieBean.getError_code() == 0) {
                    String h5url = movieBean.getResult().getH5url();
                    String h5weixin = movieBean.getResult().getH5weixin();
                    activity.runOnUiThread(() -> setWebView(wvMovie, h5url));
                } else {
                    toast("网络异常", 2000);
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onNexts(Object o, View view, String s) {

    }

    @Override
    public boolean onBackPresss() {
        if (cancelable != null) {
            cancelable.cancel();
        }
        toFrag(getClass(), Frag_home.class, null, false);
        return true;
    }

    private void setWebView(WebView wv, String url) {

        // 4.获取wb的设置对象
        WebSettings wv_setting = wv.getSettings();
        // 5.设置js效果(此处为false:设置该步会影响webview加载完成的监听, 建议无特殊效果要求不要使用)
        wv_setting.setJavaScriptEnabled(false);
        // 6.设置类似于地图的缩放触碰块效果(此处为false:会影响用户体验, 一般设置双击放大即可)
        wv_setting.setBuiltInZoomControls(false);
        // 7.设置双击放大缩小(此处为false)
        wv_setting.setUseWideViewPort(false);
        // 8.设置webview自适应屏幕
        wv_setting.setLoadWithOverviewMode(true);
        // 9.设置webview布局规则
        wv_setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //如果不设置WebViewClient，请求会跳转系统浏览器
        wv.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //该方法在Build.VERSION_CODES.LOLLIPOP以前有效，从Build.VERSION_CODES.LOLLIPOP起，建议使用shouldOverrideUrlLoading(WebView, WebResourceRequest)} instead
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242

                // if (url.toString().contains("sina.cn")) {
                //     view.loadUrl("http://ask.csdn.net/questions/178242");
                //     return true;
                // }

                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
                // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //     if (request.getUrl().toString().contains("sina.cn")) {
                //         view.loadUrl("http://ask.csdn.net/questions/178242");
                //         return true;
                //     }
                // }

                return false;
            }

        });
        // 3.webview加载路径(如果为SD卡本地路径,需要添加头--> file:///sdcard)
        wv.loadUrl(url);
    }
}
