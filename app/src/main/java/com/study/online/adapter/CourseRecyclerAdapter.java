package com.study.online.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class CourseRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_HEADER = 0;
    private static final int ITEM_TYPE_CONTENT = 1;
    private int mHeaderCount = 1;
    private Context mContext;
    private List<KnowledgeBean> data;
    private LayoutInflater mInflater;
    private RecyclerViewListener onItemClickListener;
    private String description;

    public interface RecyclerViewListener {
        void onItemClickListener(View view, int position);
    }

    public void setOnItemClickListener(RecyclerViewListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CourseRecyclerAdapter(Context context, List<KnowledgeBean> data, String desc) {
        mInflater = LayoutInflater.from(context);
        this.data = data;
        this.mContext = context;
        this.description = desc;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE_HEADER;
        } else {
            return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return new MyHeaderViewHolder(mInflater.inflate(R.layout.layout_study_header, null));
        } else if (viewType == ITEM_TYPE_CONTENT) {
            return new MyViewHolder(mInflater.inflate(R.layout.fragment_layout_course_item, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == ITEM_TYPE_HEADER) {
            ((MyHeaderViewHolder) holder).textView.setText("" + description);
        } else if (getItemViewType(position) == ITEM_TYPE_CONTENT) {
            ((MyViewHolder) holder).course_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition() - 1;
                    Log.e("roy", pos + "");
                    onItemClickListener.onItemClickListener(v, pos);
                }
            });
            if (getItem(position).getImages() != null && !getItem(position).getImages().isEmpty())
                Picasso.with(mContext).load(getItem(position).getImages()).into(((MyViewHolder) holder).course_pic);
            ((MyViewHolder) holder).course_name.setText(getItem(position).getName() + "");
        }
    }

    public KnowledgeBean getItem(int position) {
        return data.get(position - mHeaderCount);
    }

    @Override
    public int getItemCount() {
        return data.size() + mHeaderCount;
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

    class MyHeaderViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyHeaderViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.header_textView);
        }
    }

    public void setData(List<KnowledgeBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<KnowledgeBean> getData() {
        return data;
    }

    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == ITEM_TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }
}
