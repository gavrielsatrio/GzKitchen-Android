package com.example.gzkitchen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.TimedMetaData;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddEditMenuActivity extends AppCompatActivity {

    TextView btnBack;
    Button btnSave;
    Button btnCancel;
    FloatingActionButton btnAddImage;
    Spinner comboIngredients;
    TextView btnAddIngredients;
    TextView btnAddDetails;

    ImageView imgMenu;
    EditText txtName;
    EditText txtPrice;
    RecyclerView recViewIngredients;
    EditText txtDescription;
    EditText txtDetails;
    ListView listViewDetails;

    JSONArray jsonArrayQueryIngredients;

    JSONArray jsonArrayMenuIngredients = new JSONArray();
    JSONArray jsonArrayMenuDetails = new JSONArray();

    Bitmap menuImageBitmap = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if(requestCode == 501) {
                Uri dataUri = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(dataUri, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String path = cursor.getString(columnIndex);

                menuImageBitmap = BitmapFactory.decodeFile(path);
                imgMenu.setImageBitmap(menuImageBitmap);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_menu);

        btnBack = findViewById(R.id.addEditMenuBtnBack);
        btnSave = findViewById(R.id.addEditMenuBtnSave);
        btnCancel = findViewById(R.id.addEditMenuBtnCancel);
        btnAddImage = findViewById(R.id.addEditMenuBtnAddImage);
        comboIngredients = findViewById(R.id.addEditMenuComboIngredients);
        btnAddIngredients = findViewById(R.id.addEditMenuBtnAddIngredients);
        btnAddDetails = findViewById(R.id.addEditMenuBtnAddDetails);
        imgMenu = findViewById(R.id.addEditMenuImg);
        txtName = findViewById(R.id.addEditMenuTxtName);
        txtPrice = findViewById(R.id.addEditMenuTxtPrice);
        recViewIngredients = findViewById(R.id.addEditMenuRecViewIngredients);
        txtDescription = findViewById(R.id.addEditMenuTxtDescription);
        txtDetails = findViewById(R.id.addEditMenuTxtDetails);
        listViewDetails = findViewById(R.id.addEditMenuListViewDetails);

        String menuID = getIntent().getStringExtra("MenuID");
        if(menuID != null) {
            LoadData(menuID);
        }

        LoadComboIngredients();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddEditMenuActivity.super.onBackPressed();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(menuImageBitmap != null) {
                    if(!txtName.getText().toString().trim().equals("")) {
                        if(!txtPrice.getText().toString().trim().equals("")) {
                            if(TextUtils.isDigitsOnly(txtPrice.getText().toString().trim())) {
                                int price = Integer.parseInt(txtPrice.getText().toString().trim());

                                if(price > 0) {
                                    if(jsonArrayMenuIngredients.length() > 0) {
                                        if(!txtDescription.getText().toString().trim().equals("")) {
                                            if(jsonArrayMenuDetails.length() > 0) {
                                                MenuController menuController = new MenuController(AddEditMenuActivity.this);
                                                BitmapHelper bitmapHelper = new BitmapHelper();

                                                if(menuID != null) {
                                                    // Update
                                                } else {
                                                    // Insert
                                                    try {
                                                        JSONArray jsonArrayAllMenus = menuController.getMenus();
                                                        int latestID = jsonArrayAllMenus.getJSONObject(jsonArrayAllMenus.length() - 1).getInt("ID");

                                                        int insertID = latestID + 1;
                                                        JSONObject objectMenuInsert = new JSONObject();
                                                        objectMenuInsert.put("ID", insertID).put("Image", bitmapHelper.convertToBase64String(menuImageBitmap)).put("Name", txtName.getText().toString().trim()).put("Price", price).put("Description", txtDescription.getText().toString().trim())
                                                                .put("Ingredients", jsonArrayMenuIngredients)
                                                                .put("Details", jsonArrayMenuDetails);

                                                        menuController.addMenu(objectMenuInsert);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }


                                            } else {
                                                Toast.makeText(AddEditMenuActivity.this, "Please add at least one detail to the menu", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            txtDescription.setError("Fill up menu description");
                                        }
                                    } else {
                                        Toast.makeText(AddEditMenuActivity.this, "Please add at least one ingredient to the menu", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    txtPrice.setError("Price must be more than zero");
                                    txtPrice.requestFocus();
                                }
                            } else {
                                txtPrice.setError("Price must be a number without decimal");
                                txtPrice.requestFocus();
                            }
                        } else {
                            txtPrice.setError("Fill up menu price");
                            txtPrice.requestFocus();
                        }
                    } else {
                        txtName.setError("Fill up menu name");
                        txtName.requestFocus();
                    }
                } else {
                    Toast.makeText(AddEditMenuActivity.this, "Please select a menu image", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddEditMenuActivity.super.onBackPressed();
            }
        });

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isReadExternalPermitted()) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, 501);
                }
            }
        });

        btnAddIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject objectIngredientSelected = jsonArrayQueryIngredients.getJSONObject(comboIngredients.getSelectedItemPosition());
                    boolean isThereThatIngredientAlready = false;
                    for(int i = 0; i < jsonArrayMenuIngredients.length(); i++) {
                        JSONObject objectIngredient = jsonArrayMenuIngredients.getJSONObject(i);
                        if(objectIngredient.getInt("ID") == objectIngredientSelected.getInt("ID")) {
                            isThereThatIngredientAlready = true;
                            break;
                        }
                    }

                    if(!isThereThatIngredientAlready) {
                        jsonArrayMenuIngredients.put(objectIngredientSelected);
                        LoadDataIngredients();
                    } else {
                        Toast.makeText(AddEditMenuActivity.this, "This ingredients is already added to the menu", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnAddDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtDetails.getText().toString().trim().equals("")) {
                    jsonArrayMenuDetails.put(txtDetails.getText().toString().trim());

                    LoadDataDetails();

                    txtDetails.setText("");
                    txtDetails.requestFocus();
                } else {
                    txtDetails.setError("Fill up menu details to add");
                    txtDetails.requestFocus();
                }
            }
        });
    }

    private void LoadComboIngredients() {
        jsonArrayQueryIngredients = new IngredientsController(AddEditMenuActivity.this).getIngredients();
        comboIngredients.setAdapter(new ComboBoxAdapter(AddEditMenuActivity.this, jsonArrayQueryIngredients));
    }

    private void LoadData(String menuID) {

    }

    private boolean isReadExternalPermitted() {
        if(ActivityCompat.checkSelfPermission(AddEditMenuActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(AddEditMenuActivity.this, new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE }, 500);
            return false;
        }
    }

    public void LoadDataIngredients() {
        recViewIngredients.setAdapter(new IngredientsAdapter(AddEditMenuActivity.this, jsonArrayMenuIngredients));
        recViewIngredients.setLayoutManager(new LinearLayoutManager(AddEditMenuActivity.this, LinearLayoutManager.HORIZONTAL, false));
    }

    public void LoadDataDetails() {
        listViewDetails.setAdapter(new MenuDetailsAdapter(AddEditMenuActivity.this, jsonArrayMenuDetails));
    }
}