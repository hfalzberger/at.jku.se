package at.jku.stockticker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	SessionManager session;
	
	private EditText usernameText;
	private EditText passwordText;
	private Button loginBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		usernameText = (EditText) findViewById(R.id.username);
		passwordText = (EditText) findViewById(R.id.password);
		loginBtn = (Button) findViewById(R.id.loginButton);
		
        loginBtn.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View arg0) {
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();
                 
                if(username.trim().length() > 0 && password.trim().length() > 0){
                    if(username.equals("test") && password.equals("test")){
                        session.createLoginSession(username, password);
                         
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                    	Toast.makeText(LoginActivity.this, "Username/Password is incorrect", Toast.LENGTH_LONG).show();
                    }               
                }else{
                    Toast.makeText(LoginActivity.this, "Please enter username and password", Toast.LENGTH_LONG).show();
                }
            }
        });
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
