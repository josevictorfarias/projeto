package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ViewHolder> {

    private List<Produto> lista;
    private String modo;

    public ProdutoAdapter(List<Produto> lista, String modo) {
        this.lista = lista;
        this.modo = modo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto p = lista.get(position);

        holder.txtNome.setText(p.getNome());
        holder.txtCategoria.setText(p.getCategoria());
        holder.txtQuantidade.setText("Quantidade: " + p.getQuantidade());

        byte[] imagemBytes = p.getImagem();

        if (imagemBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagemBytes, 0, imagemBytes.length);
            holder.img.setImageBitmap(bitmap);
        } else {
            holder.img.setImageResource(R.drawable.ic_launcher_background);
        }

        // ABRIR DETALHES
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), ProductDetailsActivity.class);
            i.putExtra("produto_id", p.getId());
            i.putExtra("modo", modo); // <-- importante
            v.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtNome, txtCategoria, txtQuantidade;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imgItem);
            txtNome = itemView.findViewById(R.id.txtNomeItem);
            txtCategoria = itemView.findViewById(R.id.txtCategoriaItem);
            txtQuantidade = itemView.findViewById(R.id.txtQuantidadeItem);
        }
    }
}
