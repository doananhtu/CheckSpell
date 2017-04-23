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
 * Created by anhtu on 3/25/2017.
 */

public class ContentAdapter extends ArrayAdapter<Page>{

    public ContentAdapter(Context context, int resource) {
        super(context, resource);
    }

    public ContentAdapter(Context context, int resource, List<Page> item) {
        super(context, resource, item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater vi;
            vi =LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.page,null);
        }
        Page page = getItem(position);
        if(page != null){
            TextView txtContentMain = (TextView) v.findViewById(R.id.txtContentMain);
            TextView txtPageNumber = (TextView) v.findViewById(R.id.txtPageNumber);
            txtContentMain.setText(page.getContent());
            txtPageNumber.setText(String.valueOf(page.getPageNumber()));
        }
        return v;
    }
}
