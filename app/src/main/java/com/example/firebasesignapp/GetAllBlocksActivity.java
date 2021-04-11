package com.example.firebasesignapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GetAllBlocksActivity extends AppCompatActivity {
    List<Block> blockList = new ArrayList<>();
    private static final String TAG = "GetAllBlocksActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_blocks);
        RecyclerView getallBlocks_rec = findViewById(R.id.getAllBlocks_rec);
        BlockAdapter blockAdapter = new BlockAdapter(this);
        getallBlocks_rec.setAdapter(blockAdapter);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userKey = user.getUid();
        DatabaseReference get_blocks_db = FirebaseDatabase.getInstance().getReference("blocks");
        get_blocks_db.child(userKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    blockList.clear();
                    for (DataSnapshot blocksnap : dataSnapshot.getChildren()) {
                        Log.d(TAG, "blocksnap.getKey(): " + blocksnap.getKey());
                        Block block = blocksnap.getValue(Block.class);
                        blockList.add(block);
                        blockAdapter.setDataSet((ArrayList<Block>) blockList);
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
    }
}