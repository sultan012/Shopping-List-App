package com.hotmail.a_asultan.androidshoppinglistaapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private JSONSerializer mSerializer;
    private List<Item> itemList;
    private RecyclerView mRecyclerView;
    private ItemAdapter mAdapter;
    private boolean mShowDividers;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogNewItem dialog = new DialogNewItem();
                dialog.show(getSupportFragmentManager(), "");
            }
        });

        mSerializer = new JSONSerializer("NoteToSelf.json", getApplicationContext());

        try {
            itemList = mSerializer.load();
        } catch (Exception e) {
            itemList = new ArrayList<Item>();
            Log.e("Error loading notes: ", "", e);
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new ItemAdapter(this, itemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        swipeToDelete();
    }

    public void saveItem(){
        try{
            mSerializer.save(itemList);
        }catch(Exception e){
            Log.e("Error Saving Notes", "", e);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        saveItem();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mPrefs = getSharedPreferences("Note to self", MODE_PRIVATE);
        mShowDividers = mPrefs.getBoolean("dividers", true);

        if(mShowDividers){
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        }else{
            if(mRecyclerView.getItemDecorationCount() > 0){
                mRecyclerView.removeItemDecorationAt(0);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createNewItem(Item item){
        itemList.add(item);
        mAdapter.notifyDataSetChanged();
    }

    private void swipeToDelete(){
        SwipeDelete swipeDelete = new SwipeDelete(this){
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction){
                final int position = viewHolder.getAdapterPosition();
                mAdapter.removeItem(position);
                saveItem();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeDelete);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

}
