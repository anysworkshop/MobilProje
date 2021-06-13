package tr.edu.yildiz.mywardrobe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.DatePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class ClothesAddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private ImageView imageView;
    private Bitmap selectedImage;
    private EditText typeText;
    private EditText colorText;
    private EditText patternText;
    private TextView dateText;
    private EditText priceText;
    private Button buttonSave;
    private int partText;
    private boolean editMode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_add);

        Intent intent = getIntent();
        final Drawer loggedDrawerID = intent.getParcelableExtra("Drawer ID");
        final Clothes clothesEdit = intent.getParcelableExtra("Clothes to Edit");



        imageView = findViewById(R.id.imageViewAddClothes);
        dateText = findViewById(R.id.dateText);
        typeText = findViewById(R.id.typeText);
        colorText = findViewById(R.id.colorText);
        patternText = findViewById(R.id.patternText);
        priceText = findViewById(R.id.priceText);
        buttonSave = findViewById(R.id.saveButton);

        final Spinner spinner = findViewById(R.id.spinnerClothes);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.parts,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                partText = i;
                ((TextView) view).setTextColor(Color.GREEN);
                adapter.notifyDataSetChanged();
                Toast.makeText(adapterView.getContext(),"You selected this clothes as an "+ text, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        if(clothesEdit!=null){
            editMode = true;
            selectedImage = BitmapFactory.decodeByteArray(clothesEdit.getByteArrays(),0,clothesEdit.getByteArrays().length);
            imageView.setImageBitmap(selectedImage);
            typeText.setText(clothesEdit.getType());
            colorText.setText(clothesEdit.getColor());
            patternText.setText(clothesEdit.getPattern());
            dateText.setText(clothesEdit.getDate());
            priceText.setText(String.valueOf(clothesEdit.getPrice()));
            buttonSave.setText("Update");
        }

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editMode){
                    updateClothes(clothesEdit);
                }
                else{
                    saveClothes(loggedDrawerID.getDrawerID());
                }
            }
        });


    }

    public void updateClothes(Clothes clothes){
        Clothes clothesEdited = new Clothes(partText,typeText.getText().toString(),colorText.getText().toString(),patternText.getText().toString(),dateText.getText().toString(),Integer.parseInt(priceText.getText().toString()),save(),clothes.getDrawerID());

        dbOperation db = new dbOperation(getApplicationContext());
        Boolean result = db.clothesUpdate(clothesEdited,clothes.getClothesID());
        if  (result)  {
            //finish();
            Toast.makeText(getApplicationContext(),"You have successfully added a new clothes",Toast.LENGTH_LONG).show();
        }  else {
            Toast.makeText(getApplicationContext(),"Something went wrong, try again!",Toast.LENGTH_LONG).show();
        }
        db.close();

        finish();
    }

    public void saveClothes(int drawerID){
        Clothes clothes = new Clothes(partText,typeText.getText().toString(),colorText.getText().toString(),patternText.getText().toString(),dateText.getText().toString(),Integer.parseInt(priceText.getText().toString()),save(),drawerID);

        dbOperation db = new dbOperation(getApplicationContext());
        Boolean result = db.clothesAdd(clothes);
        if  (result)  {
            //finish();
            Toast.makeText(getApplicationContext(),"You have successfully added a new clothes",Toast.LENGTH_LONG).show();
        }  else {
            Toast.makeText(getApplicationContext(),"Something went wrong, try again!",Toast.LENGTH_LONG).show();
        }
        db.close();

        finish();
    }

    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = + month + "/" + dayOfMonth + "/" + year;
        dateText.setText(date);
        dateText.setTypeface(dateText.getTypeface(),Typeface.BOLD);
        dateText.setTextSize(20);
    }

    public void selectImage(View view) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
        } else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery,2);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery,2);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

            Uri imageData = data.getData();


            try {

                if (Build.VERSION.SDK_INT >= 28) {

                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(),imageData);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    imageView.setImageBitmap(selectedImage);

                } else {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageData);
                    imageView.setImageBitmap(selectedImage);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    public byte[] save() {


        Bitmap smallImage = makeSmallerImage(selectedImage,300);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        smallImage.compress(Bitmap.CompressFormat.PNG,50,outputStream);
        byte[] byteArray = outputStream.toByteArray();


        return byteArray;
    }

    public Bitmap makeSmallerImage(Bitmap image, int maximumSize) {

        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;

        if (bitmapRatio > 1) {
            width = maximumSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maximumSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image,width,height,true);
    }

}