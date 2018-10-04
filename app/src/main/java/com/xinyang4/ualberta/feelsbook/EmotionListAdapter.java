package com.xinyang4.ualberta.feelsbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.Comparator;
import java.util.List;

/**
 * Created by apple on 2018/9/29.
 */
/**
 * to show the emotion from the emotion class we use the array to store it.
 */
public class EmotionListAdapter extends ArrayAdapter<Emotion>{
    private int resourceLayout;
    private Context mContext;
    private int selected = -1;

    public EmotionListAdapter(Context context, int resource, List<Emotion> emotions) {
        super(context, resource, emotions);
        this.resourceLayout = resource;
        this.mContext = context;
    }
    /**
     * to get the position than get the view
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Emotion e = getItem(position);

        TextView title = v.findViewById(R.id.title);
        TextView timestamp = v.findViewById(R.id.timestamp);
        Button btn = v.findViewById(R.id.edit);
        TextView comment = v.findViewById(R.id.text_comment);
        CardView card  = v.findViewById(R.id.card);

        title.setText(e.getType());
        timestamp.setText(e.getFormatedDate());

        if (position == selected){
            btn.setVisibility(View.VISIBLE);
            comment.setVisibility(View.VISIBLE);
            card.setCardBackgroundColor(v.getResources().getColor(R.color.colorAccent));
            comment.setText(e.getComment());
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =  new Intent(getContext(),EditActivity.class);
                    intent.putExtra("position",position);
                    getContext().startActivity(intent);
                }
            });
        } else {
            btn.setVisibility(View.GONE);
            comment.setVisibility(View.GONE);
            card.setCardBackgroundColor(v.getResources().getColor(R.color.cardview_light_background));
        }

        return v;
    }

    /**
     * to count the emotion data.
     */
    @Override
    public void notifyDataSetChanged() {
        DataManager.getInstance(getContext()).getEmotions().sort(new Comparator<Emotion>() {
            @Override
            public int compare(Emotion emotion, Emotion t1) {
                return t1.getDate().compareTo(emotion.getDate());
            }
        });
        super.notifyDataSetChanged();
    }

    public void select(int selected) {
        this.selected = selected;
    }
}
