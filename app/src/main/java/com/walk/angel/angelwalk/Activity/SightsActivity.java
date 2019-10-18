package com.walk.angel.angelwalk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.walk.angel.angelwalk.Adapter.SightsAdapter;
import com.walk.angel.angelwalk.Data.SightList;
import com.walk.angel.angelwalk.Data.SightsData;
import com.walk.angel.angelwalk.R;

import java.util.ArrayList;

public class SightsActivity extends AppCompatActivity {

    SightsAdapter sightListAdapter;
    RecyclerView recyclerView;

    private ArrayList<SightsData> arrayListOfSightData = new ArrayList<>();
    private GestureDetector gestureDetector;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sights);

        gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        try {
            SightList manualSightList = new SightList();
            manualSightList.setArrayListOfSightData(getAssets());
            manualSightList.setLocationInfo();
            arrayListOfSightData = manualSightList.getArrayListOfSightData();
            linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            sightListAdapter = new SightsAdapter(arrayListOfSightData);
        }catch (Exception e) {
            Log.d("aaaa", "Sight Setting Error is " + e.toString());
        }

        setupViews();
    }

    void setupViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_sight);

        // Setup RecyclerView, associated adapter, and layout manager.

        //RecyclerView에 Adapter세팅
        recyclerView.setAdapter(sightListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(onItemTouchListener);

    }

    RecyclerView.OnItemTouchListener onItemTouchListener = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View chileView = rv.findChildViewUnder(e.getX(), e.getY());

            if (chileView != null && gestureDetector.onTouchEvent(e)) {
                int currentPosition = rv.getChildAdapterPosition(chileView);

                SightsData data = arrayListOfSightData.get(currentPosition);
                //Toast.makeText(SightsActivity.this, "클릭한 아이템의 이름은 " + arrayListOfSightData.get(currentPosition).getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SightsActivity.this, SightInfoWebActivity.class);
                intent.putExtra("sightsInfo", data);
                startActivity(intent);

                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    };

}
