package bwie.com.zhenshuaai76.api;

import java.util.HashMap;

import bwie.com.zhenshuaai76.entry.DataEntry;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * date:2019/7/6
 * name:windy
 * function:
 */
public interface ApiData {
    @Multipart
    @POST(Api.PICTURE_URL)
    Observable<DataEntry>getData(@HeaderMap HashMap<String,String>parms, @Part MultipartBody.Part file);
}
