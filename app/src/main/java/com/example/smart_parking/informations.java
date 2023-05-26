package com.example.smart_parking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class informations extends AppCompatActivity {
    TextView aa ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations);
        aa = (TextView) findViewById(R.id.textView25);

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


                            // Convert the int value to a String.
                            String nb_places_libres_string = String.valueOf(nb_places_libres);

                            aa.setText("Nombre de places libres : "+nb_places_libres_string);
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


}