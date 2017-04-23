package myapp.anhtu.com.checkspell.entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import myapp.anhtu.com.checkspell.R;

/**
 * Created by anhtu on 4/22/2017.
 */

public class SpellResultAdapter  extends ArrayAdapter<Result> {
    public SpellResultAdapter(Context context, int resource) {
        super(context, resource);
    }

    public SpellResultAdapter(Context context, int resource, List<Result> item) {
        super(context, resource, item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater vi;
            vi =LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.search_result,null);
        }
        Result result = getItem(position);
        if(result != null){
            TextView txtSearchResult =(TextView)v.findViewById(R.id.txtSearchResult);
            TextView txtPageNum = (TextView)v.findViewById(R.id.txtPageNum);
            txtSearchResult.setText(result.getContent());
            txtPageNum.setText(String.valueOf(result.getPageNumber()));
        }
        return v;
    }
}
