package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private List<Usuario> listaUsuarios;

    public UsuarioAdapter(List<Usuario> lista) {
        this.listaUsuarios = lista;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // ESSENCIAL: O Android deve encontrar o arquivo R.layout.item_usuario
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario u = listaUsuarios.get(position);
        holder.txtNome.setText(u.getNomeCompleto());
        holder.txtLogin.setText("Login: " + u.getLogin());
        holder.txtNivel.setText("Nível: " + u.getNivelAcesso());
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome, txtLogin, txtNivel;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            // ESSENCIAL: Os IDs devem existir no item_usuario.xml
            txtNome = itemView.findViewById(R.id.txtNomeUsuario);
            txtLogin = itemView.findViewById(R.id.txtLoginUsuario);
            txtNivel = itemView.findViewById(R.id.txtNivelUsuario);
        }
    }
}