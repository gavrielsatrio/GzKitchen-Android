package com.example.gzkitchen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddEditMenuActivity extends AppCompatActivity {

    TextView btnBack;
    Button btnSave;
    Button btnCancel;
    FloatingActionButton btnAddImage;
    Spinner comboIngredients;
    TextView btnAddIngredients;
    TextView btnAddDetails;

    ImageView imgMneu;
    EditText txtName;
    EditText txtPrice;
    RecyclerView recViewIngredients;
    EditText txtDescription;
    EditText txtDetails;
    ListView listViewDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_menu);
    }
}