package com.study.online.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private List<KnowledgeBean> data;
    private LayoutInflater mInflater;
    private RecyclerViewListener onItemClickListener;

    public interface RecyclerViewListener {
        void onItemClickListener(View view, int position);
    }

    public void setOnItemClickListener(RecyclerViewListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CourseRecyclerAdapter(Context context, List<KnowledgeBean> data) {
        mInflater = LayoutInflater.from(context);
        this.data = data;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.fragment_layout_course_item, null));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.course_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                onItemClickListener.onItemClickListener(v, pos);
            }
        });

        Picasso.with(mContext).load(getBean(position).getImages()).into(holder.course_pic);
        holder.course_name.setText(getBean(position).getName());
//        holder.course_author.setText("作 者：" + getBean(position).getAuthor());
//        holder.course_publishing.setText("产 商：" + getBean(position).getPublishing());
//        holder.course_desc.setText("描 述：" + getBean(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout course_linear;
        ImageView course_pic;
        TextView course_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.course_linear = (LinearLayout) itemView.findViewById(R.id.course_linear);
            this.course_pic = (ImageView) itemView.findViewById(R.id.course_pic);
            this.course_name = (TextView) itemView.findViewById(R.id.course_name);
        }
    }

    public KnowledgeBean getBean(int position) {
        return data.get(position);
    }

    public void setData(List<KnowledgeBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<KnowledgeBean> getData() {
        return data;
    }
}
