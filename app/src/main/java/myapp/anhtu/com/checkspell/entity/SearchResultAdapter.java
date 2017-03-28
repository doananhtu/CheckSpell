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
 * Created by anhtu on 3/27/2017.
 */

public class SearchResultAdapter extends ArrayAdapter<String> {

    public SearchResultAdapter(Context context, int resource) {
        super(context, resource);
    }

    public SearchResultAdapter(Context context, int resource, List<String> item) {
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
        String result = getItem(position);
        if(result != null){
            TextView txtSearchResult =(TextView)v.findViewById(R.id.txtSearchResult);
            txtSearchResult.setText(result);
        }
        return v;
    }
}
