package com.smartpullup.smartpullup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class WorkoutListAdapter extends BaseAdapter {

    String [] result;
    WorkoutFragment context;
    int [] imageId;
    private static LayoutInflater inflater=null;

    public WorkoutListAdapter(WorkoutFragment workoutFragment, String[] prgmNameList, int[] prgmImages) {
    // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=workoutFragment;
        imageId=prgmImages;
        inflater = ( LayoutInflater )context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
    // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
    // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
    // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView titel_workout_textView;
        ImageView image_workout_imageView;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
    // TODO Auto-generated method stub
        Holder holder=new Holder();
        View view;
        view = inflater.inflate(R.layout.fragment_workout_list_item, null);

        holder.titel_workout_textView=(TextView) view.findViewById(R.id.titel_cardView);
        holder.image_workout_imageView=(ImageView) view.findViewById(R.id.workout_imageView);

        holder.titel_workout_textView.setText(result[position]);
        Picasso.with(context.getContext()).load(imageId[position]).into(holder.image_workout_imageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        // TODO Auto-generated method stub
                Toast.makeText(context.getContext(), "You Clicked " + result[position], Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

}
