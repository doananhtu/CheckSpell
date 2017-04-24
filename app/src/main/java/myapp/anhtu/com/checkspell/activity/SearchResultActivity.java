package myapp.anhtu.com.checkspell.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

import myapp.anhtu.com.checkspell.R;
import myapp.anhtu.com.checkspell.entity.Result;
import myapp.anhtu.com.checkspell.entity.SearchResultAdapter;

public class SearchResultActivity extends AppCompatActivity{

    private ListView lv;
    private SearchResultAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        //
        Intent intent = getIntent();
        ArrayList<Result> arr = (ArrayList<Result>) intent.getSerializableExtra("listResult");
        final String path = intent.getStringExtra("path");
        Collections.reverse(arr);
        arr.add(new Result("Số kết quả tìm thấy:",arr.size()));
        Collections.reverse(arr);
        lv = (ListView)findViewById(R.id.listResult);
        adapter = new SearchResultAdapter(SearchResultActivity.this,R.layout.search_result,arr);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Result item = (Result) parent.getItemAtPosition(position);
                Intent itentS = new Intent(SearchResultActivity.this,PageActivity.class);
                itentS.putExtra("pageNum",item.getPageNumber());
                itentS.putExtra("path",path);
                startActivity(itentS);
            }
        });
    }
}
