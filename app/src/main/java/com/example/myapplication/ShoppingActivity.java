package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ShoppingActivity extends AppCompatActivity {

    EditText edtItem, edtQtd;
    Button btnAdd, btnRemove, btnClear, btnSort, btnReverse, btnEdit;
    ListView listView;
    TextView txtContador;

    ArrayList<Item> items;
    ArrayList<String> display;
    ArrayAdapter<String> adapter;

    // prefs
    private static final String PREFS = "shopping_prefs";
    private static final String KEY_LIST = "shopping_list_json";

    // selected index for remove/edit
    int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        edtItem = findViewById(R.id.edtItem);
        edtQtd = findViewById(R.id.edtQtd);
        btnAdd = findViewById(R.id.btnAdd);
        btnRemove = findViewById(R.id.btnRemove);
        btnClear = findViewById(R.id.btnClear);
        btnSort = findViewById(R.id.btnSort);
        btnReverse = findViewById(R.id.btnReverse);
        btnEdit = findViewById(R.id.btnEdit);
        listView = findViewById(R.id.listView);
        txtContador = findViewById(R.id.txtContador);

        items = new ArrayList<>();
        display = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, display);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // load saved
        loadFromPrefs();

        // clicks
        btnAdd.setOnClickListener(v -> addItem());
        btnRemove.setOnClickListener(v -> removeSelected());
        btnClear.setOnClickListener(v -> clearAll());
        btnSort.setOnClickListener(v -> sortAZ());
        btnReverse.setOnClickListener(v -> reverseList());
        btnEdit.setOnClickListener(v -> updateSelected());

        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;
            listView.setItemChecked(position, true);
            // fill fields for potential edit
            Item it = items.get(position);
            edtItem.setText(it.name);
            edtQtd.setText(String.valueOf(it.qty));
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            confirmRemove(position);
            return true;
        });

        updateUI();
    }

    // ========== data model ==========
    static class Item {
        String name;
        int qty;
        Item(String n, int q) { name = n; qty = q; }
    }

    // ========== CRUD functions ==========

    private void addItem() {
        String name = edtItem.getText().toString().trim();
        String qtdS = edtQtd.getText().toString().trim();
        if (name.isEmpty() || qtdS.isEmpty()) {
            Toast.makeText(this, "Preencha nome e quantidade", Toast.LENGTH_SHORT).show();
            return;
        }
        int qtd;
        try { qtd = Integer.parseInt(qtdS); } catch (NumberFormatException e) {
            Toast.makeText(this, "Quantidade inválida", Toast.LENGTH_SHORT).show();
            return;
        }
        // if same name exists, sum quantities (option)
        boolean merged = false;
        for (Item it : items) {
            if (it.name.equalsIgnoreCase(name)) {
                it.qty += qtd;
                merged = true;
                break;
            }
        }
        if (!merged) items.add(new Item(name, qtd));

        edtItem.setText("");
        edtQtd.setText("");
        selectedIndex = -1;
        saveAndRefresh();
        Toast.makeText(this, "Item adicionado", Toast.LENGTH_SHORT).show();
    }

    private void removeSelected() {
        int pos = listView.getCheckedItemPosition();
        if (pos == ListView.INVALID_POSITION) {
            Toast.makeText(this, "Selecione um item para remover", Toast.LENGTH_SHORT).show();
            return;
        }
        confirmRemove(pos);
    }

    private void confirmRemove(int pos) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Remover item");
        b.setMessage("Deseja remover: " + items.get(pos).name + " ?");
        b.setPositiveButton("Sim", (dialog, which) -> {
            items.remove(pos);
            listView.clearChoices();
            selectedIndex = -1;
            saveAndRefresh();
            Toast.makeText(this, "Item removido", Toast.LENGTH_SHORT).show();
        });
        b.setNegativeButton("Não", null);
        b.show();
    }

    private void clearAll() {
        if (items.isEmpty()) {
            Toast.makeText(this, "Lista vazia", Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Limpar lista");
        b.setMessage("Deseja realmente limpar toda a lista?");
        b.setPositiveButton("Sim", (dialog, which) -> {
            items.clear();
            selectedIndex = -1;
            saveAndRefresh();
            Toast.makeText(this, "Lista limpa", Toast.LENGTH_SHORT).show();
        });
        b.setNegativeButton("Não", null);
        b.show();
    }

    private void updateSelected() {
        int pos = listView.getCheckedItemPosition();
        if (pos == ListView.INVALID_POSITION) {
            Toast.makeText(this, "Selecione um item para atualizar", Toast.LENGTH_SHORT).show();
            return;
        }
        String name = edtItem.getText().toString().trim();
        String qtdS = edtQtd.getText().toString().trim();
        if (name.isEmpty() || qtdS.isEmpty()) {
            Toast.makeText(this, "Preencha nome e quantidade", Toast.LENGTH_SHORT).show();
            return;
        }
        int qtd;
        try { qtd = Integer.parseInt(qtdS); } catch (NumberFormatException e) {
            Toast.makeText(this, "Quantidade inválida", Toast.LENGTH_SHORT).show();
            return;
        }
        Item it = items.get(pos);
        it.name = name;
        it.qty = qtd;
        saveAndRefresh();
        Toast.makeText(this, "Item atualizado", Toast.LENGTH_SHORT).show();
    }

    private void sortAZ() {
        Collections.sort(items, Comparator.comparing(i -> i.name.toLowerCase()));
        listView.clearChoices();
        selectedIndex = -1;
        saveAndRefresh();
    }

    private void reverseList() {
        Collections.reverse(items);
        listView.clearChoices();
        selectedIndex = -1;
        saveAndRefresh();
    }

    // ========== persistence ==========
    private void saveAndRefresh() {
        saveToPrefs();
        refreshDisplay();
    }

    private void saveToPrefs() {
        try {
            JSONArray arr = new JSONArray();
            for (Item it : items) {
                JSONObject o = new JSONObject();
                o.put("name", it.name);
                o.put("qty", it.qty);
                arr.put(o);
            }
            SharedPreferences prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
            prefs.edit().putString(KEY_LIST, arr.toString()).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFromPrefs() {
        try {
            SharedPreferences prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
            String s = prefs.getString(KEY_LIST, null);
            if (s == null) return;
            JSONArray arr = new JSONArray(s);
            items.clear();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                String name = o.getString("name");
                int qty = o.getInt("qty");
                items.add(new Item(name, qty));
            }
            refreshDisplay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========== UI refresh ==========
    private void refreshDisplay() {
        display.clear();
        for (Item it : items) {
            display.add(it.name + "  •  Qtd: " + it.qty);
        }
        adapter.notifyDataSetChanged();
        updateUI();
    }

    private void updateUI() {
        int totalItems = items.size();
        int totalQty = 0;
        for (Item it : items) totalQty += it.qty;
        txtContador.setText("Total: " + totalItems + " itens | Qtd total: " + totalQty);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveToPrefs();
    }
}
