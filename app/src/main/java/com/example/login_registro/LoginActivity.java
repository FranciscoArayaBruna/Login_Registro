package com.example.login_registro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashSet;
import java.util.Set;

public class LoginActivity extends Activity {

    private EditText editTextUsername;
    private EditText editTextPassword;

    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistoUsuario.class);
                startActivity(i);
            }
        });

        // Borrar cualquier texto existente en los EditText
        editTextUsername.setText(""); // Establecer el texto en una cadena vacía
        editTextPassword.setText(""); // Establecer el texto en una cadena vacía
    }

    public void login(View view) {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        if (isValidUser(username, password)) {
            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Inicio de sesión fallido", Toast.LENGTH_SHORT).show(); // Aquí debes usar Toast.LENGTH_SHORT
        }
    }

    private boolean isValidUser(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        Set<String> userSet = sharedPreferences.getStringSet("users", new HashSet<String>());

        if (userSet.contains(username)) {
            String savedPassword = sharedPreferences.getString(username, "");
            return password.equals(savedPassword);
        }

        return false;
    }
}


