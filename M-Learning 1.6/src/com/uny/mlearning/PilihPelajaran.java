package com.uny.mlearning;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PilihPelajaran extends Activity implements	AdapterView.OnItemSelectedListener {
	TextView selection_kelas;
	TextView selection_mapel;
	Button btnMateri;
	private Button btnBacaMateri;
	private String user;
	private String kategori;
	Spinner spin_mapel;
	Spinner spin_kelas;
	String[] kelas = { "X A", "X B", "X C", "X D", "X E", "XI IPA A", "XI IPA B", "XI IPA C", "XI IPS A", "XI IPS B", "XII IPA A", "XII IPA B", "XII IPA C", "XII IPS A", "XII IPS B"};
	
    HashMap<String, String []> hash_kelas = new HashMap<String, String []>();

	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		generateData();
		setContentView(R.layout.pilih);
		selection_kelas = (TextView) findViewById(R.id.selection_kelas);
		spin_kelas = (Spinner) findViewById(R.id.spinner_kelas);
		btnBacaMateri = (Button) findViewById(R.id.btnBacaMateri);
		spin_kelas.setOnItemSelectedListener(this);
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, kelas);
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin_kelas.setAdapter(aa);
		spin_mapel = (Spinner) findViewById(R.id.spinner_mapel);
		btnMateri = (Button) findViewById(R.id.btnMateri);
		btnBacaMateri.setVisibility(View.GONE);
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		String user = extras.getString("user");
		if (user !=null) {
			this.user = user;
		}
		String kategori = extras.getString("kategori");
		if (user !=null) {
			this.kategori = kategori;
			if (this.kategori.equals("Tugas")) {
        		btnMateri.setText("Upload Tugas");
        	}
			else if (this.kategori.equals("Materi")) {
        		btnMateri.setText("Upload Materi");
        	}
		}
		btnMateri.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View v) {
				String selected_kelas = spin_kelas.getSelectedItem().toString();
				String selected_mapel = spin_mapel.getSelectedItem().toString();
				callUploadFile(selected_kelas,selected_mapel);
				
			}
		});
		
		btnBacaMateri.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View arg0) {
				bacaMateri();
			}
		});
		

	}
	
	
	public void bacaMateri() {
		String sk = spin_kelas.getSelectedItem().toString();
		String sm = spin_mapel.getSelectedItem().toString();
		Intent i = new Intent(this, DaftarMateri.class);
    	i.putExtra("value1", sk );
    	i.putExtra("value2", sm );
    	startActivity(i);
	}
	
	public void callUploadFile(String sk, String sm) {
		Intent i = new Intent(this, UploadFile.class);
    	i.putExtra("value1", sk );
    	i.putExtra("value2", sm );
    	i.putExtra("user", this.user);
    	i.putExtra("kategori", this.kategori);
    	startActivity(i);
	}

	public void onItemSelected(AdapterView<?> parent, View v, int position,	long id) {
		fillCombomapel(kelas[position]);
	}

	public void onNothingSelected(AdapterView<?> parent) {
		Toast.makeText(this, "Silahkan Pilih kelas", Toast.LENGTH_LONG).show();
	}

	private void generateData(){
		for (int i=0; i<kelas.length; i++) {
			if (kelas[i].substring(0, 2).equalsIgnoreCase("X ")) {
				hash_kelas.put(kelas[i], new String[] {"b.indonesia", "matematika", "agama", "b.inggris", "sejarah ", "BK", "ketrampilan", "penjaskes", "TIK", "seni Budaya", "PKN", "B.jawa", "sosiologi", "geografi", "ekonomi", "kimia", "biologi", "fisika"});
			}
			else if(kelas[i].substring(0, 6).equalsIgnoreCase("XI IPA")) {
				hash_kelas.put(kelas[i], new String[] {"kimia", "b.indonesia", "matematika", "agama", "biologi", "fisika", "b.inggris", "sejarah", "BK", "ketrampilan", "penjaskes", "TIK", "seni Budaya", "PKN", "B.jawa"});
			}
			else if(kelas[i].substring(0, 6).equalsIgnoreCase("XI IPS")) {
				hash_kelas.put(kelas[i], new String[] {"b.indonesia", "matematika", "agama", "b.inggris", "sejarah ", "BK", "ketrampilan", "penjaskes", "TIK", "seni Budaya", "PKN", "B.jawa", "sosiologi", "geografi", "ekonomi"});
			}
			else if(kelas[i].substring(0, 7).equalsIgnoreCase("XII IPA")) {
				hash_kelas.put(kelas[i], new String[] {"kimia", "b.indonesia", "matematika", "english conversation", "agama", "biologi", "fisika", "b.inggris", "sejarah ", "BK", "ketrampilan", "penjaskes", "TIK", "seni Budaya", "PKN", "B.jawa"});
			}
			else if(kelas[i].substring(0, 7).equalsIgnoreCase("XII IPS")) {
				hash_kelas.put(kelas[i], new String[] {"b.indonesia", "matematika", "agama", "b.inggris", "sejarah", "BK", "ketrampilan", "penjaskes", "TIK", "seni Budaya", "PKN", "B.jawa", "sosiologi", "geografi", "ekonomi", "akutansi"});
			}
					
		}
	}
	private void fillCombomapel(String skelas){
		String[] mapel = null;
		ArrayAdapter<String> aa = null;
		try {
			mapel = hash_kelas.get(skelas);
			aa = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, mapel);
		} catch (NullPointerException e) {
			aa = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, new String[] {});
		}
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin_mapel.setAdapter(aa);
	}
	
	public void onAbout(View v)
    {
    	Toast.makeText (getApplicationContext(),"Pilih kelas dan mata pelajaran untuk mnelihat materi/tugas" , Toast.LENGTH_LONG).show ();
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