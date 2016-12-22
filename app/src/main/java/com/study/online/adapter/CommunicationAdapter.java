package com.study.online.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.study.online.R;
import com.study.online.bean.CommunicationBean;
import com.study.online.view.RoundImageView;

import java.util.List;

/**
 * Created by roy on 2016/12/21.
 */

public class CommunicationAdapter extends ArrayAdapter<CommunicationBean> {
    private Context context;
    private List<CommunicationBean>list;
    public CommunicationAdapter(Context context, int resource, List<CommunicationBean> objects) {
        super(context, resource, objects);
        this.context=context;
        this.list=objects;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CommunicationHolder holder;
        if (convertView==null){
            holder=new CommunicationHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_communication,null);
            holder.articleCommit=(TextView) convertView.findViewById(R.id.article_commit);
            holder.articleTitle=(TextView) convertView.findViewById(R.id.article_title);
            holder.articleContent=(TextView) convertView.findViewById(R.id.article_content);
            holder.userName=(TextView) convertView.findViewById(R.id.username);
            holder.articleTime=(TextView) convertView.findViewById(R.id.article_time);
            holder.cardView=(CardView) convertView.findViewById(R.id.cardview);
            holder.userImg=(RoundImageView) convertView.findViewById(R.id.userimg);
            convertView.setTag(holder);
        }else {
            holder=(CommunicationHolder) convertView.getTag();
        }
        holder.userName.setText(list.get(position).getUserName());
        holder.articleTitle.setText(list.get(position).getTitle());
        holder.articleContent.setText(list.get(position).getContent());
        holder.articleCommit.setText(list.get(position).getCommit());
        holder.articleTime.setText(list.get(position).getTime());
        Picasso.with(context).load(list.get(position).getHeadurl()).into(holder.userImg);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"刷新了"+position,Toast.LENGTH_LONG).show();

            }
        });
        return convertView;
    }
    class CommunicationHolder{
        RoundImageView userImg;
        TextView userName,articleTitle,articleContent,articleCommit,articleTime;
        CardView cardView;
    }
}
