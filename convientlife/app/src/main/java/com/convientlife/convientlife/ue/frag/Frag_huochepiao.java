package com.convientlife.convientlife.ue.frag;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.convientlife.convientlife.R;
import com.convientlife.convientlife.adapter.TrainAdapter;
import com.convientlife.convientlife.bean.TrainBean;
import com.convientlife.convientlife.bean.TrainSimpleBean;
import com.hiber.hiber.RootFrag;
import com.hiber.tools.Lgg;
import com.hiber.tools.layout.PercentRelativeLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.request.UriRequest;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/*
 * Created by Administrator on 2019/3/10 0010.
 */
public class Frag_huochepiao extends RootFrag {

    @BindView(R.id.iv_train_back)
    ImageView ivTrainBack;// 回退

    @BindView(R.id.tv_train_date)
    TextView tvTrainDate;// 日期
    @BindView(R.id.rl_train_date)
    PercentRelativeLayout rlTrainDate;// 日期面板

    @BindView(R.id.rl_trainlist_title)
    RelativeLayout rlTrainListTitle;// 标题栏
    @BindView(R.id.et_train_from)
    EditText etTrainFrom;// 出发地
    @BindView(R.id.et_train_to)
    EditText etTrainTo;// 目的地

    @BindView(R.id.tv_train_check)
    TextView tvTrainCheck;// 查询
    @BindView(R.id.rcv_train)
    RecyclerView rcvTrain;// 显示列表

    @BindView(R.id.cv_train)
    CalendarView cvTrain;// 日历

    private String choiceDate;

    @Override
    public int onInflateLayout() {
        return R.layout.frag_huochepiao;
    }

    @Override
    public void initViewFinish() {
        super.initViewFinish();
        setToday();
        initClick();

    }

    private void initClick() {
        // 回退键
        ivTrainBack.setOnClickListener(v -> onBackPresss());
        // 点击日期
        rlTrainDate.setOnClickListener(v -> {
            cvTrain.setVisibility(View.VISIBLE);
            cvTrain.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
                choiceDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                Lgg.t(getClass().getSimpleName()).ii(choiceDate);
                String date = String.format(getString(R.string.date), choiceDate);
                tvTrainDate.setText(date);
                cvTrain.setVisibility(View.GONE);
            });
        });
        // 默认隐藏
        cvTrain.setVisibility(View.GONE);
        rlTrainListTitle.setVisibility(View.INVISIBLE);
        // 点击查询
        tvTrainCheck.setOnClickListener(v -> queryTrain());
    }

    private void queryTrain() {
        String trainFrom = getTrainFrom();
        String trainTo = getTrainTo();
        if (TextUtils.isEmpty(trainFrom)) {
            toast("出发地不能为空", 2000);
            return;
        }
        if (TextUtils.isEmpty(trainTo)) {
            toast("目的地不能为空", 2000);
            return;
        }
        Lgg.t(getClass().getSimpleName()).ii("choiceDate: " + choiceDate);
        RequestParams rp = new RequestParams("http://api.shujuzhihui.cn/api/search/train");
        rp.addQueryStringParameter("appKey", "7828c1d0aa9d45d4b16b0a291f782afa");
        rp.addQueryStringParameter("train_date", choiceDate);
        rp.addQueryStringParameter("from_station_name", trainFrom);
        rp.addQueryStringParameter("to_station_name", trainTo);
        x.http().get(rp, new Callback.CommonCallback<String>() {
            @Override
            public void responseBody(UriRequest uriRequest) {

            }

            @Override
            public void onSuccess(String json) {
                Lgg.t(getClass().getSimpleName()).ii(json);
                TrainBean trainBean = JSONObject.parseObject(json, TrainBean.class);
                String errorcode = trainBean.getERRORCODE();
                if (errorcode.equalsIgnoreCase("0")) {
                    List<TrainSimpleBean> trainSimpleBeans = new ArrayList<>();
                    List<TrainBean.RESULTBean> result = trainBean.getRESULT();
                    for (TrainBean.RESULTBean resultBean : result) {
                        TrainSimpleBean tsb = new TrainSimpleBean();
                        tsb.setTrainClass(resultBean.getStation_train_code());
                        tsb.setTrainFromPlace(resultBean.getStart_station_name());
                        tsb.setTrainToPlace(resultBean.getTo_station_name());
                        tsb.setTrainFromTime(resultBean.getStart_time());
                        tsb.setTrainToTime(resultBean.getArrive_time());
                        tsb.setTrainDuration(resultBean.getLishi());
                        trainSimpleBeans.add(tsb);
                    }
                    setRcvTrain(trainSimpleBeans);

                } else if (errorcode.equalsIgnoreCase("1001")) {
                    toast("请填写正确的出发地", 2000);
                } else if (errorcode.equalsIgnoreCase("1002")) {
                    toast("请填写正确的目的地", 2000);
                } else {
                    toast("网络异常", 2000);
                }
            }

            private void setRcvTrain(List<TrainSimpleBean> trainSimpleBeans) {
                rlTrainListTitle.setVisibility(View.VISIBLE);
                LinearLayoutManager lm = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
                rcvTrain.setLayoutManager(lm);
                rcvTrain.setAdapter(new TrainAdapter(activity, trainSimpleBeans));
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Lgg.t(getClass().getSimpleName()).ii(throwable.getMessage());
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {
                Lgg.t(getClass().getSimpleName()).ii("onfinish");
            }
        });
    }

    private String getTrainFrom() {
        return etTrainFrom.getText().toString().trim().replace(" ", "");
    }

    private String getTrainTo() {
        return etTrainTo.getText().toString().trim().replace(" ", "");
    }

    private void setToday() {
        choiceDate = getToday();
        String date = String.format(getString(R.string.date), choiceDate);
        tvTrainDate.setText(date);
    }

    @SuppressLint("SimpleDateFormat")
    private String getToday() {
        SimpleDateFormat sif = new SimpleDateFormat("yyyy-MM-dd");
        return sif.format(new Date());
    }

    @Override
    public void onNexts(Object o, View view, String s) {

    }

    @Override
    public boolean onBackPresss() {
        if (cvTrain.getVisibility() == View.VISIBLE) {
            cvTrain.setVisibility(View.GONE);
        } else {
            toFrag(getClass(), Frag_home.class, null, false);
        }
        return true;
    }

}
