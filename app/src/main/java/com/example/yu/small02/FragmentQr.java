package com.example.yu.small02;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class FragmentQr extends Fragment {

    private SharedPreferences sharedPreferences;
    private ImageView mimageView;
    private Button button;
    private SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qr,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences=getContext().getSharedPreferences("Mydata",0);
        editor=sharedPreferences.edit();

        mimageView=view.findViewById(R.id.image);
        button=view.findViewById(R.id.exit);
        //点击退出登录
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),MainActivity.class);
                //改变sharedpreferences中的数据
                editor.putBoolean("isCheck_L",false);
                editor.commit();
                startActivity(intent);
            }
        });


        String name = sharedPreferences.getString("name", null);
        QRTask qrTask = new QRTask(getContext(), mimageView);
        qrTask.execute(name);
    }

    static class QRTask extends AsyncTask<String,Void,Bitmap>{

        private WeakReference<Context> mContext;
        private WeakReference<ImageView> mimageView;

        public QRTask(Context mContext, ImageView mimageView) {
            this.mContext = new WeakReference<>(mContext);
            this.mimageView =new WeakReference<>(mimageView);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String str=strings[0];
            if(TextUtils.isEmpty(str)){
                return null;
            }
            int size=200;
            return QRCodeEncoder.syncEncodeQRCode(str,size);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap!=null){
                mimageView.get().setImageBitmap(bitmap);
            }
        }
    }
}
