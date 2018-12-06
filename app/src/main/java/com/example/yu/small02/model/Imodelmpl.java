package com.example.yu.small02.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.example.yu.small02.callback.MyCallBack;
import com.example.yu.small02.util.NetUtil;
import com.google.gson.Gson;

import java.io.IOException;

public class Imodelmpl implements Imodel{
    @SuppressLint("StaticFieldLeak")
    @Override
    public void startRequestData(final String str, final Class clas, final MyCallBack myCallBack) {
        new AsyncTask<String, Void, Object>() {
            @Override
            protected Object doInBackground(String... strings) {
                try {
                    String string = NetUtil.getString(str);
                    Gson gson = new Gson();
                    Object json = gson.fromJson(string, clas);
                    Log.i("TEST",json.toString());
                    return json;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                myCallBack.onSuccess(o);
            }
        }.execute(str);
    }
}
