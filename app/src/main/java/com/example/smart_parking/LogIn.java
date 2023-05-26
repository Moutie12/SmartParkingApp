package com.example.smart_parking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LogIn extends AppCompatActivity {

    Button btnsend;

    EditText email,password ;

    public void insert_historic(String email1,String password1) {
        StringRequest request1 = new StringRequest(Request.Method.POST, "http://192.168.43.26/insert_historic.php",
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

                params.put("email", email1);
                params.put("password", password1);


                return params;
            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(LogIn.this);
        requestQueue1.add(request1);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        email=(EditText) findViewById(R.id.email2);
        password=(EditText) findViewById(R.id.password2);
        btnsend=(Button) findViewById(R.id.search1);
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                search();
            }
        });
    }
    public void pass_to_options(View V)
    {

        startActivity(new Intent(this,options.class));
    }
    public void pass_to_back(View V)
    {

        startActivity(new Intent(this,MainActivity.class));
    }
    public void pass_to_signup(View V)
    {

        startActivity(new Intent(this,SignUP.class));
    }

    private void search()
    {
        String email1 = email.getText().toString().trim();
        String password1 = password.getText().toString().trim();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        if (email1.isEmpty()){
            Toast.makeText(this, "Entrer Email", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (password1.isEmpty()){
            Toast.makeText(this, "Entrer Password", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.43.26/signin.php", new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    if(response.equalsIgnoreCase("data founded11111")){
                        Toast.makeText(LogIn.this, "data founded", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        insert_historic(email1,password1);
                        startActivity(new Intent(LogIn.this,options.class));
                        common.main(email1,password1);
                    }
                    else {
                        Toast.makeText(LogIn.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LogIn.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<>();

                    params.put("email",email1);
                    params.put("password",password1);



                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(LogIn.this);
            requestQueue.add(request);
        }
    }
}