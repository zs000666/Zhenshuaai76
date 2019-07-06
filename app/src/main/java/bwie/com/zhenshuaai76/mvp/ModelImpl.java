package bwie.com.zhenshuaai76.mvp;

import org.w3c.dom.Entity;

import java.util.HashMap;

import bwie.com.zhenshuaai76.api.ApiData;
import bwie.com.zhenshuaai76.entry.DataEntry;
import bwie.com.zhenshuaai76.net.CallBack;
import bwie.com.zhenshuaai76.net.HttpUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

/**
 * date:2019/7/6
 * name:windy
 * function:
 */
public class ModelImpl implements Controller.Model{

    @Override
    public void getDates(HashMap<String, String> parms, MultipartBody.Part file, final CallBack back) {
        HttpUtils.httpUtils().creat(ApiData.class).getData(parms,file)
        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataEntry>() {
                    @Override
                    public void accept(DataEntry dataEntry) throws Exception {
                          back.onSuccess(dataEntry);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
