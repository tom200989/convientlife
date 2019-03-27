package com.convientlife.convientlife.ue.frag;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.convientlife.convientlife.R;
import com.convientlife.convientlife.adapter.FanyiAdapter;
import com.convientlife.convientlife.bean.TraslateBean;
import com.convientlife.convientlife.utils.Ogg;
import com.hiber.hiber.RootFrag;
import com.hiber.tools.Lgg;
import com.hiber.tools.layout.PercentRelativeLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.request.UriRequest;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/*
 * Created by Administrator on 2019/3/10 0010.
 */
public class Frag_Fanyi extends RootFrag {

    @BindView(R.id.iv_fanyi_back)
    ImageView ivFanyiBack;// 回退

    @BindView(R.id.et_fanyi)
    EditText etFanyi;// 输入框

    @BindView(R.id.tv_fanyi_click)
    TextView tvFanyiClick;// 点击翻译

    @BindView(R.id.rl_fanyi_language)
    PercentRelativeLayout rlFanyiLanguage;// 选择语言面板
    @BindView(R.id.tv_set_language)
    TextView tvSetLanguage;// 选择语言

    @BindView(R.id.iv_alpha_click)
    ImageView ivAlphaClick;// 透明背景
    @BindView(R.id.rcv_language_choice)
    RecyclerView rcvLanguageChoice;// 语言列表

    @BindView(R.id.tv_fanyi_translate)
    TextView tvFanyiTranslate;// 翻译后文本

    private List<String> languages = new ArrayList<>();
    private int TO_LANGUAGE = 1;// 目标语言类型(1中文,2日文,3英文,4韩文,5法文,6俄文,7葡萄牙文,8西班牙文,9auto,默认为1)

    @Override
    public int onInflateLayout() {
        return R.layout.frag_fanyi;
    }

    @Override
    public void onNexts(Object o, View view, String s) {
        initRes();
        initClick();
    }

    private void initRes() {
        languages = Arrays.asList(getResources().getStringArray(R.array.fanyi));
    }

    private void initClick() {
        // 回退
        ivFanyiBack.setOnClickListener(v -> onBackPresss());
        // 透明背景
        ivAlphaClick.setOnClickListener(v -> onBackPresss());
        // 选择语言面板
        rlFanyiLanguage.setOnClickListener(v -> {
            showOrHideChoice(true);
            setManagetAndAdapter();
        });

        // 开始翻译
        tvFanyiClick.setOnClickListener(v -> {
            String content = etFanyi.getText().toString();
            if (TextUtils.isEmpty(content)) {
                return;
            }
            Ogg.hideKeyBoard(activity);
            requestToTranslate(content);
        });
    }

    private void setManagetAndAdapter() {
        LinearLayoutManager lm = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        rcvLanguageChoice.setLayoutManager(lm);
        FanyiAdapter adapter = new FanyiAdapter(activity, languages);
        adapter.setOnFanYiItemClickListener(position -> {
            tvSetLanguage.setText(languages.get(position));
            showOrHideChoice(false);
            TO_LANGUAGE = position + 1;
        });
        rcvLanguageChoice.setAdapter(adapter);
    }

    private void requestToTranslate(String content) {
        Lgg.t(getClass().getSimpleName()).ii("requestToTranslate");
        RequestParams rp = new RequestParams("http://api.shujuzhihui.cn/api/sjzhApi/searchFanyi");
        rp.addQueryStringParameter("appKey", "9bca78974c0847eca97fa3653726d1ec");
        rp.addQueryStringParameter("text", content);
        rp.addQueryStringParameter("from", "9");
        rp.addQueryStringParameter("to", String.valueOf(TO_LANGUAGE));
        x.http().get(rp, new Callback.CommonCallback<String>() {
            @Override
            public void responseBody(UriRequest uriRequest) {

            }

            @Override
            public void onSuccess(String json) {
                Lgg.t(getClass().getSimpleName()).ii(json);
                TraslateBean traslateBean = JSONObject.parseObject(json, TraslateBean.class);
                if (traslateBean.getERRORCODE().equalsIgnoreCase("0")) {
                    tvFanyiTranslate.setText(traslateBean.getRESULT());
                } else {
                    toast("网络异常", 2000);
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Lgg.t(getClass().getSimpleName()).ee(throwable.getMessage());
                toast("网络异常", 2000);
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {
                Lgg.t(getClass().getSimpleName()).ii("requestToTranslate onFinished");
            }
        });
    }

    @Override
    public boolean onBackPresss() {
        if (rcvLanguageChoice.getVisibility() == View.VISIBLE | ivAlphaClick.getVisibility() == View.VISIBLE) {
            showOrHideChoice(false);
        } else {
            toFrag(getClass(), Frag_home.class, null, false);
        }

        return true;
    }

    private void showOrHideChoice(boolean isShow) {
        if (isShow) {
            rcvLanguageChoice.setVisibility(View.VISIBLE);
            ivAlphaClick.setVisibility(View.VISIBLE);
        } else {
            rcvLanguageChoice.setVisibility(View.GONE);
            ivAlphaClick.setVisibility(View.GONE);
        }
    }
}
