package com.example.yu.small02.presenter;

import android.util.Log;

import com.example.yu.small02.callback.MyCallBack;
import com.example.yu.small02.model.Imodelmpl;
import com.example.yu.small02.view.IView;

public class IpresenterImpl implements Ipresenter {

    IView iView;
    Imodelmpl imodelmpl;

    public IpresenterImpl(IView iView) {
        this.iView=iView;
        imodelmpl=new Imodelmpl();
    }

    @Override
    public void startRequest(String str, Class claz) {
        imodelmpl.startRequestData(str, claz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                Log.i("TEST",data.toString());
                iView.setData(data);
            }
        });
    }
    public void onDetach(){
        if(iView!=null){
            iView=null;
        }
        if(imodelmpl!=null){
            iView=null;
        }
    }
}
