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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView.OnItemClickListener;

public class Login extends Activity {
	private JSONObject jObject;
	private EditText editLogin_user;
	private EditText editLogin_pass;
	private Button btnLogin;
	private String xResult="";
	private String url="http://unymlearning.comlu.com/android/readaccount.php";
	private String[] user;
	private String[] password;
	private String[] tipe;
	private String[] nama;
	private String[] nis;
	private String[] email;
	private String[] kelas;
	private int jumlahuser;
	
	
	public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        editLogin_user = (EditText) findViewById(R.id.editLogin_user);
        editLogin_pass = (EditText) findViewById(R.id.editLogin_pass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        
        btnLogin.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View arg0) {
				masukLogin();
				
			}
		});
	}
	
	
	public void masukLogin() {
		xResult = getRequest(url);
       	try {
 			parse();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
		for (int i=0; i<jumlahuser; i++) {
			if ((user[i].equalsIgnoreCase(editLogin_user.getText().toString())) && (password[i].equals(editLogin_pass.getText().toString()))) {
				if (tipe[i].equals("siswa")) {
					Intent s = new Intent(this, MenuSiswa.class);
					String usr = editLogin_user.getText().toString();
					s.putExtra("tugassendiri", usr);
					startActivity(s);
				}
				else if (tipe[i].equals("guru")) {
					Intent s = new Intent(this, MenuGuru.class);
					String usr = editLogin_user.getText().toString();
					s.putExtra("user", usr);
					startActivity(s);
				}
			}
			else if ((user[i].equals(editLogin_user.getText().toString())) && (!password[i].equals(editLogin_pass.getText().toString()))) {
				Toast.makeText(this, "Username dan Password tidak cocok", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	public void parse() throws Exception {
		
		jObject = new JSONObject(xResult);

		JSONArray menuitemArray = jObject.getJSONArray("account");
		this.jumlahuser = menuitemArray.length();
		String[] user = new String[menuitemArray.length()];
		String[] password = new String[menuitemArray.length()];
		String[] tipe = new String[menuitemArray.length()];
		String[] nama = new String[menuitemArray.length()];
		String[] nis = new String[menuitemArray.length()];
		String[] email = new String[menuitemArray.length()];
		String[] kelas = new String[menuitemArray.length()];
		for (int i = 0; i < menuitemArray.length(); i++) {
			user[i] = menuitemArray.getJSONObject(i).getString("user").toString();
			password[i] = menuitemArray.getJSONObject(i).getString("password").toString();
			tipe[i] = menuitemArray.getJSONObject(i).getString("tipe").toString();
			nama[i] = menuitemArray.getJSONObject(i).getString("nama").toString();
			nis[i] = menuitemArray.getJSONObject(i).getString("nis").toString();
			email[i] = menuitemArray.getJSONObject(i).getString("email").toString();
			kelas[i] = menuitemArray.getJSONObject(i).getString("kelas").toString();
		}
		this.user = user;
		this.password = password;
		this.tipe = tipe;
		this.nama = nama;
		this.nis = nis;
		this.email = email;
		this.kelas = kelas;
		
	}

	public String getRequest(String Url){

       String sret="";
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(Url);
        try{
          HttpResponse response = client.execute(request);
          sret =request(response);

        }catch(Exception ex){
        	Toast.makeText(this,"Maaf, koneksi ke server gagal"+sret, Toast.LENGTH_SHORT).show();
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
    	Toast.makeText (getApplicationContext(),"Login berdasarkan username dan password yang pernah didaftarkan. Jika belum register, harap register terlebih dahulu " , Toast.LENGTH_LONG).show ();
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
