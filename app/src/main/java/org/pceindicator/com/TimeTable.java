package org.pceindicator.com;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

public class TimeTable extends AppCompatActivity {

    SharedPreferences sp;
    String year,branch,div,timeTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_time_table);
        PDFView pdfView = findViewById(R.id.pdfView);
        sp = getSharedPreferences("USER_DATA",MODE_PRIVATE);
        year = sp.getString("YEAR","year");
        branch = sp.getString("BRANCH","branch");
        div = sp.getString("DIVISION","division");
        if(year.equals("First Year")) {
            timeTable = (year + div);
            Toast.makeText(TimeTable.this,"Name = "+timeTable,Toast.LENGTH_SHORT).show();
        }
        else {
            timeTable = (year + branch + div);
            Toast.makeText(TimeTable.this,"Name = "+timeTable,Toast.LENGTH_SHORT).show();
        }
        pdfView.fromAsset(timeTable+".pdf").load();
    }
}
