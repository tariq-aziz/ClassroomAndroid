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
public class ClassroomAdapter extends BaseAdapter{

    private Context mContext;
    private List<Classroom> mClassrooms;

    public ClassroomAdapter(Context context, List<Classroom> classrooms){
        mContext = context;
        mClassrooms = classrooms;
    }

    @Override
    public int getCount() {
        return mClassrooms.size();
    }

    @Override
    public Object getItem(int position) {
        return mClassrooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Classroom classroom = (Classroom) getItem(position);

        ViewHolder holder;
        //convertView: reused view sent for enhanced performance
        if(convertView==null){
            //inflate layout to obtain the View of a single row
            convertView = LayoutInflater.from(mContext).inflate(R.layout.classroom_item_layout, parent, false);
            //if convertView is null, initialize viewHolder and store within convertView
            holder = new ViewHolder();
            holder.courseCodeTextView = (TextView) convertView.findViewById(R.id.course_code_text_view);
            holder.profNameTextView = (TextView)convertView.findViewById(R.id.prof_name_text_view);
            convertView.setTag(holder);
        }

        else{
            //viewHolder already initialized, access via convertView
            holder = (ViewHolder) convertView.getTag();
        }

        holder.courseCodeTextView.setText(classroom.getCourseCode());
        holder.profNameTextView.setText(classroom.getProfName());


        return convertView;
    }


    static class ViewHolder {
        TextView courseCodeTextView;
        TextView profNameTextView;
    }

}
