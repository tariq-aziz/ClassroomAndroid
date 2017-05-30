package com.aziz.tariq.classroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tariqaziz on 2016-10-14.
 */
public class MessageAdapter extends BaseAdapter{
    private Context mContext;
    private List<Message> mMessages;

    public MessageAdapter(Context context, List<Message> messages){
        mContext = context;
        mMessages = messages;
    }

    @Override
    public int getCount() {
        return mMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = (Message) getItem(position);

        ViewHolder holder;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item_layout, parent, false);
            holder = new ViewHolder();
            holder.messageTextView = (TextView) convertView.findViewById(R.id.message_text_text_view);
            holder.authorTextView = (TextView)convertView.findViewById(R.id.message_author_text_view);
            convertView.setTag(holder);
        }

        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.messageTextView.setText(message.getText());
        holder.authorTextView.setText(message.getAuthor());

        return convertView;
    }


    static class ViewHolder {
        TextView messageTextView;
        TextView authorTextView;
    }
}
