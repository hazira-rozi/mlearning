package com.uny.mlearning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity
{
	public  void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
     }
	
	public void onAbout(View v)
    {
    	Toast.makeText (getApplicationContext(),"Menu search belum berfungsi" , Toast.LENGTH_LONG).show ();
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
	
	
}
