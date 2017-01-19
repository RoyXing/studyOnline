package com.study.online.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.online.R;
import com.study.online.bean.SlideMenuItem;


/**
 * @description 这个是菜单界面的适配器
 * @version 1.0
 * @author 庞品
 * @date 2015-5-25 上午11:35:33
 */
public class SlideMenuAdapter extends ArrayAdapter<SlideMenuItem> {

    //寻找布局
    LayoutInflater layoutInflater;
    //对应listview的id
    private int textViewResourceId;
    public SlideMenuAdapter(Context context,
                            int textViewResourceId, List<SlideMenuItem> objects) {
        super(context,  textViewResourceId, objects);
        this.textViewResourceId=textViewResourceId;
        layoutInflater=LayoutInflater.from(context);

    }
    //获取视图
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SlideMenuItem menu_listview=getItem(position);

        ViewHolder viewHolder;
        if(convertView==null){
            convertView=layoutInflater.inflate(textViewResourceId, null);
            viewHolder=new ViewHolder();
            viewHolder.menu_imageView=(ImageView) convertView.findViewById(R.id.listivew_itme_img);
            viewHolder.menu_textView=(TextView) convertView.findViewById(R.id.listview_itme_text);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();//重新获取viewHolder
        }
        viewHolder.menu_imageView.setImageResource(menu_listview.getItme_img());
        viewHolder.menu_textView.setText(menu_listview.getItme_text());
        return convertView;
    }
    //该类用来优化listview的加载
    class ViewHolder{
        ImageView menu_imageView;
        TextView menu_textView;
    }



}
