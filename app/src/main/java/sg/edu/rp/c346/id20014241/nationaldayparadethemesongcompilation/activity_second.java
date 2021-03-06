package sg.edu.rp.c346.id20014241.nationaldayparadethemesongcompilation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

class SecondActivity extends AppCompatActivity {

    Button btn5Stars;
    Spinner spinner;
    ListView lv;
    RadioGroup radGrp;
    ArrayList<Song> al;
    ArrayAdapter<Song> aa;
    String moduleCode;
    int requestCode = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        setTitle(getTitle().toString() + " ~ " + "Insert Song");

        btn5Stars = findViewById(R.id.btn5Stars);
        spinner = findViewById(R.id.spinner);
        lv = findViewById(R.id.lv);
        radGrp = findViewById(R.id.radGrp);

        DBHelper dbh = new DBHelper(this);
        al = dbh.getAllSongs();
        dbh.close();

        //al = new ArrayList<Song>();
        aa = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long identity) {
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                i.putExtra("song", al.get(position));
                startActivity(i);
                //startActivityForResult(i, requestCode);
            }
        });

        btn5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                al.clear();
                al.addAll(dbh.getAllSongsByStars(5));
                aa.notifyDataSetChanged();
            }
        });
    }

}