package com.uny.mlearning;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuUtama extends Activity {
	private Button btnMenuLogin;
	private Button btnMenuRegister;
	private Button btnMenuBantuan;
	private Button btnMenuKeluar;
	Dialog dlg;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        btnMenuLogin = (Button) findViewById(R.id.btnMenuLogin);
        btnMenuRegister = (Button) findViewById(R.id.btnMenuRegister);
        btnMenuBantuan = (Button) findViewById(R.id.btnMenuBantuan);
        btnMenuKeluar = (Button) findViewById(R.id.btnMenuKeluar);
        btnMenuLogin.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View arg0) {
				menuLogin();
			}
		});
        
        btnMenuRegister.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View v) {
				menuRegister();
			}
		});
        
        btnMenuBantuan.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View arg0) {
				dlg = new Dialog(MenuUtama.this);
		    	dlg.setContentView(R.layout.bantuan);
		    	dlg.setTitle("Bantuan");
		    	dlg.setCancelable(true);
		        dlg.show();
			}
		});
        
        btnMenuKeluar.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View arg0) {
				finish();
			}
		});
        
    }
    
    public void onAbout(View v)
    {
    	Toast.makeText (getApplicationContext(),"Aplikasi M-Learning merupakan E-Learning pada perangkat bergerak, jika belum memiliki account harap register" , Toast.LENGTH_LONG).show ();
    }
    
    public void onSearch(View v)
    {
    	startActivity (new Intent(getApplicationContext(), SearchActivity.class));
    }
    
    public void onHome (View v)
    {
    	return2Home(this);
    }
    
    public void return2Home(Context context)
    {
        final Intent intent = new Intent(context, MenuUtama.class);
        intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity (intent);
    }
    
    public void menuLogin() {
    	Intent i = new Intent(this, Login.class);
    	startActivity(i);
    }
    
    public void menuRegister() {
    	Intent i = new Intent(this, Register.class);
    	startActivity(i);
    }
    
    
}