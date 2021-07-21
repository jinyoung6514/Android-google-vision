package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.opencsv.CSVReader;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SearchResult extends AppCompatActivity {

    private ArrayList<String> all_ingre = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        //region RecyclerView 활용
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //세로 레이아웃 활용
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        CSdataAdapter adapter = new CSdataAdapter();
        Intent intent = getIntent();
        String message = intent.getStringExtra("텍스트인식");
        Toast.makeText(getApplicationContext(),"아이템 선택 " + message, Toast.LENGTH_LONG).show();

        //CSV파일 읽기
        try {
            InputStreamReader is = new InputStreamReader(getResources().openRawResource(R.raw.result2));
            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            CSVReader read = new CSVReader(reader);
            String[] record = null;
            while ((record = read.readNext()) != null){
                record[2] = StringUtils.deleteWhitespace(record[2]);
                message = StringUtils.deleteWhitespace(message);
                if(StringUtils.containsIgnoreCase(record[2], message))
                {
                    switch (record[3]) {
                        case "verified":
                            adapter.addItem(new CSdata(record[2], R.drawable.grade0));
                            break;
                        case "1":
                            adapter.addItem(new CSdata(record[2], R.drawable.grade1));
                            break;
                        case "2":
                            adapter.addItem(new CSdata(record[2], R.drawable.grade2));
                            break;
                        case "3":
                            adapter.addItem(new CSdata(record[2], R.drawable.grade3));
                            break;
                        case "4":
                            adapter.addItem(new CSdata(record[2], R.drawable.grade4));
                            break;
                        case "5":
                            adapter.addItem(new CSdata(record[2], R.drawable.grade5));
                            break;
                        case "6":
                            adapter.addItem(new CSdata(record[2], R.drawable.grade6));
                            break;
                        case "7":
                            adapter.addItem(new CSdata(record[2], R.drawable.grade7));
                            break;
                        case "8":
                            adapter.addItem(new CSdata(record[2], R.drawable.grade8));
                            break;
                        case "9":
                            adapter.addItem(new CSdata(record[2], R.drawable.grade9));
                            break;
                    }
//                    all_ingre.add(record[4]);
                }

            }
//            for(int i = 0; i <record.length;i++)
//            {
//                adapter.addItem(new CSdata(record[i]));
//            }
        }catch (Exception e){
            e.printStackTrace();
        }

        recyclerView.setAdapter(adapter);

        //View클릭 시 상세정보창 이동
        adapter.setOnItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(CSdataAdapter.ViewHolder holder, View view, int position) {
                //클릭한 item의 position값(정보) 받아오기
                CSdata item = adapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(),MainContent.class);
                intent.putExtra("제품명",item.getName());
                // intent.putExtra("성분이름", all_ingre);
                startActivityForResult(intent,MainActivity.MAINCONTENT_CODE);

                Toast.makeText(getApplicationContext(),"아이템 선택 " + item.getName(), Toast.LENGTH_LONG).show();

            }
        });
        //endregion
    }
}