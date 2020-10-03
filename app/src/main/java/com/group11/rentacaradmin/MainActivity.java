package com.group11.rentacaradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    EditText txtqus ,   txtans  ;


    Button btn1 ;

    DatabaseReference dbref;
    Datain std;

    ListView listviewQuestion;
    List<Datain> questionlist;

    private void clearControls()
    {


        txtans.setText("");
        txtqus.setText("");
    }




    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        txtqus = (EditText) findViewById(R.id.edque);
        txtans = (EditText) findViewById(R.id.edans);


        btn1 = (Button) findViewById(R.id.btn1);


        std = new  Datain();

        dbref = FirebaseDatabase.getInstance().getReference().child("FAQ");
        listviewQuestion = (ListView) findViewById(R.id.listviewQuestion);
        questionlist = new ArrayList<>();


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  try {
                if (TextUtils.isEmpty(txtqus.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Pleace enter the Question", Toast.LENGTH_SHORT).show();

                else {

                    std.setQus(txtqus.getText().toString().trim());
                    std.setAns(txtans.getText().toString().trim());


                    dbref.push().setValue(std);
                    Toast.makeText(getApplicationContext(), "Questiuon added", Toast.LENGTH_SHORT).show();
                    clearControls();
                }

            }
            catch (NullPointerException e)
            {
                Toast.makeText(getApplicationContext(), "pleace enter the answer", Toast.LENGTH_SHORT).show();


            } }
        });



    }


    @Override
    protected void onStart() {
        super.onStart();
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                questionlist.clear();
                for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {

                    Datain datain = questionSnapshot.getValue(Datain.class);
                    questionlist.add(datain);
                }

                Questionlist adapter = new Questionlist(MainActivity.this, questionlist);
                listviewQuestion.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}