package xsda.xsda.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

import com.alibaba.fastjson.JSONObject;
import com.hiber.tools.RootEncrypt;

import org.xutils.common.util.MD5;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

import xsda.xsda.bean.LoginBean;

/**
 * Created by qianli.ma on 2018/6/22 0022.
 */

public class Ogg {

    /**
     * 把流转换成字符
     *
     * @param ins 原始流
     * @return 字符
     */
    public static String streamToString(InputStream ins) {
        byte[] b = new byte[1024];
        StringBuilder sb = new StringBuilder();
        int len;
        try {
            while ((len = ins.read(b)) != -1) {
                sb.append(new String(b, 0, len));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 获取本地版本号
     *
     * @param context
     * @return 版本号整型
     */
    public static int getLocalVersion(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            // 00_从上下文中获取包管理者 --> 获取当前包用getPackageName
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            // 01_获取包信息中的版本号
            int versionCode = packageInfo.versionCode;
            // 02_返回版本号
            return versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 把date转换成字符串
     *
     * @param date date
     * @return 2018-01-05 16:28:36
     */
    public static String turnDateToString(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sif = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sif.format(date);
    }


    /**
     * byte转file
     *
     * @param bytes    数据源
     * @param dirPath  存放的目录
     * @param fileName 希望转换的文件名
     * @return 转换后的文件对象
     */
    public static File byte2File(byte[] bytes, String dirPath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(dirPath);
            if (!dir.exists() | !dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(dirPath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 初始化创建「安装包目录」
     */
    public static void createInstallRootDir() {
        File sdcard = Environment.getExternalStorageDirectory();
        String installDirPath = sdcard.getAbsolutePath() + File.separator + Cons.INSTALL_FILEPATH;
        File installDir = new File(installDirPath);
        if (!installDir.exists() | !installDir.isDirectory()) {
            boolean isCreate = installDir.mkdir();
            Lgg.t(Cons.TAG).ii("init create the download dir: " + isCreate);
        }
    }

    /**
     * 查询需要安装的APK的信息
     *
     * @param context 环境
     * @param apkPath APK所在路径
     * @return 包信息
     */
    public static PackageInfo checkApkVersionInfo(Context context, String apkPath) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
    }

    /**
     * 获取
     *
     * @param leanCloudApkName 从leanCloud获取到的APK信息
     * @return 如果为null:没有下载过最新版本
     */
    public static String getLocalInstallApkPath(String leanCloudApkName) {
        File sdcard = Environment.getExternalStorageDirectory();
        String installDirPath = sdcard.getAbsolutePath() + File.separator + Cons.INSTALL_FILEPATH;
        File installDir = new File(installDirPath);
        if (!installDir.exists() | !installDir.isDirectory()) {// 不存在--> null
            boolean isCreate = installDir.mkdir();
            return null;
        } else {
            File[] files = installDir.listFiles();
            if (files.length <= 0) {// 空目录--> null
                return null;
            } else {
                for (File file : files) {
                    if (file.getName().contains(leanCloudApkName)) {
                        return file.getAbsolutePath();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 匹配手机号码
     *
     * @param phoneNum 手机号码
     * @return 是否匹配
     */
    public static boolean matchPhoneReg(String phoneNum) {
        String reg = "^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\\d{8}$";
        return Pattern.compile(reg).matcher(phoneNum).matches();
    }

    /**
     * 隐藏软键盘
     */
    public static void hideKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public static void showKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInputFromInputMethod(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * 获取设备唯一ID(可应对大部分设备, 除非设备被恢复出厂设置)
     *
     * @param context 环境
     * @return 设备唯一ID
     */
    public static String getDeviceId(Context context) {
        String id = MD5.md5(UUID.randomUUID().toString().replace("-", ""));
        Lgg.t(Cons.TAG).vv("id:" + id);
        return id;
    }

    /**
     * 保存登陆信息
     *
     * @param phoneNum   手机号
     * @param password   密码
     * @param isRemember 是否记住密码
     */
    public static void saveLoginJson(Context context, String phoneNum, String password, boolean isRemember) {
        LoginBean loginBean = new LoginBean();
        loginBean.setPhoneNum(phoneNum);
        loginBean.setPassword(password);
        loginBean.setRemember(isRemember);
        String loginJson = JSONObject.toJSONString(loginBean);
        loginJson = RootEncrypt.des_encrypt(loginJson);// 加密
        Sgg.getInstance(context).putString(Cons.SP_LOGIN_INFO, loginJson);
    }

    /**
     * 读取登陆信息
     *
     * @param context 上下文
     * @return loginbean
     */
    public static LoginBean readLoginJson(Context context) {
        String loginJson = Sgg.getInstance(context).getString(Cons.SP_LOGIN_INFO, "");
        loginJson = RootEncrypt.des_descrypt(loginJson);// 解密
        LoginBean loginBean;
        if (TextUtils.isEmpty(loginJson)) {
            loginBean = new LoginBean();
        } else {
            loginBean = JSONObject.parseObject(loginJson, LoginBean.class);
        }
        return loginBean;
    }

    /**
     * 是否符合自动登陆的前提条件
     *
     * @param context 上下文
     * @return true:符合
     */
    public static boolean isCanAutoLogin(Context context) {
        LoginBean loginBean = readLoginJson(context);
        if (loginBean.isRemember()) {
            boolean isMatchPhoneNum = !TextUtils.isEmpty(loginBean.getPhoneNum());
            boolean isMatchPassword = !TextUtils.isEmpty(loginBean.getPassword());
            return isMatchPhoneNum && isMatchPassword;
        }
        return false;
    }
}
