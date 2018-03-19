package com.gingold.basisglidelibrary;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.Target;

import java.io.File;

/**
 * BasisGlideUtils
 */

public class BasisGlideUtils {
    private static final int DEFAULT = 252;
    private static final int CONTEXT = 52;
    private static final int ACTIVITY = 53;
    private static final int FRAGMENT = 54;
    private static final int FRAGMENTAPP = 55;
    private static final int FRAGMENTACTIVITY = 56;

    public static final int CROPCIRCLE = 58;

    /**
     * @param context 支持类型: Context, Activity, Fragment, FragmentActivity
     */
    public static void load(Object context, Object model, ImageView view) {
        build(context, model).into(view);
    }

    /**
     * @param context 支持类型: Context, Activity, Fragment, FragmentActivity
     */
    public static void loadSpecial(Object context, Object model, ImageView view, Context activity, int special) {
        switch (special) {
            case CROPCIRCLE:
//                options.transform(new CropCircleTransformation(activity));
//                options.bitmapTransform(new CropCircleTransformation(activity));
                break;
        }
        build(context, model).into(view);
//        GlideApp.with(activity).load(model).transform(new CropCircleTransformation(activity)).into(view);
    }

    /**
     * @param context 支持类型: Context, Activity, Fragment, FragmentActivity
     */
    public static void loadWithError(Object context, Object model, ImageView view, Drawable placeholder) {
        build(context, model).error(placeholder).into(view);
    }

    /**
     * @param context 支持类型: Context, Activity, Fragment, FragmentActivity
     */
    public static void loadWithError(Object context, Object model, ImageView view, int placeholderId) {
        build(context, model).error(placeholderId).into(view);
    }

    /**
     * @param context  支持类型: Context, Activity, Fragment, FragmentActivity
     * @param duration 过渡动画时长
     */
    public static void load(Object context, Object model, ImageView view, int duration) {
        build(context, model)
                .into(view);
    }

    /**
     * @param context        支持类型: Context, Activity, Fragment, FragmentActivity
     * @param sizeMultiplier 请求给定系数的缩略图
     */
    public static void load(Object context, Object model, ImageView view, float sizeMultiplier) {
        build(context, model)
                .thumbnail(sizeMultiplier)
                .into(view);
    }

    /**
     * @param context        支持类型: Context, Activity, Fragment, FragmentActivity
     * @param duration       过渡动画时长
     * @param sizeMultiplier 请求给定系数的缩略图
     */
    public static void load(Object context, Object model, ImageView view, int duration, float sizeMultiplier) {
        build(context, model)
                .thumbnail(sizeMultiplier)
                .into(view);
    }

    /**
     * @param context          支持类型: Context, Activity, Fragment, FragmentActivity
     * @param thumbnailRequest 缩略图图片资源
     */
    public static void load(Object context, Object model, ImageView view, Object thumbnailRequest, boolean isDrawable) {
        build(context, model)
                .thumbnail(build(context, thumbnailRequest))
                .into(view);
    }

    /**
     * @param context          支持类型: Context, Activity, Fragment, FragmentActivity
     * @param duration         过渡动画时长
     * @param thumbnailRequest 缩略图图片资源
     */
    public static void load(Object context, Object model, ImageView view, int duration, Object thumbnailRequest, boolean isDrawable) {
        build(context, model)
                .thumbnail(build(context, thumbnailRequest))
                .into(view);
    }

    /**
     * @param context 支持类型: Context, Activity, Fragment, FragmentActivity
     */
    public static void loadAsGif(Object context, Object model, ImageView view) {
        with(context).load(model).asGif().into(view);
    }

    /**
     * 获取Glide磁盘缓存大小
     */
    public static String getCacheSize(Context context) {
        return BasisGlideCacheUtil.getInstance().getCacheSize(context);
    }

    /**
     * 清除图片磁盘缓存
     */
    public static boolean clearDiskCacheSelf(final Context context) {
        return BasisGlideCacheUtil.getInstance().clearDiskCacheSelf(context);
    }

    /**
     * 清除内存缓存
     */
    public static boolean clearCacheMemory(final Context context) {
        return BasisGlideCacheUtil.getInstance().clearCacheMemory(context);
    }

    /**
     * 清除所有缓存
     */
    public static boolean clearAllCacheMemory(final Context context) {
        return clearCacheMemory(context) && clearDiskCacheSelf(context);
    }

    /**
     * 下载图片
     */
    public static void downloadPic(final Context context, final String url, final String fileName, final BasisCallBack callBack) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Bitmap downloadBitmap = Glide
                            .with(context)
                            .load(url)
                            .asBitmap()
                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();

                    GlideDownLoadPicUtil.downLoadPic(context, downloadBitmap, fileName, callBack);
                } catch (Exception e) {
                    e.printStackTrace();
                    GlideDownLoadPicUtil.failure(callBack);
                }
            }
        }.start();
    }

    /**
     * 下载gif图片
     */
    public static void downloadGif(final Context context, final String url, final String fileName, final BasisCallBack callBack) {
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = Glide
                            .with(context)
                            .load(url)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();

                    GlideDownLoadPicUtil.downLoadGif(context, file, fileName, callBack);
                } catch (Exception e) {
                    e.printStackTrace();
                    GlideDownLoadPicUtil.failure(callBack);
                }
            }
        }.start();
    }

    /**
     * 获取上下文类型
     */
    private static int getState(Object context) {
        if (context instanceof Activity) {
            return ACTIVITY;
        } else if (context instanceof Fragment) {
            return FRAGMENT;
        } else if (context instanceof android.app.Fragment) {
            return FRAGMENTAPP;
        } else if (context instanceof FragmentActivity) {
            return FRAGMENTACTIVITY;
        } else if (context instanceof Context) {
            return CONTEXT;
        }
        return DEFAULT;
    }

    /**
     * 根据不同上下文类型返回结果
     */
    public static RequestManager with(Object context) {
        int withState = getState(context);
        if (withState == CONTEXT) {
            return Glide.with((Context) context);
        } else if (withState == ACTIVITY) {
            return Glide.with((Activity) context);
        } else if (withState == FRAGMENT) {
            return Glide.with((Fragment) context);
        } else if (withState == FRAGMENTAPP) {
            return Glide.with((android.app.Fragment) context);
        } else if (withState == FRAGMENTACTIVITY) {
            return Glide.with((FragmentActivity) context);
        }
        throw new IllegalArgumentException("非法的上下文参数!");
    }

    public static DrawableTypeRequest<Object> build(Object context, Object model) {
        return with(context).load(model);
    }

}
