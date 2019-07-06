package bwie.com.zhenshuaai76.mvp;

import org.w3c.dom.Entity;

import java.util.HashMap;

import bwie.com.zhenshuaai76.entry.DataEntry;
import bwie.com.zhenshuaai76.net.CallBack;
import okhttp3.Call;
import okhttp3.MultipartBody;

/**
 * date:2019/7/6
 * name:windy
 * function:
 */
public interface Controller {
     interface Model{
       void getDates(HashMap<String,String>parms, MultipartBody.Part file, CallBack back);
    }
     interface View{
       void getView(DataEntry dataEntry);
    }
     interface Presenter{
       void attach(View view);
       void pttach();
       void getHttp(HashMap<String,String>parms, MultipartBody.Part file);
    }
}

