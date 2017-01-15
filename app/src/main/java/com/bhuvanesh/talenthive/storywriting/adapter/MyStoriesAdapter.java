package com.bhuvanesh.talenthive.storywriting.adapter;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.storywriting.model.Story;
import com.bhuvanesh.talenthive.util.DateUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by bhuvanesh on 15-01-2017.
 */

public class MyStoriesAdapter extends BaseAdapter {

    private List<Story> mList = new LinkedList<>();

    public void setData(List<Story> storyList) {
        mList = storyList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public Story getItem(int position) {
        return ((mList != null && position < mList.size()) ? mList.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_stories, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Story item = mList.get(position);
        holder.imageViewWrapper.setImageBitmap(BitmapFactory.decodeFile(mList.get(position).wrapperImageUrl));
        holder.textViewTitle.setText(mList.get(position).title);
        holder.textViewTimeStamp.setText(DateUtil.getFormattedString(mList.get(position).lastModifiedDate,
                DateUtil.DATE_TIME_FORMAT_TYPE_dd_MM_yyyy_HH_MM));
        return convertView;
    }

    private static class ViewHolder {
        private ImageView imageViewWrapper;
        private TextView textViewTitle;
        private TextView textViewTimeStamp;
        private CheckBox checkBoxSelection;

        ViewHolder(View itemView) {
            imageViewWrapper = (ImageView) itemView.findViewById(R.id.imageview_wrapper);
            textViewTitle = (TextView) itemView.findViewById(R.id.textview_story_title);
            textViewTimeStamp = (TextView) itemView.findViewById(R.id.textview_timestamp);
            checkBoxSelection = (CheckBox) itemView.findViewById(R.id.checkbox_selection);
        }
    }
}
