package com.qichang.hfydemo.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author hfy
 * @description
 * @time 2020/4/24
 */
public class MGlide {
    /**
     * 带占位符的加载
     */
    public static void LoadwithPlaceHolder(Context context, Object url, int img, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().placeholder(img);
        Glide.with(context)
                .asBitmap()  // some .jpeg files are actually gif
                .load(url)
                .apply(requestOptions)
                .into(imageView);

    }

    /**
     * 带占位符的加载,以及加载错误图
     */
    public static void LoadwithPlaceHolderError(Context context, Object url, int img, int error, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().placeholder(img).error(error);
        Glide.with(context)
                .asBitmap()  // some .jpeg files are actually gif
                .load(url)
                .apply(requestOptions)
                .into(imageView);

    }

    /**
     * 带占位符的加载，的圆形图
     *
     * @param context
     * @param url
     * @param img       占位图
     * @param imageView
     */
    public static void LoadWithCircle(Context context, Object url, int img, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().placeholder(img).centerCrop();
        Glide.with(context)
                .asBitmap()  // some .jpeg files are actually gif
                .load(url)
                .apply(requestOptions)
                .into(imageView);

    }

    /**
     * 带占位符的加载,以及加载错误图，的圆形图
     *
     * @param context
     * @param url
     * @param img       占位图
     * @param error     错误图
     * @param imageView
     */
    public static void LoadWithCircle(Context context, Object url, int img, int error, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().placeholder(img).error(error).centerCrop();
        Glide.with(context)
                .asBitmap()  // some .jpeg files are actually gif
                .load(url)
                .apply(requestOptions)
                .into(imageView);

    }

    /**
     * 圆角矩形图片
     * 不建议使用
     */
    public static void LoadWithRoundCorner(Context context, Object url, ImageView imageView, int corners) {
        RequestOptions requestOptions = new RequestOptions()
                .transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners(corners)));
        Glide.with(context)
                .asBitmap()  // some .jpeg files are actually gif
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 带占位图， 的圆角矩形图片
     */
    public static void LoadWithRoundCorner(Context context, Object url, int img, ImageView imageView, int corners) {
        RequestOptions requestOptions = new RequestOptions().placeholder(img)
                .transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners(corners)));
        Glide.with(context)
                .asBitmap()  // some .jpeg files are actually gif
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 带占位图，错误图 的圆角矩形图片
     *
     * @param context
     * @param url
     * @param img
     * @param error
     * @param imageView
     * @param corners   圆角大小
     */
    public static void LoadWithRoundCorner(Context context, Object url, int img, int error, ImageView imageView, int corners) {
        RequestOptions requestOptions = new RequestOptions().placeholder(img).error(error)
                .transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners(corners)));
        Glide.with(context)
                .asBitmap()  // some .jpeg files are actually gif
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }
}
