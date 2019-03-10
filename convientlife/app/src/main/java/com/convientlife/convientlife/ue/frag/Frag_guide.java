package com.convientlife.convientlife.ue.frag;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.convientlife.convientlife.R;
import com.convientlife.convientlife.cons.SPCons;
import com.hiber.hiber.RootFrag;
import com.hiber.hiber.language.Sgg;
import com.hiber.tools.layout.PercentLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/*
 * Created by Administrator on 2019/3/10 0010.
 */
public class Frag_guide extends RootFrag {

    @BindView(R.id.vp)
    ViewPager vp;// 轮播图
    List<ImageView> dots = new ArrayList<>();
    @BindView(R.id.ll_dot)
    PercentLinearLayout llDot;// 轮播点
    @BindView(R.id.tv_guide_start)
    TextView tvGuideStart;// 开始按钮
    @BindView(R.id.tv_guide_des)
    TextView tvGuideDes;// 描述

    int[] draws = {R.drawable.guide1, R.drawable.guide2, R.drawable.guide3};
    int[] dess = {R.string.guide_1, R.string.guide_2, R.string.guide_3};
    int dot_check = R.drawable.dot_check;
    int dot_uncheck = R.drawable.dot_uncheck;
    List<ImageView> ivs = new ArrayList<>();


    @Override
    public int onInflateLayout() {
        return R.layout.frag_guide;
    }

    @Override
    public void onNexts(Object o, View view, String s) {
        createPage();
        vp.setAdapter(new VPAdapter());
        setPageListener(vp);
        setClick();
    }

    private void setClick() {
        tvGuideStart.setOnClickListener(v -> {
            Sgg.getInstance(activity).putBoolean(SPCons.IS_GUIDE, true);
            toFrag(getClass(), Frag_home.class, null, true);
        });
    }

    private void setPageListener(ViewPager vp) {
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                // 开启按钮
                tvGuideStart.setVisibility(position == draws.length - 1 ? View.VISIBLE : View.INVISIBLE);
                // 轮播点
                for (int i = 0; i < dots.size(); i++) {
                    Drawable dotCheck = getResources().getDrawable(R.drawable.dot_check);
                    Drawable dotUnCheck = getResources().getDrawable(R.drawable.dot_uncheck);
                    dots.get(i).setImageDrawable(i == position ? dotCheck : dotUnCheck);
                }
                // 描述文本
                tvGuideDes.setText(dess[position]);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    /**
     * 创建页面
     */
    private void createPage() {
        // 描述文本
        tvGuideDes.setText(dess[0]);
        for (int i = 0; i < draws.length; i++) {
            // 背景图片
            ImageView iv = new ImageView(activity);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            RelativeLayout.LayoutParams ip = new RelativeLayout.LayoutParams(-1, -1);
            iv.setLayoutParams(ip);
            iv.setImageDrawable(getResources().getDrawable(draws[i]));
            ivs.add(iv);
            // 点
            ImageView dotiv = new ImageView(activity);
            dotiv.setScaleType(ImageView.ScaleType.FIT_XY);
            LinearLayout.LayoutParams dotlp = new LinearLayout.LayoutParams(15, 15);
            dotlp.setMarginStart(15);
            dotiv.setLayoutParams(dotlp);
            dotiv.setImageDrawable(i == 0 ? getResources().getDrawable(dot_check) : getResources().getDrawable(dot_uncheck));
            dots.add(dotiv);
            llDot.addView(dotiv);
        }

    }

    @Override
    public boolean onBackPresss() {
        finishActivity();
        return true;
    }

    /* -------------------------------------------- class -------------------------------------------- */
    public class VPAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return draws.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView pageIv = ivs.get(position);
            container.addView(pageIv);
            return pageIv;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
