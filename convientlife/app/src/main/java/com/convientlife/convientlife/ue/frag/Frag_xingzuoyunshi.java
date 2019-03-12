package com.convientlife.convientlife.ue.frag;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.convientlife.convientlife.R;
import com.convientlife.convientlife.adapter.XingzuoAdapter;
import com.convientlife.convientlife.bean.XingzuoBean;
import com.hiber.cons.Cons;
import com.hiber.hiber.RootFrag;
import com.hiber.tools.Lgg;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.request.UriRequest;
import org.xutils.x;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/*
 * Created by Administrator on 2019/3/10 0010.
 */
public class Frag_xingzuoyunshi extends RootFrag {

    @BindView(R.id.iv_fanyi_back)
    ImageView ivFanyiBack;
    @BindView(R.id.rcv_xingzuo)
    RecyclerView rcvXingzuo;

    @BindView(R.id.sv_xingzuo)
    ScrollView svXingzuo;
    @BindView(R.id.tv_xingzuo_date)
    TextView tvXingzuoDate;
    @BindView(R.id.tv_xingzuo_name)
    TextView tvXingzuoName;
    @BindView(R.id.tv_xingzuo_datetime)
    TextView tvXingzuoDatetime;
    @BindView(R.id.tv_xingzuo_all)
    TextView tvXingzuoAll;
    @BindView(R.id.tv_xingzuo_color)
    TextView tvXingzuoColor;
    @BindView(R.id.tv_xingzuo_health)
    TextView tvXingzuoHealth;
    @BindView(R.id.tv_xingzuo_love)
    TextView tvXingzuoLove;
    @BindView(R.id.tv_xingzuo_money)
    TextView tvXingzuoMoney;
    @BindView(R.id.tv_xingzuo_number)
    TextView tvXingzuoNumber;
    @BindView(R.id.tv_xingzuo_QFriend)
    TextView tvXingzuoQFriend;
    @BindView(R.id.tv_xingzuo_summary)
    TextView tvXingzuoSummary;
    @BindView(R.id.tv_xingzuo_work)
    TextView tvXingzuoWork;

    @Override
    public int onInflateLayout() {
        return R.layout.frag_xingzuo;
    }

    @Override
    public void initViewFinish() {
        super.initViewFinish();
        ivFanyiBack.setOnClickListener(v -> onBackPresss());
        initRCV();
    }

    private void initRCV() {
        GridLayoutManager gm = new GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false);
        rcvXingzuo.setLayoutManager(gm);
        List<String> xingzuos = Arrays.asList(getResources().getStringArray(R.array.xingzuo));
        XingzuoAdapter xingzuoAdapter = new XingzuoAdapter(activity, xingzuos);
        xingzuoAdapter.setOnClickXingzuoListener(position -> requestXingzuo(xingzuos.get(position)));
        rcvXingzuo.setAdapter(xingzuoAdapter);
    }

    private void requestXingzuo(String xingzuo) {
        String key = "419c67c7b63b62ac0696bfc53090ad4c";
        RequestParams rp = new RequestParams("http://web.juhe.cn:8080/constellation/getAll");
        rp.addQueryStringParameter("key", key);
        rp.addQueryStringParameter("consName", xingzuo);
        rp.addQueryStringParameter("type", "today");
        x.http().get(rp, new Callback.CommonCallback<String>() {
            @Override
            public void responseBody(UriRequest uriRequest) {

            }

            @Override
            public void onSuccess(String s) {
                svXingzuo.setVisibility(View.VISIBLE);
                XingzuoBean xingzuoBean = JSONObject.parseObject(s, XingzuoBean.class);
                if (xingzuoBean.getError_code() == 0) {
                    tvXingzuoDate.setText(formatText(getString(R.string.xingzuo_date), String.valueOf(xingzuoBean.getDate())));
                    tvXingzuoName.setText(formatText(getString(R.string.xingzuo_name), xingzuoBean.getName()));
                    tvXingzuoDatetime.setText(formatText(getString(R.string.xingzuo_datetime), xingzuoBean.getDatetime()));
                    tvXingzuoAll.setText(formatText(getString(R.string.xingzuo_all), xingzuoBean.getAll()));
                    tvXingzuoColor.setText(formatText(getString(R.string.xingzuo_color), xingzuoBean.getColor()));
                    tvXingzuoHealth.setText(formatText(getString(R.string.xingzuo_health), xingzuoBean.getHealth()));
                    tvXingzuoLove.setText(formatText(getString(R.string.xingzuo_love), xingzuoBean.getLove()));
                    tvXingzuoMoney.setText(formatText(getString(R.string.xingzuo_money), xingzuoBean.getMoney()));
                    tvXingzuoNumber.setText(formatText(getString(R.string.xingzuo_number), String.valueOf(xingzuoBean.getNumber())));
                    tvXingzuoQFriend.setText(formatText(getString(R.string.xingzuo_friend), xingzuoBean.getQFriend()));
                    tvXingzuoSummary.setText(formatText(getString(R.string.xingzuo_summary), xingzuoBean.getSummary()));
                    tvXingzuoWork.setText(formatText(getString(R.string.xingzuo_work), xingzuoBean.getWork()));
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Lgg.t(Cons.TAG).ee(throwable.getMessage());
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private String formatText(String strId, String content) {
        return String.format(strId, content);
    }

    @Override
    public void onNexts(Object o, View view, String s) {

    }

    @Override
    public boolean onBackPresss() {
        if (svXingzuo.getVisibility() == View.VISIBLE) {
            svXingzuo.setVisibility(View.GONE);
        } else {
            toFrag(getClass(), Frag_home.class, null, false);
        }

        return true;
    }
}
