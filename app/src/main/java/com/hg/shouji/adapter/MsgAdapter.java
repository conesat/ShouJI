package com.hg.shouji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.hg.shouji.R;
import com.hg.shouji.entity.Msg;

import java.util.ArrayList;
import java.util.List;

public class MsgAdapter extends BaseAdapter {

    private List<Msg> list=new ArrayList<>();//信息数据

    private LayoutInflater layoutInflater;

    public MsgAdapter(List<Msg> list, Context context){
        this.list=list;
        this.layoutInflater= LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.msg_layout,null);
            TextView time=(TextView)convertView.findViewById(R.id.msg_time);
            TextView title=(TextView)convertView.findViewById(R.id.msg_title);
            TextView context=(TextView)convertView.findViewById(R.id.msg_context);
            time.setText("12/12"+position);
            title.setText("标题标题标题"+position);
            context.setText("内容内容内容"+position);
        }
        return convertView;
    }
}
