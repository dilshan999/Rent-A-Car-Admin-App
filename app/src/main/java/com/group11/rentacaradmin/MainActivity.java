package com.group11.rentacaradmin;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    EditText txtqus ,   txtans  ;


    Button btn1 ;

    DatabaseReference dbref;
    Datain std;

    private void clearControls()
    {


        txtans.setText("");
        txtqus.setText("");
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        txtqus = (EditText) findViewById(R.id.edque);
        txtans = (EditText) findViewById(R.id.edans);


        btn1 = (Button) findViewById(R.id.btn1);


        std = new  Datain();

        dbref = FirebaseDatabase.getInstance().getReference().child("Datain");



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  try {
                if (TextUtils.isEmpty(txtqus.getText().toString()))
                    Toast.makeText(getApplicationContext(), "ONE", Toast.LENGTH_SHORT).show();

                else {

                    std.setQus(txtqus.getText().toString().trim());
                    std.setAns(txtans.getText().toString().trim());


                    dbref.push().setValue(std);
                    Toast.makeText(getApplicationContext(), "TWO", Toast.LENGTH_SHORT).show();
                    clearControls();
                }

            }
            catch (NullPointerException e)
            {
                Toast.makeText(getApplicationContext(), "THREE", Toast.LENGTH_SHORT).show();


            } }
        });





    }
}