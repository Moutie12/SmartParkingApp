package com.example.smart_parking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class SignUP extends AppCompatActivity {

    Button btnsend;

    EditText matricule,nom,prénom,email,password ;

    public void insert_historic(String email1,String password1) {
        StringRequest request1 = new StringRequest(Request.Method.POST, "http://192.168.43.26/insert_historic1.php",
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
        RequestQueue requestQueue1 = Volley.newRequestQueue(SignUP.this);
        requestQueue1.add(request1);
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        matricule=(EditText) findViewById(R.id.matricule);
        nom=(EditText) findViewById(R.id.nom);
        prénom=(EditText) findViewById(R.id.prénom);
        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        btnsend=(Button) findViewById(R.id.register);
        btnsend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                register();
            }
        });
    }
    private void register()
    {
        String matricule1 = matricule.getText().toString().trim();
        String nom1 = nom.getText().toString().trim();
        String prénom1 = prénom.getText().toString().trim();
        String email1 = email.getText().toString().trim();
        String password1 = password.getText().toString().trim();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        if (matricule1.isEmpty()){
            Toast.makeText(this, "entrer matricule", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (nom1.isEmpty()){
            Toast.makeText(this, "entrer nom", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (prénom1.isEmpty()){
            Toast.makeText(this, "entrer prénom", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (email1.isEmpty()){
            Toast.makeText(this, "entrer email", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (password1.isEmpty()){
            Toast.makeText(this, "entrer password", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.43.26/signup.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equalsIgnoreCase("data inserted")){
                        Toast.makeText(SignUP.this, "data inserted", Toast.LENGTH_SHORT).show();
                        insert_historic(email1,password1);
                        progressDialog.dismiss();
                        startActivity(new Intent(SignUP.this,LogIn.class));

                    }
                    else {
                        Toast.makeText(SignUP.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SignUP.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<>();

                    params.put("matricule",matricule1);
                    params.put("nom",nom1);
                    params.put("prénom",prénom1);
                    params.put("email",email1);
                    params.put("password",password1);



                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(SignUP.this);
            requestQueue.add(request);
        }
    }
}