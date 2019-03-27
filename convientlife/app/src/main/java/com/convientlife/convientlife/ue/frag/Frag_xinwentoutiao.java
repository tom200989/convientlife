package com.convientlife.convientlife.ue.frag;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.convientlife.convientlife.R;
import com.convientlife.convientlife.bean.XinwenBean;
import com.convientlife.convientlife.utils.Ogg;
import com.hiber.hiber.RootFrag;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.request.UriRequest;
import org.xutils.x;

import butterknife.BindView;

/*
 * Created by Administrator on 2019/3/10 0010.
 */
public class Frag_xinwentoutiao extends RootFrag {

    @BindView(R.id.iv_xinwen_back)
    ImageView ivXinwenBack;
    @BindView(R.id.et_xinwen)
    EditText etXinwen;
    @BindView(R.id.tv_xinwen_click)
    TextView tvXinwenClick;
    @BindView(R.id.tv_xinwen_result)
    TextView tvXinwenResult;

    @Override
    public int onInflateLayout() {
        return R.layout.frag_xinwentoutiao;
    }

    @Override
    public void initViewFinish() {
        ivXinwenBack.setOnClickListener(v -> onBackPresss());
        tvXinwenClick.setOnClickListener(v -> {
            String phone = etXinwen.getText().toString();
            if (TextUtils.isEmpty(phone) | phone.length() < 11) {
                toast("请输入11位手机号", 3000);
                return;
            }
            clickQuery(phone);
        });
    }

    private void clickQuery(String phone) {
        Ogg.hideKeyBoard(activity);
        RequestParams rp = new RequestParams("http://apis.juhe.cn/mobile/get");
        rp.addQueryStringParameter("phone", phone);
        rp.addQueryStringParameter("key", "3be3a2dda33fa719b1f55cffbfcd80f3");
        x.http().get(rp, new Callback.CommonCallback<String>() {
            @Override
            public void responseBody(UriRequest uriRequest) {

            }

            @Override
            public void onSuccess(String json) {
                XinwenBean xinwenBean = JSONObject.parseObject(json, XinwenBean.class);
                if (xinwenBean.getResultcode().equalsIgnoreCase("200")) {
                    XinwenBean.ResultBean result = xinwenBean.getResult();
                    String province = result.getProvince();
                    String city = result.getCity();
                    String company = result.getCompany();

                    String content = province + city + " " + company;
                    tvXinwenResult.setText(content);

                } else {
                    toast("网络异常", 2000);
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                toast("网络异常", 2000);
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
        toFrag(getClass(), Frag_home.class, null, false);
        return true;
    }
}
