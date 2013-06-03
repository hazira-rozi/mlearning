package com.uny.mlearning;

import java.util.HashMap;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MenuGuru extends Activity implements	AdapterView.OnItemSelectedListener {
	private String user;
	private String[] menu;
	private ListView listSubMenu;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		super.setContentView(R.layout.submenu);
		listSubMenu = (ListView) findViewById(R.id.listSubMenu);
		this.menu = new String[] { "Upload Materi", "Lihat Materi", "Baca Tugas Siswa"};
		listSubMenu.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		String user = extras.getString("user");
		if (user !=null) {
			this.user = user;
		}
		
		listSubMenu.setClickable(true);
	    listSubMenu.setOnItemClickListener(new OnItemClickListener() {

			
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {		
				Object o = listSubMenu.getItemAtPosition(arg2);
				String keyword = o.toString();
				if (keyword.equals(menu[0])) {
					callUploadFile();
				}
				else if (keyword.equals(menu[1])) {
					callListMateri("Materi");
				}
				else if (keyword.equals(menu[2])) {
					callListMateri("Tugas");
				}
				
			}
	    });
	}

	
	public void callListMateri(String s) {
		Intent i = new Intent(this, ListMateri.class);
		i.putExtra("value1", s );
		startActivity(i);
		
	}
	public void callUploadFile() {
		Intent i = new Intent(this, PilihPelajaran.class);
		i.putExtra("user", this.user);
		i.putExtra("kategori", "Materi");
		startActivity(i);
		
	}
	
	
	
	public void onItemSelected(AdapterView<?> parent, View v, int position,	long id) {
		
	}

	public void onNothingSelected(AdapterView<?> parent) {
		
	}
	
	public void onAbout(View v)
    {
    	Toast.makeText (getApplicationContext(),"Menu Guru digunakan untuk melihat tugas siswa, melihat materi dan mengupload materi" , Toast.LENGTH_LONG).show ();
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
