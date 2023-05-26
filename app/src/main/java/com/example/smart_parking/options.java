package com.example.smart_parking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }

    public void pass_to_réservation(View V)
    {

        startActivity(new Intent(this,Reservation.class));
    }
    public void pass_to_info(View V)
    {

        startActivity(new Intent(this,informations.class));
    }
}