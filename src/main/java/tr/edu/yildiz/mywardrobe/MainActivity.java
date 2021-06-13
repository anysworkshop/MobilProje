package tr.edu.yildiz.mywardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {


    Button loginButton;
    Button signUpButton;
    EditText editTextEmail;
    EditText editTextPassword;
    int attempt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = (EditText) findViewById(R.id.loginEmail);
        editTextPassword = (EditText) findViewById(R.id.loginPassword);

        loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                dbOperation db = new dbOperation(getApplicationContext());
                User user = db.login(email,password);
                if (user != null) {
                    openMenu(user);
                    Toast.makeText(getApplicationContext(),"You have successfully logged in",Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    attempt++;
                    Toast.makeText(getApplicationContext(),"Email and Password do not match!",Toast.LENGTH_LONG).show();
                    if(attempt >= 3){
                        attempt = 0;
                        openDialog();
                    }
                }

            }
        });

        signUpButton = (Button) findViewById(R.id.register);
        signUpButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openRegistration();
            }
        });
    }

    public void openDialog(){
        Dialogue dialogue = new Dialogue();
        dialogue.show(getSupportFragmentManager(),"Dialogue Example");
    }

    public void openRegistration(){
        Intent intent = new Intent(this,RegistrationActivity.class);
        startActivity(intent);
    }

    public void openMenu(User user){
        Intent intent = new Intent(this,MainMenu.class);
        intent.putExtra("Logged in User",user);
        startActivity(intent);
    }
    public void guestSign(View view) {
        Intent intent = new Intent(this,MainMenu.class);
        //intent.putExtra("Logged in User",user);
        intent.putExtra("Guest Login",0);
        startActivity(intent);
    }
}