package com.alfredjin.iamrobot.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alfredjin.iamrobot.R;
import com.alfredjin.iamrobot.bean.StudyResult;

import java.util.List;


/**
 * Created by AlfredJin on 2017/4/20.
 */

public class StudyAdapter extends BaseAdapter {

    private Context mContext;
    private List<StudyResult> list;

    public StudyAdapter(Context mContext, List<StudyResult> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StudyResult result = list.get(position);
        StudyViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new StudyViewHolder();
            convertView = View.inflate(mContext, R.layout.item_study, null);
            viewHolder.tvQue = (TextView) convertView.findViewById(R.id.tv_study_question);
            viewHolder.tvAns = (TextView) convertView.findViewById(R.id.tv_study_answer);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (StudyViewHolder) convertView.getTag();
        }
        viewHolder.tvQue.setText(result.getQuestion());
        viewHolder.tvAns.setText(result.getAnswer());
        return convertView;
    }

    private class StudyViewHolder {
        TextView tvQue;
        TextView tvAns;
    }
}
