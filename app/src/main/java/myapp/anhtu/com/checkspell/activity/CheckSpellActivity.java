package myapp.anhtu.com.checkspell.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import myapp.anhtu.com.checkspell.R;
import myapp.anhtu.com.checkspell.entity.Result;
import myapp.anhtu.com.checkspell.entity.SpellResultAdapter;

public class CheckSpellActivity extends AppCompatActivity {

    private ListView lv;
    private SpellResultAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_spell);

        Intent intent = getIntent();
        ArrayList<Result> arr = (ArrayList<Result>) intent.getSerializableExtra("listResult");
        lv = (ListView)findViewById(R.id.listSpell);
        adapter = new SpellResultAdapter(CheckSpellActivity.this,R.layout.search_result,arr);
        lv.setAdapter(adapter);
    }
}
