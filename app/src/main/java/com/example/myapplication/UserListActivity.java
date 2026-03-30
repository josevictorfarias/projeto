package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserListActivity extends AppCompatActivity {

    RecyclerView recyclerUsuarios;
    DatabaseHelper db;
    UsuarioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        recyclerUsuarios = findViewById(R.id.recyclerUsuarios);
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(this));

        db = new DatabaseHelper(this);

        carregarUsuarios();
    }

    private void carregarUsuarios() {
        List<Usuario> lista = db.listarUsuarios(); // deve retornar List<Usuario>
        adapter = new UsuarioAdapter(lista);
        recyclerUsuarios.setAdapter(adapter);
    }
}
