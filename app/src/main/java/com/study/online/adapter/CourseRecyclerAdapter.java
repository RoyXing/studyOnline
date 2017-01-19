package com.study.online.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.study.online.R;
import com.study.online.bean.KnowledgeBean;

import java.util.List;

/**
 * Created by roy on 2017/1/10.
 */

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private List<KnowledgeBean.ResponseBean> data;
    private LayoutInflater mInflater;

    public CourseRecyclerAdapter(Context context, List<KnowledgeBean.ResponseBean> data) {
        mInflater = LayoutInflater.from(context);
        this.data = data;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.fragment_layout_course_item, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Picasso.with(mContext).load(getBean(position).getImages()).into(holder.course_pic);
        holder.course_name.setText("名称：" + getBean(position).getName());
        holder.course_author.setText("作 者：" + getBean(position).getAuthor());
        holder.course_publishing.setText("产商：" + getBean(position).getPublishing());
        holder.course_desc.setText("描 述：" + getBean(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView course_pic;
        TextView course_name;
        TextView course_author;
        TextView course_publishing;
        TextView course_desc;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.course_pic = (ImageView) itemView.findViewById(R.id.course_pic);
            this.course_name = (TextView) itemView.findViewById(R.id.course_name);
            this.course_author = (TextView) itemView.findViewById(R.id.course_author);
            this.course_publishing = (TextView) itemView.findViewById(R.id.course_publishing);
            this.course_desc = (TextView) itemView.findViewById(R.id.course_desc);
        }
    }

    public KnowledgeBean.ResponseBean getBean(int position) {
        return data.get(position);
    }

    public void setData(List<KnowledgeBean.ResponseBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}