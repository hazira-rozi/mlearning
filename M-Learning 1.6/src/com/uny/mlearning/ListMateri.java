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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;



public class ListMateri extends Activity implements	AdapterView.OnItemSelectedListener {
	private TextView textListMat_kelas;
	private TextView textListMat_mapel;
	private Spinner spinListMateri_kelas;
	private Spinner spinListMateri_mapel;
	String[] daftarKelas = { "X A", "X B", "X C", "X D", "X E", "XI IPA A", "XI IPA B", "XI IPA C", "XI IPS A", "XI IPS B", "XII IPA A", "XII IPA B", "XII IPA C", "XII IPS A", "XII IPS B"};
    HashMap<String, String []> hash_kelas = new HashMap<String, String []>();
    
    private ListView listMateri_listmat;
	private JSONObject jObject;
	private String extKelas;
	private String extMapel;
	private String ktgr =  "Materi";
	private String[] kelas;
	private String[] pelajaran;
	private String[] judul;
	private String[] diskripsi;
	private String[] kategori;
	private String[] urlMat;
	private String[] user;
	private String xResult ="";
	private String url = "http://unymlearning.comlu.com/android/readmateri.php";
	

	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		generateData();
		setContentView(R.layout.listmateri);
		textListMat_kelas = (TextView) findViewById(R.id.textListMat_kelas);
		textListMat_mapel = (TextView) findViewById(R.id.textListMat_mapel);
		listMateri_listmat = (ListView) findViewById(R.id.listMateri_listmat);
		spinListMateri_kelas = (Spinner) findViewById(R.id.spinListMateri_kelas);
		spinListMateri_kelas.setOnItemSelectedListener(this);
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, daftarKelas);
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinListMateri_kelas.setAdapter(aa);
		spinListMateri_mapel = (Spinner) findViewById(R.id.spinListMateri_mapel);
		xResult = getRequest(url);
       	try {
 			parse();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		String value1 = extras.getString("value1");
		String ts = extras.getString("ts");
		if (value1 !=null) {
			ktgr = value1;
		}
		if (ts !=null) {
			textListMat_kelas.setText("Tugas yang pernah di upload : ");
			textListMat_mapel.setVisibility(View.GONE);
			spinListMateri_kelas.setVisibility(View.GONE);
			spinListMateri_mapel.setVisibility(View.GONE);
			tampilHasilTugas(ts);
		}
		
		spinListMateri_mapel.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				pilihMateri();
				
			}

			
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}

        });
		
		 listMateri_listmat.setClickable(true);
	        listMateri_listmat.setOnItemClickListener(new OnItemClickListener() {

				
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					HashMap<String, String> o = (HashMap<String, String>) listMateri_listmat.getItemAtPosition(arg2);
					String judulWebView = o.get("judul");
					String urlView = o.get("url");
					callIntent(judulWebView, urlView);
					
				}
	        	
	        	
	        });
	        
	}
	
	public void tampilHasilTugas(String s) {
		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
    	HashMap<String, String> map;
    	for (int i=0; i<kelas.length; i++) {
    		if (s.equals(user[i])) {
    			if (kategori[i].equals("Tugas")) {
            		map = new HashMap<String, String>();
                    map.put("judul", judul[i]);
                    map.put("user", "oleh : "+user[i]);
                    map.put("diskripsi", diskripsi[i]);
                    map.put("url", urlMat[i]);
                    mylist.add(map);
            		
            	}
    		}
    		
        }
    	SimpleAdapter mat = new SimpleAdapter(this, mylist, R.layout.bacamateri, new String[] {"judul", "user", "diskripsi"}, new int[] {R.id.textView_judul, R.id.textView_user, R.id.textView_diskripsi});
    	listMateri_listmat.setAdapter(mat);
	}
	
	
	public void callIntent(String jdl, String urlView) {
		Intent i = new Intent(this, TampilMateri.class);
    	i.putExtra("value1", jdl);
    	i.putExtra("value2", urlView);
    	startActivity(i);
	}
	
	
	public void onItemSelected(AdapterView<?> parent, View v, int position,	long id) {
		fillCombomapel(daftarKelas[position]);
	}

	public void onNothingSelected(AdapterView<?> parent) {
		Toast.makeText(this, "Silahkan Pilih kelas", Toast.LENGTH_LONG).show();
	}

	private void generateData(){
		for (int i=0; i<daftarKelas.length; i++) {
			if (daftarKelas[i].substring(0, 2).equalsIgnoreCase("X ")) {
				hash_kelas.put(daftarKelas[i], new String[] {"b.indonesia", "matematika", "agama", "b.inggris", "sejarah ", "BK", "ketrampilan", "penjaskes", "TIK", "seni Budaya", "PKN", "B.jawa", "sosiologi", "geografi", "ekonomi", "kimia", "biologi", "fisika"});
			}
			else if(daftarKelas[i].substring(0, 6).equalsIgnoreCase("XI IPA")) {
				hash_kelas.put(daftarKelas[i], new String[] {"kimia", "b.indonesia", "matematika", "agama", "biologi", "fisika", "b.inggris", "sejarah", "BK", "ketrampilan", "penjaskes", "TIK", "seni Budaya", "PKN", "B.jawa"});
			}
			else if(daftarKelas[i].substring(0, 6).equalsIgnoreCase("XI IPS")) {
				hash_kelas.put(daftarKelas[i], new String[] {"b.indonesia", "matematika", "agama", "b.inggris", "sejarah ", "BK", "ketrampilan", "penjaskes", "TIK", "seni Budaya", "PKN", "B.jawa", "sosiologi", "geografi", "ekonomi"});
			}
			else if(daftarKelas[i].substring(0, 7).equalsIgnoreCase("XII IPA")) {
				hash_kelas.put(daftarKelas[i], new String[] {"kimia", "b.indonesia", "matematika", "english conversation", "agama", "biologi", "fisika", "b.inggris", "sejarah ", "BK", "ketrampilan", "penjaskes", "TIK", "seni Budaya", "PKN", "B.jawa"});
			}
			else if(daftarKelas[i].substring(0, 7).equalsIgnoreCase("XII IPS")) {
				hash_kelas.put(daftarKelas[i], new String[] {"b.indonesia", "matematika", "agama", "b.inggris", "sejarah", "BK", "ketrampilan", "penjaskes", "TIK", "seni Budaya", "PKN", "B.jawa", "sosiologi", "geografi", "ekonomi", "akutansi"});
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
		spinListMateri_mapel.setAdapter(aa);
	}
	
	public void pilihMateri() {
		extKelas = spinListMateri_kelas.getSelectedItem().toString();
		extMapel = spinListMateri_mapel.getSelectedItem().toString();
    	ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
    	HashMap<String, String> map;
    	for (int i=0; i<kelas.length; i++) {
    		if ((extKelas.equals(kelas[i])) && (extMapel.equals(pelajaran[i]))) {
    			if (kategori[i].equals(ktgr)) {
            		map = new HashMap<String, String>();
                    map.put("judul", judul[i]);
                    map.put("user", "oleh : "+user[i]);
                    map.put("diskripsi", diskripsi[i]);
                    map.put("url", urlMat[i]);
                    mylist.add(map);
            		
            	}
    		}
    		
        }
    	SimpleAdapter mat = new SimpleAdapter(this, mylist, R.layout.bacamateri, new String[] {"judul", "user", "diskripsi"}, new int[] {R.id.textView_judul, R.id.textView_user, R.id.textView_diskripsi});
    	listMateri_listmat.setAdapter(mat);
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
		String[] user = new String[menuitemArray.length()];
		for (int i = 0; i < menuitemArray.length(); i++) {
			kelas[i] = menuitemArray.getJSONObject(i).getString("kelas").toString();
			pelajaran[i] = menuitemArray.getJSONObject(i).getString("pelajaran").toString();
			judul[i] = menuitemArray.getJSONObject(i).getString("judul").toString();
			diskripsi[i] = menuitemArray.getJSONObject(i).getString("diskripsi").toString();
			kategori[i] = menuitemArray.getJSONObject(i).getString("kategori").toString();
			urlMat[i] = menuitemArray.getJSONObject(i).getString("url").toString();
			user[i] = menuitemArray.getJSONObject(i).getString("user").toString();
		}
		this.kelas = kelas;
		this.pelajaran = pelajaran;
		this.judul = judul;
		this.diskripsi = diskripsi;
		this.kategori = kategori;
		this.urlMat = urlMat;
		this.user = user;
		
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
	
	public void onAbout(View v)
    {
    	Toast.makeText (getApplicationContext(),"Pilih daftar materi/tugas untuk membaca" , Toast.LENGTH_LONG).show ();
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
