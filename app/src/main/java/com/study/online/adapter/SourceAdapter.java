package com.study.online.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.study.online.R;
import com.study.online.bean.Source;

import java.util.List;

/**
 * Created by roy on 2017/1/17.
 */

public class SourceAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Source> data;
    private Context mContext;

    public SourceAdapter(Context context, List<Source> datas) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.data = datas;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.fragment_layout_resource_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(mContext).load(getBean(position).getIcon()).into(holder.source_image);

        holder.source_name.setText(getBean(position).getName());
        holder.source_type.setText(getBean(position).getType());
        holder.source_desc.setText(getBean(position).getDesc());

        return convertView;
    }

    private Source getBean(int position) {
        return data.get(position);
    }

    public List<Source> getData() {
        return data;
    }

    public void setData(List<Source> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ViewHolder {

        public TextView source_name;
        public TextView source_type;
        public ImageView source_image;
        public TextView source_desc;

        public ViewHolder(View rootView) {
            this.source_name = (TextView) rootView.findViewById(R.id.source_name);
            this.source_type = (TextView) rootView.findViewById(R.id.source_type);
            this.source_image = (ImageView) rootView.findViewById(R.id.source_image);
            this.source_desc = (TextView) rootView.findViewById(R.id.source_desc);
        }

    }
}
