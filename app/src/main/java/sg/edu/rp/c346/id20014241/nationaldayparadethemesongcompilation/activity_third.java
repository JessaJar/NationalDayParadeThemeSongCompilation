package sg.edu.rp.c346.id20014241.nationaldayparadethemesongcompilation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

class ThirdActivity extends AppCompatActivity {

    EditText etTitle, etSinger, etYear, etId;
    RadioGroup radGrp;
    RadioButton rad1, rad2, rad3, rad4, rad5;
    Button btnUpdate, btnDelete, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        rad1 = findViewById(R.id.rad1);
        rad2 = findViewById(R.id.rad2);
        rad3 = findViewById(R.id.rad3);
        rad4 = findViewById(R.id.rad4);
        rad5 = findViewById(R.id.rad5);
        etTitle = findViewById(R.id.etTitle);
        etSinger = findViewById(R.id.etSinger);
        etYear = findViewById(R.id.etYear);
        etId = findViewById(R.id.etId);
        radGrp = findViewById(R.id.radGrp);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        Intent i = getIntent();
        final Song currentSong = (Song) i.getSerializableExtra("song");

        etId.setText(currentSong.getId() + "");
        etTitle.setText(currentSong.getTitle());
        etSinger.setText(currentSong.getSingers());
        etYear.setText(currentSong.getYear());
        switch (currentSong.getStars()) {
            case 5:
                rad5.setChecked(true);
                break;
            case 4:
                rad4.setChecked(true);
            case 3:
                rad3.setChecked(true);
            case 2:
                rad2.setChecked(true);
            case 1:
                rad1.setChecked(true);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                currentSong.setTitle(etTitle.getText().toString().trim());
                currentSong.setSingers(etSinger.getText().toString().trim());
                int year = 0;
                try {
                    year = Integer.valueOf(etYear.getText().toString().trim());
                } catch (Exception e) {
                    Toast.makeText(ThirdActivity.this, "Invalid year", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentSong.setYearReleased(year);

                int selectedRB = radGrp.getCheckedRadioButtonId();
                RadioButton rb = findViewById(selectedRB);
                currentSong.setStars(Integer.parseInt(rb.getText().toString()));
                int result = dbh.updateSong(currentSong);
                if (result>0) {
                    Toast.makeText(ThirdActivity.this, "Song updated", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(ThirdActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                int result = dbh.deleteSong(currentSong.getId());
                if (result > 0) {
                    Toast.makeText(ThirdActivity.this, "Song deleted", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(ThirdActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}