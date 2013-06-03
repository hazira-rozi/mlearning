package com.uny.mlearning;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView.OnItemClickListener;

public class DaftarMateri extends Activity {
	private ListView listMateri;
	private JSONObject jObject;
	private TextView txtCoba;
	private Spinner spin_kategori;
	private String extKelas;
	private String extMapel;
	private int pilihan;
	private String[] kelas;
	private String[] pelajaran;
	private String[] judul;
	private String[] diskripsi;
	private String[] kategori;
	private String[] urlMat;
	public String[] spin_kat = {"Materi","Tugas"};
	private String xResult ="";
	private String url = "http://unymlearning.comlu.com/android/readmateri.php";
	public DaftarMateri() {
		xResult = getRequest(url);
       	try {
 			parse();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
	}
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baca);
        listMateri = (ListView) findViewById(R.id.listMateri);
        spin_kategori = (Spinner) findViewById(R.id.spin_kategori);
        txtCoba = (TextView) findViewById(R.id.txtCoba);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spin_kat);
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin_kategori.setAdapter(aa);
		Bundle extras = getIntent().getExtras();
        if (extras == null) {
        	return;
        }
        String value1 = extras.getString("value1");
        String value2 = extras.getString("value2");
        if (value1 != null ) {
        	extKelas = value1;
        }
        
        if (value2 != null ) {
        	extMapel = value2;
        }
        
        
        spin_kategori.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				pilihMateri();
				
			}

			
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}

        });
        
        listMateri.setClickable(true);
        listMateri.setOnItemClickListener(new OnItemClickListener() {

			
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				HashMap<String, String> o = (HashMap<String, String>) listMateri.getItemAtPosition(arg2);
				String judulWebView = o.get("judul");
				String urlView = o.get("url");
				callIntent(judulWebView, urlView);
				
			}
        	
        	
        });
        
        
        // ...
        
    }
    
    public void callIntent(String jdl, String urlView) {
		Intent i = new Intent(this, TampilMateri.class);
    	i.putExtra("value1", jdl);
    	i.putExtra("value2", urlView);
    	startActivity(i);
	}
    
    

    
    public void pilihMateri() {
    	ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
    	HashMap<String, String> map;
    	for (int i=0; i<kelas.length; i++) {
    		if ((extKelas.equals(kelas[i])) && (extMapel.equals(pelajaran[i]))) {
    			if (spin_kategori.getSelectedItem().toString().equals(kategori[i])) {
            		map = new HashMap<String, String>();
                    map.put("judul", judul[i]);
                    map.put("diskripsi", diskripsi[i]);
                    map.put("url", urlMat[i]);
                    mylist.add(map);
            		
            	}
    		}
        }
    	SimpleAdapter mat = new SimpleAdapter(this, mylist, R.layout.bacamateri, new String[] {"judul", "diskripsi"}, new int[] {R.id.textView_judul, R.id.textView_diskripsi});
    	listMateri.setAdapter(mat);
    }
    
public void parse() throws Exception {
		
		jObject = new JSONObject(xResult);

		JSONArray menuitemArray = jObject.getJSONArray("materi");
		String[] kelas = new String[menuitemArray.length()];
		String[] pelajaran = new String[menuitemArray.length()];
		String[] judul = new String[menuitemArray.length()];
		String[] diskripsi = new String[menuitemArray.length()];
		String[] kategori = new String[menuitemArray.length()];
		String[] urlMat = new String[menuitemArray.length()];
		for (int i = 0; i < menuitemArray.length(); i++) {
			kelas[i] = menuitemArray.getJSONObject(i).getString("kelas").toString();
			pelajaran[i] = menuitemArray.getJSONObject(i).getString("pelajaran").toString();
			judul[i] = menuitemArray.getJSONObject(i).getString("judul").toString();
			diskripsi[i] = menuitemArray.getJSONObject(i).getString("diskripsi").toString();
			kategori[i] = menuitemArray.getJSONObject(i).getString("kategori").toString();
			urlMat[i] = menuitemArray.getJSONObject(i).getString("url").toString();
		}
		this.kelas = kelas;
		this.pelajaran = pelajaran;
		this.judul = judul;
		this.diskripsi = diskripsi;
		this.kategori = kategori;
		this.urlMat = urlMat;
		
	}

	public String getRequest(String Url){

       String sret="";
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(Url);
        try{
          HttpResponse response = client.execute(request);
          sret =request(response);

        }catch(Exception ex){
        	Toast.makeText(this,"Gagal "+sret, Toast.LENGTH_SHORT).show();
        }
        return sret;

    }

	public static String request(HttpResponse response){
        String result = "";
        try{
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                str.append(line + "\n");
            }
            in.close();
            result = str.toString();
        }catch(Exception ex){
            result = "Error";
        }
        return result;
    }
}