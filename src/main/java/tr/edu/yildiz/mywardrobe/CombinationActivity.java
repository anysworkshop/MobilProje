package tr.edu.yildiz.mywardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class CombinationActivity extends AppCompatActivity {


    private User tempUser;
    private Combination combination;
    private Button createButton;
    private Button selectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combination);

        Intent intent = getIntent();
        final User user = intent.getParcelableExtra("Logged in User Combination");
        tempUser = user;

        createButton = findViewById(R.id.buttonCreate);
        selectButton = findViewById(R.id.buttonSelect);


        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbOperation db = new dbOperation(getApplicationContext());
                db.createCombination(tempUser.getUserID(),"Combination 1");
            }
        });

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageFromDrawer();
            }
        });
    }

    public void selectImageFromDrawer(){
        combination = new Combination();

        //DATABASE OLUŞTUR!!

        Intent intent = new Intent(this,DrawerActivity.class);
        intent.putExtra("Logged in User from Combination",tempUser);
        intent.putExtra("Logged in Combination",combination);
        startActivity(intent);
    }

    public void selectImageHead(){

    }

    public void selectImageFace(){

    }

    public void selectImageTop(){

    }

    public void selectImageBottom(){

    }

    public void selectImageFootwear(){

    }
}