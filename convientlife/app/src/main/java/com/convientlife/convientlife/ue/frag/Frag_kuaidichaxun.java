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
import com.convientlife.convientlife.adapter.KuaidiAdapter;
import com.convientlife.convientlife.bean.KuaidiBean;
import com.convientlife.convientlife.bean.KuaidiSimplebean;
import com.hiber.hiber.RootFrag;
import com.hiber.tools.Lgg;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.request.UriRequest;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/*
 * Created by Administrator on 2019/3/10 0010.
 */
public class Frag_kuaidichaxun extends RootFrag {
    @BindView(R.id.iv_kuaidi_back)
    ImageView ivKuaidiBack;// 回退键
    @BindView(R.id.et_kuaidi)
    EditText etKuaidi;// 快递号输入
    @BindView(R.id.tv_kuaidi_click)
    TextView tvKuaidiClick;// 点击查询
    @BindView(R.id.rcv_kuaidi)
    RecyclerView rcvKuaidi;// 显示快递结果

    @Override
    public int onInflateLayout() {
        return R.layout.frag_kuaidichaxun;
    }

    @Override
    public void initViewFinish() {
        super.initViewFinish();
        initClick();
    }

    private void initClick() {
        ivKuaidiBack.setOnClickListener(v -> onBackPresss());
        tvKuaidiClick.setOnClickListener(v -> {
            String kuaidiNum = etKuaidi.getText().toString().trim().replace(" ", "");
            if (TextUtils.isEmpty(kuaidiNum)) {
                toast(R.string.input_your_express_num, 2000);
                return;
            }
            requestTheKuaidi(kuaidiNum);

        });
    }

    private void requestTheKuaidi(String num) {
        RequestParams rp = new RequestParams("http://api.shujuzhihui.cn/api/sjzhApi/searchExpress");
        rp.addBodyParameter("appKey", "fa7468e16a0e493c9c983858409c82cd");
        rp.addBodyParameter("expressNo", num);
        x.http().post(rp, new Callback.CommonCallback<String>() {
            @Override
            public void responseBody(UriRequest uriRequest) {

            }

            @Override
            public void onSuccess(String s) {
                turnKuaiSimpleBean(s);
            }

            private void turnKuaiSimpleBean(String s) {
                KuaidiBean kuaidiBean = JSONObject.parseObject(s, KuaidiBean.class);
                if (kuaidiBean.getERRORCODE() == 0) {
                    List<KuaidiSimplebean> kuaidisimpleBeans = new ArrayList<>();
                    String company = kuaidiBean.getRESULT().getCom();
                    for (KuaidiBean.RESULTBean.ContextBean contextBean : kuaidiBean.getRESULT().getContext()) {
                        KuaidiSimplebean kuaidiSimplebean = new KuaidiSimplebean();
                        kuaidiSimplebean.setCompany(company);
                        kuaidiSimplebean.setDate(contextBean.getTime());
                        kuaidiSimplebean.setContent(contextBean.getDesc());
                        kuaidisimpleBeans.add(kuaidiSimplebean);
                    }
                    LinearLayoutManager lm = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
                    rcvKuaidi.setLayoutManager(lm);
                    rcvKuaidi.setAdapter(new KuaidiAdapter(activity, kuaidisimpleBeans));
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Lgg.t(getClass().getSimpleName()).ee(throwable.getMessage());
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
