package myapp.anhtu.com.checkspell.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        Collections.reverse(arr);
        arr.add(new Result("Số kết quả tìm thấy:",arr.size()));
        Collections.reverse(arr);
        lv = (ListView)findViewById(R.id.listResult);
        adapter = new SearchResultAdapter(SearchResultActivity.this,R.layout.search_result,arr);
        lv.setAdapter(adapter);
    }
}
