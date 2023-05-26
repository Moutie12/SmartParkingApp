package com.example.smart_parking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Reservation extends AppCompatActivity {

    Button btnsend,timeButton,timeButton1;
    EditText matricule,duree;

    int hour, minute;

    String date_arrivée ;
    String date_depart ;

    int nb_places_libres1 ;

    public void insert_historic(String matricule) {
        StringRequest request1 = new StringRequest(Request.Method.POST, "http://192.168.43.26/insert_historic2.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }},new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }}
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                params.put("matricule", matricule);

                return params;
            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(Reservation.this);
        requestQueue1.add(request1);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        matricule=(EditText) findViewById(R.id.matriucle);
        timeButton= findViewById(R.id.timeButton);
        timeButton1= findViewById(R.id.timeButton1);
        btnsend=(Button) findViewById(R.id.button);
        btnsend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){

                if (nb_places_libres1 >=4 )
                {
                    Toast.makeText(Reservation.this, "le parking est complet", Toast.LENGTH_SHORT).show();
                }else {
                    insertData();
                }
            }
        });


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

// Set the URL to retrieve data from.
        String url = "http://192.168.43.26/get_data.php";

// Create a new JsonObjectRequest to make a GET request.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", "Response received with status code: " + response);

                        try {

                            // Get the "nb_places_libres" value from the response object.
                            int nb_places_libres = response.getInt("nb_places_libres");

                            nb_places_libres1 = nb_places_libres ;

                            // Convert the int value to a String.
                            String nb_places_libres_string = String.valueOf(nb_places_libres);
                            // Display the value in a Toast message.
                        } catch (JSONException e) {
                            // Handle JSON parsing errors here.
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle errors here.
            }
        });

// Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }


    private void insertData() {
        String matricule1 = matricule.getText().toString().trim();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        if (matricule1.isEmpty()) {
            Toast.makeText(this, "entrer matricule", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (date_arrivée.compareTo(date_depart) < 0)
            {

                progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.43.26/insert.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("data inserted")) {
                        Toast.makeText(Reservation.this, "data inserted", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        insert_historic(matricule1);
                        startActivity(new Intent(Reservation.this, barriereE.class));
                    } else {
                        Toast.makeText(Reservation.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Reservation.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            ) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();

                    params.put("matricule", matricule1);
                    params.put("date", date_arrivée);
                    params.put("dépard", date_depart);


                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(Reservation.this);
            requestQueue.add(request);
        }
            else {
                Toast.makeText(this, "the first time is later than the second", Toast.LENGTH_SHORT).show();
            }
        }


    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                String date = String.format(Locale.getDefault(), "%02d:%02d",hour, minute) ;
                date_arrivée = date ;
                Toast.makeText(Reservation.this, date, Toast.LENGTH_SHORT).show();
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void popTimePicker1(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                String date1 = String.format(Locale.getDefault(), "%02d:%02d",hour, minute) ;
                date_depart = date1 ;
                Toast.makeText(Reservation.this, date1, Toast.LENGTH_SHORT).show();
                timeButton1.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}