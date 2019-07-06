package bwie.com.zhenshuaai76.mvp;

import org.w3c.dom.Entity;

import java.util.HashMap;

import bwie.com.zhenshuaai76.entry.DataEntry;
import bwie.com.zhenshuaai76.mvp.Controller;
import bwie.com.zhenshuaai76.net.CallBack;
import okhttp3.MultipartBody;

/**
 * date:2019/7/6
 * name:windy
 * function:
 */
public class PresenterImpl implements Controller.Presenter {

    private Controller.Model model;
    private Controller.View view;
    @Override
    public void attach(Controller.View view) {
     this.view=view;
     model=new ModelImpl();
    }

    @Override
    public void pttach() {
      if (view!=null){
          view=null;
      }
      if (model!=null){
          model=null;
      }
      System.gc();
    }

    @Override
    public void getHttp(HashMap<String, String> parms, MultipartBody.Part file) {
      model.getDates(parms, file, new CallBack() {
          @Override
          public void onSuccess(DataEntry dataEntry) {
              view.getView(dataEntry);
          }
      });
    }
}
