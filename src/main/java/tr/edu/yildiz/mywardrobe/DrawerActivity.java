package tr.edu.yildiz.mywardrobe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DrawerActivity extends AppCompatActivity {
    ArrayList<Drawer> drawerList;

    private RecyclerView mRecyclerView;
    private DrawerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView emptyView;
    private FloatingActionButton buttonAdd;
    private User tempUser;
    private ImageView buttonDeleteDrawer;
    private User tempCombinationUser;
    private Combination tempCombination;
    /*private Button buttonInsert;
    private Button buttonRemove;
    private EditText editTextInsert;
    private EditText editTextRemove;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Intent intent = getIntent();
        final User user = intent.getParcelableExtra("Logged in User Drawer");
        tempUser = user;
        final User userCombination = intent.getParcelableExtra("Logged in User from Combination");
        final Combination combination = intent.getParcelableExtra("Logged in Combination");
        tempCombinationUser = userCombination;
        tempCombination = combination;

        emptyView = findViewById(R.id.empty_view);



        /*
        buttonInsert = findViewById(R.id.button_insert);
        buttonRemove = findViewById(R.id.button_remove);
        editTextInsert = findViewById(R.id.edittext_insert);
        editTextRemove = findViewById(R.id.edittext_remove);*/
        buttonAdd = findViewById(R.id.add_drawer_button);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDialogBox();
            }

        });

        if(userCombination!=null){
            buttonAdd.setVisibility(View.GONE);
            tempUser = userCombination;
        }

        createDrawerList(tempUser);
        buildRecyclerViewDrawer();
        /*
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(editTextInsert.getText().toString());
                insertItem(position);
            }
        });
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(editTextRemove.getText().toString());
                removeItem(position);
            }
        });*/
    }

    public void insertItem(String name) {

        dbOperation db = new dbOperation(getApplicationContext());
        Drawer drawer = new Drawer();
        drawer.setName(name);
        drawer.setUserID(0);

        boolean result = db.drawerAdd(drawer);
        if  (result)  {
            //finish();
            drawerList.add(new Drawer(db.drawerTakeID(name),0,name));
            Toast.makeText(getApplicationContext(),"You have successfully added a " +drawerList.get(drawerList.size()-1).getName() + " drawer " + drawerList.get(drawerList.size()-1).getDrawerID(),Toast.LENGTH_LONG).show();
        }  else {
            Toast.makeText(getApplicationContext(),"Something went wrong, try again!",Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    public void insertItem(String name,int userID) {

        dbOperation db = new dbOperation(getApplicationContext());
        Drawer drawer = new Drawer();
        drawer.setName(name);
        drawer.setUserID(userID);

        boolean result = db.drawerAdd(drawer);
        if  (result)  {
            //finish();
            drawerList.add(new Drawer(db.drawerTakeID(name),userID,name));
            Toast.makeText(getApplicationContext(),"You have successfully added a " +drawerList.get(drawerList.size()-1).getName() + " drawer " + drawerList.get(drawerList.size()-1).getDrawerID(),Toast.LENGTH_LONG).show();
        }  else {
            Toast.makeText(getApplicationContext(),"Something went wrong, try again!",Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    public void removeItem(int position) {

        //Toast.makeText(getApplicationContext(),drawerList.get(position).getName(),Toast.LENGTH_LONG).show();

        dbOperation db = new dbOperation(getApplicationContext());

        boolean result = db.drawerDelete(drawerList.get(position));
        db.close();
        if  (result)  {
            //finish();
            Toast.makeText(getApplicationContext(),"You have successfully deleted drawer",Toast.LENGTH_LONG).show();
            drawerList.remove(position);
        }  else {
            Toast.makeText(getApplicationContext(),"Something went wrong, try again!",Toast.LENGTH_LONG).show();
        }
        mAdapter.notifyItemRemoved(position);
    }



    public void createDrawerList(User user){
        drawerList = new ArrayList<>();

        if(user==null){
            dbOperation db = new dbOperation(getApplicationContext());
            if(db.takeAllDrawers(0) != null){
                drawerList = db.takeAllDrawers(0);
            }
            if(drawerList != null && drawerList.isEmpty()){
                insertItem("Drawer");
            }
            db.close();
        }
        else{
            dbOperation db = new dbOperation(getApplicationContext());
            if(db.takeAllDrawers(user.getEmail()) != null){
                drawerList = db.takeAllDrawers(user.getEmail());
            }
            if(drawerList != null && drawerList.isEmpty()){
                insertItem("Drawer " +user.getName(),db.userTakeID(user.getEmail()));
            }
            db.close();
        }

        //dbOperation db = new dbOperation(getApplicationContext());
        //questionList = db.takeAllQuestions(1);

        /*
        drawerList.add(new Drawer(0,"Başüstü", new Clothes()));
        drawerList.add(new Drawer(0,"Surat", new Clothes()));
        drawerList.add(new Drawer(0,"Üst Beden", new Clothes()));
        drawerList.add(new Drawer(0,"Alt beden", new Clothes()));
        drawerList.add(new Drawer(0,"Ayak", new Clothes()));
         */
    }

    public void buildRecyclerViewDrawer() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new DrawerAdapter(drawerList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new DrawerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                /*Toast toast = Toast.makeText(getApplicationContext(),drawerList.get(position).getName() + drawerList.get(position).getDrawerID(),Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();*/

                if(tempCombinationUser==null){
                    Intent intent = new Intent(DrawerActivity.this,ClothesActivity.class);

                    intent.putExtra("Drawer Items",drawerList.get(position)); // Belirtilen Drawer'a git
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(DrawerActivity.this,ClothesActivity.class);
                    intent.putExtra("Combination",tempCombination);
                    intent.putExtra("Drawer Items Combination   ",drawerList.get(position)); // Belirtilen Drawer'a git
                    startActivity(intent);
                }
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

                AlertDialog.Builder builder = new AlertDialog.Builder(DrawerActivity.this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });
    }

    public void addDialogBox(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(DrawerActivity.this,R.style.Theme_AppCompat_Light_Dialog_Alert);
        alertDialog.setTitle("Add New Drawer");
        final EditText input = new EditText(getApplicationContext());
        alertDialog.setView(input);
        //alertDialog.setIcon()

        alertDialog.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String item = input.getText().toString();
                //insertItem(item);
                dbOperation db = new dbOperation(getApplicationContext());
                //drawerList.add(new Drawer(0,item));
                if(tempUser!=null){
                    insertItem(item,db.userTakeID(tempUser.getEmail()));

                }
                else{
                    insertItem(item);
                }
                db.close();
                mAdapter.notifyDataSetChanged();
                //Toast toast = Toast.makeText(getApplicationContext(),"Drawer Added",Toast.LENGTH_SHORT);
                //toast.setGravity(Gravity.CENTER, 0, 0);
                //toast.show();
            }
        }).create();

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).create();

        alertDialog.show();
    }
}