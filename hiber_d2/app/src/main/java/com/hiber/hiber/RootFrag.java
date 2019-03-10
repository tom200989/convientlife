package com.hiber.hiber;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hiber.bean.PermissBean;
import com.hiber.bean.SkipBean;
import com.hiber.bean.StringBean;
import com.hiber.cons.Cons;
import com.hiber.impl.PermissedListener;
import com.hiber.tools.Lgg;
import com.hiber.tools.backhandler.FragmentBackHandler;
import com.hiber.ui.PermissFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by qianli.ma on 2018/7/23 0023.
 */

public abstract class RootFrag extends Fragment implements FragmentBackHandler {

    /*
     * fragment缓存: 记录从哪个fragment跳转过来
     * 该标记位由开发人员进行调用, 由于在开发当中涉及到很多时机
     * 框架并不能统一记录上一个跳转过来的位置, 其中影响最大的是推送场景
     * 例如从A--> B, 此时切换至后台, 推送到达, 但推送指定的目的是C,
     * 那么lastfrag如果被框架锁死, 则无法知道具体要回退到哪个页面
     * 因此决定由开发人员自行设置
     */
    public static Class lastFrag;

    private static final String TAG = "RootFrag";
    public FragmentActivity activity;
    public Unbinder unbinder;
    private View inflateView;
    private int layoutId;
    private static String whichFragmentStart;

    // 权限相关
    private int permissedCode = 0x101;
    private String[] initPermisseds;// 初始化需要申请的权限
    private String[] clickPermisseds;// 点击时需要申请的权限
    private static final int ACTION_DEFAULT = 0;// 默认情况
    private static final int ACTION_DENY = -1;// 拒绝情况
    private static final int ACTION_PASS = 1;// 同意情况
    private HashMap<HashMap<String, Integer>, Integer> permissedActionMap;// < < 权限 , 权限状态 > , 用户行为 >
    public PermissedListener permissedListener;// 权限申请监听器
    private View permissView;// 权限自定义制图
    private StringBean stringBean;// 权限默认字符内容
    private PermissBean permissbean;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
        Lgg.t(Cons.TAG).vv("Method--> " + getClass().getSimpleName() + ":onAttach()");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Lgg.t(Cons.TAG).vv("Method--> " + getClass().getSimpleName() + ":onCreateView()");
        // 1.填入layoutId
        layoutId = onInflateLayout();
        // 2.填充视图
        inflateView = View.inflate(activity, layoutId, null);
        // 3.绑定butterknife
        unbinder = ButterKnife.bind(this, inflateView);
        // 4.加载完视图后的操作--> 由子类重写
        initViewFinish();
        // 5.初始化权限
        initPermisseds = initPermissed();
        initPermissedActionMap(initPermisseds);
        Lgg.t(Cons.TAG).vv("Method--> " + getClass().getSimpleName() + ":return inflateView & init permissed");
        return inflateView;
    }

    /**
     * 初始化权限状态与用户Action
     *
     * @param permisseds 需要初始的权限组
     */
    private void initPermissedActionMap(String[] permisseds) {
        Lgg.t(Cons.TAG).vv("Method--> " + getClass().getSimpleName() + ":initPermissedActionMap()");
        if (permissedActionMap == null) {
            permissedActionMap = new HashMap<>();
        }
        permissedActionMap.clear();
        if (permisseds != null) {
            for (String permission : permisseds) {
                HashMap<String, Integer> map = new HashMap<>();
                map.put(permission, PackageManager.PERMISSION_DENIED);
                permissedActionMap.put(map, ACTION_DEFAULT);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Lgg.t(Cons.TAG).vv("Method--> " + getClass().getSimpleName() + ":onResume()");
        /* 1.初始化检查权限 */
        if (isReqPermissed(initPermisseds)) {
            // 1.1.处理未申请权限
            handlePermissed(false);
        } else {
            // 1.2.因点击申请时将initPermissions置空--> 初始化权限申请行为将不被重复触发
            // TOAT: 2018/12/21 疑问：是否有必要把初始化权限申请全部通过之后的回调提供给开发人员？目前不提供
            //if (permissedListener != null && initPermisseds != null) {
            //permissedListener.permissionResult(true,null);
            //}

            // 1.2.初始化权限全部通过 || 点击申请即使不通过 --> 也不影响数据初始化
            if (!EventBus.getDefault().isRegistered(this)) {
                // stick包存在--> 首次加载--> 执行注册
                EventBus.getDefault().register(this);
            }
        }

        /* 触发点击申请权限行为 */
        if (isReqPermissed(clickPermisseds)) {
            handlePermissed(true);
        } else {
            // 点击申请权限全部通过--> 接口回调
            if (permissedListener != null && clickPermisseds != null) {
                // 点击权限全部通过--> 执行你的业务逻辑（如启动照相机）
                permissedListener.permissionResult(true, null);
                clickPermisseds = null;// 防止重新进入该页面重复执行业务逻辑
            }
        }
    }

    /**
     * 处理未申请的权限
     *
     * @param isClickPermissed 是否为点击申请
     */
    private void handlePermissed(boolean isClickPermissed) {
        Lgg.t(Cons.TAG).vv("Method--> " + getClass().getSimpleName() + ":handlePermissed()");
        // 如果用户在同意后到system setting做了取消操作，需要把权限状态更新一下
        Collection<Integer> values = permissedActionMap.values();
        if (values.contains(ACTION_DEFAULT)) {// 默认情况(初始化）--> 直接请求权限申请
            requestPermissions(initPermisseds, permissedCode);
            Lgg.t(Cons.TAG).vv("Method--> " + getClass().getSimpleName() + ":call system requestPermissions()");
        } else {// 非默认情况（用户已通过系统框进行操作）--> 重新封装（记录拒绝的权限状态）
            Lgg.t(Cons.TAG).vv("Method--> " + getClass().getSimpleName() + ":user had click the permissed pop window");
            checkPermissedState(isClickPermissed ? clickPermisseds : initPermisseds);
            List<String> denyPermissions = new ArrayList<>();
            Set<HashMap<String, Integer>> hashMaps = permissedActionMap.keySet();
            for (HashMap<String, Integer> map : hashMaps) {
                Set<Map.Entry<String, Integer>> entries = map.entrySet();
                for (Map.Entry<String, Integer> entry : entries) {
                    if (entry.getValue() == PackageManager.PERMISSION_DENIED) {
                        denyPermissions.add(entry.getKey());
                    }
                }
            }
            // 把拒绝的权限接口对外提供
            if (permissedListener != null) {
                // 显示权限视窗
                showPermissFrag(denyPermissions);
            }

            // 点击申请情况--> 将点击权限设置为空
            if (isClickPermissed) {
                clickPermisseds = null;
            }
        }
    }

    /**
     * 显示权限视窗(fragment)
     *
     * @param denyPermissions 权限组
     */
    private void showPermissFrag(List<String> denyPermissions) {
        // 接受并处理外部重写的自定义contentView
        preparePermissView();

        // TOAT: 备用方案(缺点: 是需要用户同意顶层window显示的权限) // 弹出自定义权限框
        // permissWindow = new PermissWindow();
        // permissWindow.setOnClickCancelListener(() -> {
        //     // 接口回调给开发者
        //     permissedListener.permissionResult(false, denyPermissions.toArray(new String[denyPermissions.size()]));
        //     Lgg.t(Cons.TAG2).ii("Rootfrag: click cancel finish");
        // });
        // permissWindow.setOnClickOkListener(() -> {
        //     // 点击OK的行为不提供给开发人员
        //     Lgg.t(Cons.TAG2).ii("Rootfrag: click OK finish");
        // });
        // permissWindow.setVisibles(activity, permissView, stringBean);

        // 采用fragment方案代替以上方案 20190306
        PermissInnerBean permissInnerBean = new PermissInnerBean();
        permissInnerBean.setLayoutId(layoutId);
        permissInnerBean.setPermissView(permissView);
        permissInnerBean.setStringBean(stringBean);
        permissInnerBean.setPermissedListener(permissedListener);
        permissInnerBean.setDenyPermissons(denyPermissions.toArray(new String[denyPermissions.size()]));
        permissInnerBean.setCurrentFrag(getClass());
        // 启动权限视窗fragment
        toFrag(getClass(), PermissFragment.class, permissInnerBean, true, 500);
    }

    /**
     * 处理外部重写的自定义contentView
     */
    private void preparePermissView() {
        Lgg.t(Cons.TAG2).ii("Rootfrag: preparePermissView()");
        // PermissBean permissBean = overWritePermissedView();
        if (permissbean != null) {
            permissView = permissbean.getPermissView();
            stringBean = permissbean.getStringBean();
        }
    }

    /**
     * 申请权限回调
     *
     * @param requestCode  回调码
     * @param permissions  权限组
     * @param grantResults 权限组状态
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Lgg.t(Cons.TAG).vv("Method--> " + getClass().getSimpleName() + ":onRequestPermissionsResult()");
        Lgg.t(Cons.TAG).vv("Method--> " + getClass().getSimpleName() + ":permissions[length]" + permissions.length);
        checkPermissedState(permissions);// 检查权限当前的最新状态
    }

    /**
     * 检查权限当前的最新状态
     *
     * @param permissions 需要检查的权限组
     * @apiNote onRequestPermissionsResult()
     * @apiNote handlePermissed()
     */
    private void checkPermissedState(String[] permissions) {
        Lgg.t(Cons.TAG).vv("Method--> " + getClass().getSimpleName() + ":checkPermissedState()");
        HashMap<HashMap<String, Integer>, Integer> tempHashMap = new HashMap<>();
        for (String permission : permissions) {

            int permissedState;// 系统返回的权限状态
            int userAction;// 权限框弹出后用户的行为

            // 检查用户操作权限框后的拒绝状态
            boolean isDenied = PermissionChecker.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED;
            permissedState = isDenied ? PackageManager.PERMISSION_DENIED : PackageManager.PERMISSION_GRANTED;
            userAction = isDenied ? ACTION_DENY : ACTION_PASS;
            // 重新赋值更新Map状态
            Set<Map.Entry<HashMap<String, Integer>, Integer>> entries = permissedActionMap.entrySet();
            for (Map.Entry<HashMap<String, Integer>, Integer> entry : entries) {
                HashMap<String, Integer> permissedMap = entry.getKey();
                for (String permissionName : permissedMap.keySet()) {
                    if (permissionName.equalsIgnoreCase(permission)) {
                        HashMap<String, Integer> map = new HashMap<>();
                        map.put(permission, permissedState);
                        tempHashMap.put(map, userAction);
                    }
                }
            }
        }
        permissedActionMap = tempHashMap;
    }

    /**
     * 是否需要执行请求权限操作
     *
     * @param permissions 权限组
     * @return T：需要
     */
    private boolean isReqPermissed(String[] permissions) {
        Lgg.t(Cons.TAG).vv("Method--> " + getClass().getSimpleName() + ":isReqPermissed()");
        if (permissions == null) {
            return false;
        }
        // 1.循环检查权限
        List<Integer> permissionInt = new ArrayList<>();
        for (String permission : permissions) {
            permissionInt.add(PermissionChecker.checkSelfPermission(activity, permission));
        }

        // 2.判断是否有未通过的权限
        for (Integer permissionDenied : permissionInt) {
            if (permissionDenied == PackageManager.PERMISSION_DENIED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取其他fragment跳转过来的fragbean
     *
     * @param bean fragbean
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getData(FragBean bean) {

        /*
         * 重要: 移除传输完成的粘性事件
         * 这里为什么要移除？因为在fragment相互跳转时
         * poststicky对象会创建多个, 而且传递的数据都是Fragbean类型
         * 这样会导致往后每个fragment创建的订阅者 @Subcribe(...)
         * 都会接收到前面其他fragment跳转传输的事件
         * 这些事件实际上是与当前fragment无关的, 如果在压力测试下
         * 会造成内存溢出
         *
         * */
        Lgg.t(Cons.TAG).vv("Method--> " + getClass().getSimpleName() + ":getData()");
        Object attachs = bean.getAttach();
        whichFragmentStart = bean.getCurrentFragmentClass().getSimpleName();
        String targetFragment = bean.getTargetFragmentClass().getSimpleName();
        Lgg.t(Cons.TAG).vv("whichFragmentStart: " + whichFragmentStart);
        Lgg.t(Cons.TAG).vv("targetFragment: " + targetFragment);
        // 确保现在运行的是目标fragment
        if (getClass().getSimpleName().equalsIgnoreCase(targetFragment)) {
            Lgg.t(Cons.TAG).vv("whichFragmentStart <equal to> targetFragment");
            onNexts(attachs, inflateView, whichFragmentStart);// 抽象
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Lgg.t(Cons.TAG).vv("Method--> " + getClass().getSimpleName() + ":onPause()");
        if (EventBus.getDefault().isRegistered(this) && isReloadData()) {
            Lgg.t(Cons.TAG).vv("Method--> " + getClass().getSimpleName() + ":eventbus unregister");
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public boolean onBackPressed() {
        // 其他重写情况
        boolean isDispathcherBackPressed = onBackPresss();
        Lgg.t(Cons.TAG).vv("Method--> " + getClass().getSimpleName() + ":onBackPressed()--> isDispathcherBackPressed == " + isDispathcherBackPressed);
        return isDispathcherBackPressed;
    }

    /* -------------------------------------------- abstract -------------------------------------------- */


    /**
     * @return 1.填入layoutId
     */
    public abstract int onInflateLayout();

    /**
     * 2.你的业务逻辑
     *
     * @param yourBean           你的自定义附带对象(请执行强转)
     * @param view               填充视图
     * @param whichFragmentStart 由哪个fragment发起的跳转
     */
    public abstract void onNexts(Object yourBean, View view, String whichFragmentStart);

    /**
     * @return 3.点击返回键
     */
    public abstract boolean onBackPresss();

    /* -------------------------------------------- override -------------------------------------------- */

    /**
     * 由外部重写初始化权限
     *
     * @return 需要申请的权限组（可以为null, 为null即不申请)
     * @apiNote 重写
     */
    public String[] initPermissed() {
        return null;
    }

    /**
     * 首次初始化视图完成后的操作
     */
    public void initViewFinish() {

    }

    /**
     * 是否在页面恢复时重新拉取数据
     *
     * @return true:默认(T:会触发eventbus注销并在下次重新注册, 间接触发onNexts()的重复执行)
     */
    public boolean isReloadData() {
        return true;
    }

    /* -------------------------------------------- public -------------------------------------------- */

    /**
     * 点击申请权限
     *
     * @param permissions 需要申请的权限组
     */
    public void clickPermissed(String[] permissions) {
        Lgg.t(Cons.TAG).vv("Method--> " + getClass().getSimpleName() + ":clickPermissed()");
        initPermisseds = null;// 1.该步防止初始化权限重复申请
        clickPermisseds = permissions;
        if (isReqPermissed(clickPermisseds)) {// 2.点击权限申请
            Lgg.t(Cons.TAG).vv("Method--> " + getClass().getSimpleName() + ":to request permissed");
            initPermissedActionMap(clickPermisseds);
            requestPermissions(clickPermisseds, permissedCode);
        } else {
            Lgg.t(Cons.TAG).vv("Method--> " + getClass().getSimpleName() + ":no need to request permissed");
            if (permissedListener != null) {
                permissedListener.permissionResult(true, null);
            }
            clickPermisseds = null;
        }
    }

    /**
     * 设置permissbean
     *
     * @param permissbean 权限对象
     */
    public void setPermissView(PermissBean permissbean) {
        this.permissbean = permissbean;
    }

    // /**
    //  * 重写自定义权限视图 (由外部选择是否重写)
    //  *
    //  * @return 自定义权限视图
    //  */
    // public PermissBean overWritePermissedView() {
    //
    //     return null;
    // }

    /**
     * 普通跳转
     *
     * @param current        当前
     * @param target         目标
     * @param attach         附带
     * @param isTargetReload 是否重载视图
     */
    public void toFrag(Class current, Class target, Object attach, boolean isTargetReload) {
        if (current == null | target == null) {
            toast(activity.getString(R.string.NULL_TIP), 5000);
            return;
        }
        try {
            RootMAActivity activity = (RootMAActivity) getActivity();
            if (activity != null) {
                whichFragmentStart = current.getSimpleName();
                activity.toFrag(current, target, attach, isTargetReload);
            } else {
                Lgg.t(Cons.TAG).ee("RootHiber--> toFrag() error: RootMAActivity is null");
            }
        } catch (Exception e) {
            Lgg.t(Cons.TAG).ee("Rootfrag error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 同module + 同Activity: 普通跳转(延迟)
     *
     * @param current        当前
     * @param target         目标
     * @param attach         附带
     * @param isTargetReload 是否重载视图
     * @param delayMilis     延迟毫秒数
     */
    public void toFrag(Class current, Class target, Object attach, boolean isTargetReload, int delayMilis) {
        if (current == null | target == null) {
            toast(activity.getString(R.string.NULL_TIP), 5000);
            return;
        }
        try {
            RootMAActivity activity = (RootMAActivity) getActivity();
            if (activity != null) {
                Thread ta = new Thread(() -> {
                    try {
                        Thread.sleep(delayMilis);
                        whichFragmentStart = current.getSimpleName();
                        activity.runOnUiThread(() -> activity.toFrag(current, target, attach, isTargetReload));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                ta.start();

            } else {
                Lgg.t(Cons.TAG).ee("RootHiber--> toFrag() error: RootMAActivity is null");
            }
        } catch (Exception e) {
            Lgg.t(Cons.TAG).ee("Rootfrag error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 同module + 不同Activity 跳转
     *
     * @param current        当前的fragment
     * @param targetAC       目标Activity
     * @param target         目标fragment
     * @param attach         附件
     * @param isTargetReload 是否重载目标fragment
     */
    public void toFragActivity(Class current, Class targetAC, Class target, Object attach, boolean isTargetReload) {
        if (current == null | targetAC == null | target == null) {
            toast(activity.getString(R.string.NULL_TIP), 5000);
            return;
        }
        SkipBean skipbean = getSkipbean(current, targetAC, target, attach, isTargetReload, false);
        RootHelper.toActivityImplicit(activity, skipbean.getTargetActivityClassName(), false, false, false, 0, skipbean);
    }

    /**
     * 同module + 不同Activity 跳转(带延时和自定义结束当前)
     *
     * @param current        当前的fragment
     * @param targetAC       目标Activity
     * @param target         目标fragment
     * @param attach         附件
     * @param isTargetReload 是否重载目标fragment
     * @param isFinish       是否结束当前AC
     * @param delay          延迟毫秒数
     */
    public void toFragActivity(Class current, Class targetAC, Class target, Object attach, boolean isTargetReload, boolean isFinish, int delay) {
        if (current == null | targetAC == null | target == null) {
            toast(activity.getString(R.string.NULL_TIP), 5000);
            return;
        }
        SkipBean skipbean = getSkipbean(current, targetAC, target, attach, isTargetReload, isFinish);
        RootHelper.toActivityImplicit(activity, skipbean.getTargetActivityClassName(), false, isFinish, false, delay, skipbean);
    }

    /**
     * 不同module + 不同Activity 跳转
     *
     * @param current        当前的fragment
     * @param activityClass  目标Activity的action
     * @param target         目标fragment
     * @param attach         附件
     * @param isTargetReload 是否重载目标fragment
     */
    public void toFragModule(Class current, String activityClass, String target, Object attach, boolean isTargetReload) {
        if (current == null | activityClass == null | target == null) {
            toast(activity.getString(R.string.NULL_TIP), 5000);
            return;
        }
        SkipBean skipbean = new SkipBean();
        skipbean.setCurrentFragmentClassName(current.getName());
        skipbean.setTargetActivityClassName(activityClass);
        skipbean.setTargetFragmentClassName(target);
        skipbean.setAttach(attach);
        skipbean.setTargetReload(isTargetReload);
        skipbean.setCurrentACFinish(false);
        RootHelper.toActivityImplicit(activity, activityClass, false, false, false, 0, skipbean);
    }

    /**
     * 不同module + 不同Activity 跳转 (带延时和自定义结束当前)
     *
     * @param current        当前的fragment
     * @param activityClass  目标Activity的action
     * @param target         目标fragment
     * @param attach         附件
     * @param isTargetReload 是否重载目标fragment
     * @param isFinish       是否结束当前AC
     * @param delay          延迟毫秒数
     */
    public void toFragModule(Class current, String activityClass, String target, Object attach, boolean isTargetReload, boolean isFinish, int delay) {
        if (current == null | activityClass == null | target == null) {
            toast(activity.getString(R.string.NULL_TIP), 5000);
            return;
        }
        SkipBean skipbean = new SkipBean();
        skipbean.setCurrentFragmentClassName(current.getName());
        skipbean.setTargetActivityClassName(activityClass);
        skipbean.setTargetFragmentClassName(target);
        skipbean.setAttach(attach);
        skipbean.setTargetReload(isTargetReload);
        skipbean.setCurrentACFinish(false);
        RootHelper.toActivityImplicit(activity, activityClass, false, isFinish, false, delay, skipbean);
    }

    /**
     * 结束当前Activit
     */
    public void finishActivity() {
        RootMAActivity activity = (RootMAActivity) getActivity();
        if (activity != null) {
            activity.finish();
        } else {
            Lgg.t(Cons.TAG).ee("RootHiber--> finish() error: RootMAActivity is null");
        }
    }

    /**
     * 杀死APP
     */
    public void kill() {
        RootMAActivity activity = (RootMAActivity) getActivity();
        if (activity != null) {
            activity.kill();
        } else {
            Lgg.t(Cons.TAG).ee("RootHiber--> kill() error: RootMAActivity is null");
        }
    }

    /**
     * 吐司提示
     *
     * @param tip      提示
     * @param duration 时长
     */
    public void toast(String tip, int duration) {
        RootMAActivity activity = (RootMAActivity) getActivity();
        if (activity != null) {
            activity.toast(tip, duration);
        } else {
            Lgg.t(Cons.TAG).ee("RootHiber--> toast() error: RootMAActivity is null");
        }
    }

    /**
     * 吐司提示
     *
     * @param tip      提示
     * @param duration 时长
     */
    public void toast(@StringRes int tip, int duration) {
        RootMAActivity activity = (RootMAActivity) getActivity();
        if (activity != null) {
            activity.toast(tip, duration);
        } else {
            Lgg.t(Cons.TAG).ee("RootHiber--> toast() error: RootMAActivity is null");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /* -------------------------------------------- impl -------------------------------------------- */

    /**
     * 设置权限监听接口
     *
     * @param permissedListener 权限监听接口
     */
    public void setPermissedListener(PermissedListener permissedListener) {
        this.permissedListener = permissedListener;
    }

    /* -------------------------------------------- private -------------------------------------------- */

    /**
     * 封装skipbean
     *
     * @param current        当前的fragment
     * @param activityClass  目标Activity的action
     * @param target         目标Fragment
     * @param attach         附件
     * @param isTargetReload 是否重载对象Fragment
     * @param isFinish       是否结束当前Activity
     * @return skipbean
     */
    private SkipBean getSkipbean(Class current, Class activityClass, Class target, Object attach, boolean isTargetReload, boolean isFinish) {
        SkipBean skipBean = new SkipBean();

        boolean isCurrentFrag = Fragment.class.isAssignableFrom(current);
        boolean isTargetFrag = Fragment.class.isAssignableFrom(target);
        if (isCurrentFrag & isTargetFrag) {
            skipBean.setCurrentFragmentClassName(current.getName());
            skipBean.setTargetFragmentClassName(target.getName());

        } else if (isCurrentFrag & !isTargetFrag) {
            skipBean.setCurrentFragmentClassName(current.getName());
            skipBean.setTargetFragmentClassName(current.getName());

        } else if (!isCurrentFrag & isTargetFrag) {
            skipBean.setCurrentFragmentClassName(getClass().getName());
            skipBean.setTargetFragmentClassName(target.getName());

        } else {
            skipBean.setCurrentFragmentClassName(getClass().getName());
            skipBean.setTargetFragmentClassName(getClass().getName());

        }

        skipBean.setTargetActivityClassName(activityClass.getName());
        skipBean.setAttach(attach);
        skipBean.setTargetReload(isTargetReload);
        skipBean.setCurrentACFinish(isFinish);
        return skipBean;
    }
}
