package myapp.anhtu.com.checkspell.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

import myapp.anhtu.com.checkspell.R;
import myapp.anhtu.com.checkspell.database.ToHopAmGiua;
import myapp.anhtu.com.checkspell.entity.Result;
import myapp.anhtu.com.checkspell.entity.SpellResultAdapter;
import myapp.anhtu.com.checkspell.utility.CheckSpellUtils;

public class CheckSpellActivity extends AppCompatActivity {

    private ListView lv;
    private SpellResultAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_spell);

        Intent intent = getIntent();
        ArrayList<Result> arr = (ArrayList<Result>) intent.getSerializableExtra("listResult");
        Collections.reverse(arr);
        arr.add(new Result("Số kết quả tìm thấy:",arr.size()));
        Collections.reverse(arr);
        lv = (ListView)findViewById(R.id.listSpell);
        adapter = new SpellResultAdapter(CheckSpellActivity.this,R.layout.search_result,arr);
        lv.setAdapter(adapter);

        // Training
        final CheckSpellUtils checkSpell = new CheckSpellUtils();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Result item = (Result) parent.getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(CheckSpellActivity.this);
                builder.setTitle("Cảm ơn bạn vì đã góp phần xây dựng ứng dụng!");
                builder.setMessage("\""+item.getContent()+"\"\nBạn nghĩ rằng từ này đúng chính " +
                        "tả tiếng Việt? Hãy để tôi học hỏi từ bạn!");
                builder.setPositiveButton("Dạy tôi đi!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToHopAmGiua db = new ToHopAmGiua(CheckSpellActivity.this);
                        String str = checkSpell.addingData(item.getContent());
                        db.addAmGiua(str.toUpperCase());
                        adapter.remove(item);
                        adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Bỏ qua", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}
