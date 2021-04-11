package com.example.firebasesignapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "HomeActivity";
    List<User> blockList = new ArrayList<>();
    EditText address_edt, name_edt;
    DatabaseReference add_block_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        TextView sign_out_txt = findViewById(R.id.sign_out_txt);
        address_edt = findViewById(R.id.address);
        name_edt = findViewById(R.id.name);
        Button add_block_btn = findViewById(R.id.addblock);
        Button get_all_block_btn = findViewById(R.id.getAllBlocks_btn);

//
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference mDb = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        String userKey = user.getUid();
        add_block_db = FirebaseDatabase.getInstance().getReference("blocks");
        //add block
        add_block_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBlock();
            }
        });
        get_all_block_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, GetAllBlocksActivity.class);
                startActivity(intent);
            }
        });
        add_block_db.child(userKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    blockList.clear();
                    for (DataSnapshot blocksnap : dataSnapshot.getChildren()) {
                        Log.d(TAG, "blocksnap.getKey(): " + blocksnap.getKey());
                        User user1 = blocksnap.getValue(User.class);
                        blockList.add(user1);
                    }
                    Log.d(TAG, "blockList: " + "size:::" + blockList.size());
                } catch (Exception ex) {
                    Log.d(TAG, "onDataChange: block :catch" + ex.getMessage());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });
        mDb.child("users").child(userKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String userName = dataSnapshot.child("name").getValue(String.class);
                    String userEmail = dataSnapshot.child("email").getValue(String.class);
                    Log.d(TAG, "Name: " + userName + "email:" + userEmail);
                } catch (Exception ex) {
                    Log.d(TAG, "onDataChange: catch" + ex.getMessage());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });

        sign_out_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addBlock() {
        add_block_db.child(mAuth.getUid()).
                child(Objects.requireNonNull(add_block_db.push().getKey())).
                setValue(new Block(name_edt.getText().toString(), address_edt.getText().toString(), mAuth.getUid())).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(HomeActivity.this, "data added succssfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeActivity.this, "data added failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}