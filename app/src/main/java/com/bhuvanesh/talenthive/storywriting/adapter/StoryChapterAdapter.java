package com.bhuvanesh.talenthive.storywriting.adapter;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.storywriting.model.Chapter;
import com.bhuvanesh.talenthive.util.DateUtil;

import java.util.LinkedList;
import java.util.List;

public class StoryChapterAdapter extends BaseAdapter {

    private List<Chapter> mChapterList = new LinkedList<>();

    public void addItem(List<Chapter> chapterList) {
        mChapterList = chapterList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mChapterList != null ? mChapterList.size() : 0;
    }

    @Override
    public Chapter getItem(int position) {
        return mChapterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChapterViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_chapter, parent, false);
            holder = new ChapterViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChapterViewHolder) convertView.getTag();
        }
        Chapter item = mChapterList.get(position);
        holder.textViewChapterTitle.setText(TextUtils.isEmpty(item.chapterTitle) ?
                parent.getContext().getString(R.string.lbl_untitled_story_chapter) : item.chapterTitle);
        holder.textViewChapterDescription.setText(TextUtils.isEmpty(item.chapterDescription) ?
                parent.getContext().getString(R.string.lbl_no_description) : item.chapterDescription);
        holder.textViewLastModifiedDate.setText(DateUtil.getFormattedString(item.lastModifiedDate,
                DateUtil.DATE_TIME_FORMAT_TYPE_dd_MM_yyyy_HH_MM));
        holder.linearLayoutChapterItem.setBackgroundColor(item.isDeleted ?
                ContextCompat.getColor(parent.getContext(), R.color.color_semi_transparent_gray) : 0);

        return convertView;
    }

    private class ChapterViewHolder {
        private TextView textViewChapterTitle;
        private TextView textViewChapterDescription;
        private TextView textViewLastModifiedDate;
        private LinearLayout linearLayoutChapterItem;

        ChapterViewHolder(View itemView) {
            textViewChapterTitle = (TextView) itemView.findViewById(R.id.textview_chapter_title);
            textViewChapterDescription = (TextView) itemView.findViewById(R.id.textview_chapter_description);
            textViewLastModifiedDate = (TextView) itemView.findViewById(R.id.textview_last_modified);
            linearLayoutChapterItem = (LinearLayout) itemView.findViewById(R.id.llayout_chapter_item);
        }
    }
}
