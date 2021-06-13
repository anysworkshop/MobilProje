package tr.edu.yildiz.mywardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    private User user;
    private int guest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("Logged in User");
        guest = intent.getIntExtra("Guest Login",0);

    }

    public void openDrawerPage(View view) {
        Intent intent = new Intent(this,DrawerActivity.class);
        intent.putExtra("Logged in User Drawer",user);
        startActivity(intent);
    }

    public void openCombinationPage(View view) {
        Intent intent = new Intent(this,CombinationActivity.class);
        intent.putExtra("Logged in User Combination",user);
        startActivity(intent);
    }
}