package myapp.anhtu.com.checkspell.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import myapp.anhtu.com.checkspell.R;
import myapp.anhtu.com.checkspell.entity.SearchResultAdapter;

public class SearchResult extends AppCompatActivity {

    private ListView lv;
    private SearchResultAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        //
        ArrayList<String> arr = getIntent().getStringArrayListExtra("listRe");
        lv = (ListView)findViewById(R.id.listResult);
        adapter = new SearchResultAdapter(SearchResult.this,R.layout.search_result,arr);
        lv.setAdapter(adapter);
    }
}
