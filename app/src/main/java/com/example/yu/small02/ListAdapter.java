package com.example.yu.small02;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yu.small02.model.NewBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private List<NewBean.DataBean> list;

    public ListAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<NewBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public void addList(List<NewBean.DataBean> mlist){
        list.addAll(mlist);
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getThumbnail_pic_s03()==null?0:1;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public NewBean.DataBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder holder;
        if(convertView==null){
            holder=new Viewholder();
            convertView=LayoutInflater.from(context).inflate(getItemViewType(position)==1?R.layout.fragment_new_item:R.layout.fragment_news_item,parent,false);
            holder.title=convertView.findViewById(R.id.title);
            holder.imageView01=convertView.findViewById(R.id.image01);
            holder.imageView02=convertView.findViewById(R.id.image02);
            holder.imageView03=convertView.findViewById(R.id.image03);
            convertView.setTag(holder);
        }else{
            holder= (Viewholder) convertView.getTag();
        }
        holder.title.setText(getItem(position).getTitle());
        ImageLoader.getInstance().displayImage(getItem(position).getThumbnail_pic_s(),holder.imageView01);
        if(holder.imageView03!=null){
            ImageLoader.getInstance().displayImage(getItem(position).getThumbnail_pic_s02(),holder.imageView02);
            ImageLoader.getInstance().displayImage(getItem(position).getThumbnail_pic_s03(),holder.imageView03);
        }
        return convertView;
    }
    class Viewholder{
        TextView title;
        ImageView imageView01,imageView02,imageView03;
    }
}
