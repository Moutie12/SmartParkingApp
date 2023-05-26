package com.example.smart_parking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class BarrireS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barrire_s);
    }

    public void insert_historic1(String email,String password) {
        StringRequest request1 = new StringRequest(Request.Method.POST, "http://192.168.1.15/insert_historic4.php",
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
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(BarrireS.this);
        requestQueue1.add(request1);
    }

    public void open1(View v) {
        StringRequest request = new StringRequest(Request.Method.GET, "http://192.168.43.148/servor2", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String email2 = common.email ;
                String password2 = common.password ;
                insert_historic1(email2,password2);
                Toast.makeText(BarrireS.this, "1", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BarrireS.this, "2", Toast.LENGTH_SHORT).show();
                String email2 = common.email ;
                String password2 = common.password ;
                insert_historic1(email2,password2);
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("command","on");
                return params;
            }
        };

        // Set a custom retry policy with 0 retries
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(BarrireS.this);
        requestQueue.add(request);
        Intent I = new Intent(this,BarrireS.class);
        startActivity(I);
    }
}