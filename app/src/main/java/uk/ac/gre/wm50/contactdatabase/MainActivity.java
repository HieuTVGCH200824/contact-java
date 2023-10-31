package uk.ac.gre.wm50.contactdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Spinner avatarSpinner;
    final Integer[] avatarOptions = {R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4, R.drawable.avatar5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        avatarSpinner = findViewById(R.id.avatarSpinner);
        AvatarAdapter adapter = new AvatarAdapter(this, R.layout.spinner_item, avatarOptions, 250);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        avatarSpinner.setAdapter(adapter);

        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDetails();
            }
        });

        Button detailBtn = findViewById(R.id.detailBtn);
        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });
    }


    private void saveDetails() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        EditText nameTxt = findViewById(R.id.nameText);
        EditText dobTxt = findViewById(R.id.dobText);
        EditText emailTxt = findViewById(R.id.emailText);

        String name = nameTxt.getText().toString();
        String dob = dobTxt.getText().toString();
        String email = emailTxt.getText().toString();
        Integer selectedAvatar = (Integer) avatarSpinner.getSelectedItem();

        long personId = dbHelper.insertDetails(name, dob, email, selectedAvatar);

        Toast.makeText(this, "Person has been created with id: " + personId, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, DetailsActivity.class);
        startActivity(intent);
    }
}