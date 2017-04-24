package myapp.anhtu.com.checkspell.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import myapp.anhtu.com.checkspell.R;
import myapp.anhtu.com.checkspell.entity.Page;

public class PageActivity extends AppCompatActivity {

    private ArrayList<Page> arr;
    private static final String TAG = "PageActivity";
    TextView txtContent, txtPageNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        Intent intent = getIntent();
        int pageNum = intent.getIntExtra("pageNum",0);
        String path = intent.getStringExtra("path");
        readFile(path);
        txtContent = (TextView) findViewById(R.id.txtContent);
        txtPageNum = (TextView) findViewById(R.id.txtPageNum);
        txtContent.setText(arr.get(pageNum-1).getContent());
        txtPageNum.setText(String.valueOf(pageNum));
    }

    protected ArrayList<Page> readFile(String path){
        arr = new ArrayList<>();
        int i = 0; //line
        int j = 0; //page number
        File file = new File(path);
        if(file.exists()){
            StringBuilder content = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    i++;
                    content.append(line);
                    content.append("\n");
                    // add page
                    if(i>=30){
                        j++;
                        Page page = new Page(String.valueOf(content),j);
                        arr.add(page);
                        i = 0;
                        content.setLength(0);
                    }
                }
                br.close();
                j++;
                Page page = new Page(String.valueOf(content),j);
                arr.add(page);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG,"File not exists!");
        }
        return arr;
    }
}
