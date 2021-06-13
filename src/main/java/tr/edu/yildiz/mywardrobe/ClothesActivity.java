package tr.edu.yildiz.mywardrobe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ClothesActivity extends AppCompatActivity {
    ArrayList<Clothes> clothesList;

    private RecyclerView mRecyclerViewClothes;
    private ClothesAdapter mAdapterClothes;
    private RecyclerView.LayoutManager mLayoutManagerClothes;
    private Combination tempCombination;
    private FloatingActionButton buttonAddClothes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        Intent intent = getIntent();
        final Drawer drawer = intent.getParcelableExtra("Drawer Items");
        final Combination combination = intent.getParcelableExtra("Combination");
        tempCombination = combination;

        createClothesList(drawer);
        buildRecyclerViewClothes();

        buttonAddClothes = findViewById(R.id.add_clothes_button);

        buttonAddClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClothesActivity.this,ClothesAddActivity.class);
                intent.putExtra("Drawer ID",drawer);
                startActivity(intent);
            }
        });

        if(tempCombination!=null){
            buttonAddClothes.setVisibility(View.GONE);
        }
    }
    protected void onRestart() {
        super.onRestart();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    public void editItem(int position) {

        Intent intent = new Intent(ClothesActivity.this,ClothesAddActivity.class);
        intent.putExtra("Clothes to Edit",clothesList.get(position));
        startActivity(intent);

    }

    public void removeItem(int position) {


        dbOperation db = new dbOperation(getApplicationContext());

        boolean result = db.clothesDelete(clothesList.get(position));
        db.close();
        if  (result)  {
            //finish();
            Toast.makeText(getApplicationContext(),"Item of type "+  clothesList.get(position).getType() + " has been deleted.",Toast.LENGTH_LONG).show();
            clothesList.remove(position);
        }  else {
            //Toast.makeText(getApplicationContext(),"Something went wrong, try again!",Toast.LENGTH_LONG).show();
        }
        mAdapterClothes.notifyItemRemoved(position);
    }

    public void selectItem(int position) {


        dbOperation db = new dbOperation(getApplicationContext());
        switch (clothesList.get(position).getPart()){
            case 1:
                tempCombination.setHead(clothesList.get(position).getClothesID());
                db.combinationUpdateHead(tempCombination.getCombinationID(),clothesList.get(position).getClothesID());
                break;
            case 2:
                tempCombination.setFace(clothesList.get(position).getClothesID());
                break;
            case 3:
                tempCombination.setTop(clothesList.get(position).getClothesID());
                break;
            case 4:
                tempCombination.setBottom(clothesList.get(position).getClothesID());
                break;
            case 5:
                tempCombination.setFootwear(clothesList.get(position).getClothesID());

                break;
            default:
        }
        db.close();

    }

    public void createClothesList(Drawer drawer){
        clothesList = new ArrayList<>();
        //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");



        Log.d("Upper ","Upper Error geldi");
        dbOperation db = new dbOperation(getApplicationContext());

        if(db.takeAllClothes(drawer.getDrawerID()) != null){
            clothesList = db.takeAllClothes(drawer.getDrawerID());
        }
        if(clothesList != null && clothesList.isEmpty()){
            String _Date = "DD-MM-YYYY";
            Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.wardrobe_logo);

            Log.d("Inner"," Inner Error geldi");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            icon.compress(Bitmap.CompressFormat.PNG,50,outputStream);
            byte[] byteArray = outputStream.toByteArray();

            Clothes clothesItem = new Clothes(1,"TYPE","COLOR","PATTERN",_Date,999,byteArray,drawer.getDrawerID());
            clothesList.add(clothesItem);
        }
        db.close();
    }

    public void buildRecyclerViewClothes() {
        mRecyclerViewClothes = findViewById(R.id.recyclerView_clothes);
        mRecyclerViewClothes.setHasFixedSize(true);
        mLayoutManagerClothes = new LinearLayoutManager(this);
        mAdapterClothes = new ClothesAdapter(clothesList);

        mRecyclerViewClothes.setLayoutManager(mLayoutManagerClothes);
        mRecyclerViewClothes.setAdapter(mAdapterClothes);

        mAdapterClothes.setOnItemClickListener(new ClothesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //int tempQuestionID = clothesList.get(position).getQuestionID();
                Toast.makeText(getApplicationContext(),"It works fine" ,Toast.LENGTH_LONG).show();
                if(tempCombination!=null){
                    selectItem(position);
                }
                /*
                Intent intent = new Intent(getApplicationContext(),EditQuestionActivity.class);
                intent.putExtra("questionIDPass",tempQuestionID);
                intent.putExtra("questionPass",questionList.get(position));
                startActivity(intent);*/
            }

            @Override
            public void onItemDelete(int position) {
                final int deleted = position;
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                removeItem(deleted);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ClothesActivity.this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }

            @Override
            public void onItemUpdate(int position) {
                final int updated = position;
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                editItem(updated);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ClothesActivity.this);
                builder.setMessage("Do you really want to update?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });
    }
}