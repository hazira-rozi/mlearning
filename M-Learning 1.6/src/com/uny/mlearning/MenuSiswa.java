package com.uny.mlearning;

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
import android.widget.AdapterView.OnItemClickListener;

public class MenuSiswa extends Activity implements	AdapterView.OnItemSelectedListener {
	private String ts;
	private String user;
	private String[] menu;
	private ListView listSubMenu;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		super.setContentView(R.layout.submenu);
		listSubMenu = (ListView) findViewById(R.id.listSubMenu);

		menu = new String[] { "Baca Materi Pelajaran", "Upload Tugas", "Lihat Tugas Hasil Upload"};
		listSubMenu.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		String val = extras.getString("tugassendiri");
		if (val !=null) {
			ts = val;
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
					callListMateri();
				}
				else if (keyword.equals(menu[1])) {
					callUploadFile();
				}
				else if (keyword.equals(menu[2])) {
					callListMateriHasil();
				}
				
				
			}
	    });
	}

	
	public void callListMateri() {
		Intent i = new Intent(this, ListMateri.class);
		i.putExtra("value1", "Materi");
		startActivity(i);
	}
	public void callListMateriHasil() {
		Intent i = new Intent(this, ListMateri.class);
		i.putExtra("ts", ts);
		startActivity(i);
	}
	
	public void callUploadFile() {
		Intent i = new Intent(this, PilihPelajaran.class);
		i.putExtra("user", ts);
		i.putExtra("kategori", "Tugas");
		startActivity(i);
	}


	
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {	
	}


	
	public void onNothingSelected(AdapterView<?> arg0) {	
	}
	
	public void onAbout(View v)
    {
    	Toast.makeText (getApplicationContext(),"Menu siswa digunakan untuk mengupload tugas, melihat materi dan melihat tugas yang pernah di upload"  , Toast.LENGTH_LONG).show ();
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