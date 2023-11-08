package com.example.login_registro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RegistoUsuario extends Activity {

    private EditText editTextUsername;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo_usuario);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void register(View view) {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        if (isValidInput(username, password)) {
            // Recuperar la lista de usuarios registrados
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            Set<String> userSet = sharedPreferences.getStringSet("users", new HashSet<String>());
            ArrayList<String> userList = new ArrayList<>(userSet);

            // Agregar el nuevo usuario a la lista
            userList.add(username);

            // Guardar la lista actualizada en SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("users", new HashSet<>(userList));
            editor.putString(username, password);  // También puedes guardar la contraseña asociada con el nombre de usuario
            editor.apply();

            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();

            // Redirigir a la pantalla de inicio de sesión automáticamente
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Por favor, ingrese un nombre de usuario y una contraseña válidos", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidInput(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un nombre de usuario y una contraseña válidos", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Verificar si el usuario ya existe
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        Set<String> userSet = sharedPreferences.getStringSet("users", new HashSet<String>());
        if (userSet.contains(username)) {
            Toast.makeText(this, "Este usuario ya está registrado", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}

