package bwie.com.zhenshuaai76.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.HashMap;

import bwie.com.zhenshuaai76.api.Api;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * date:2019/7/6
 * name:windy
 * function:
 */
public class HttpUtils {
    private static HttpUtils getInstance;
    private final OkHttpClient client;
    private final Retrofit retrofit;

    private HttpUtils(){
        client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Api.BASE_URL)
                .client(client)
                .build();
    }
    public boolean isNet(Context context){
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info!=null){
            return info.isAvailable();
        }
        return false;
    }
    public static HttpUtils httpUtils(){
        if (getInstance==null){
            synchronized (HttpUtils.class){
                if (getInstance==null){
                    getInstance=new HttpUtils();
                }
            }
        }
        return getInstance;
    }
    public <T>T creat(Class<T>clz){
        return retrofit.create(clz);
    }
}
