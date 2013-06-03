package com.uny.mlearning;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView.OnItemSelectedListener;

public class Register extends Activity {
	private EditText editReg_user;
	private EditText editReg_pass;
	private EditText editReg_nama;
	private EditText editReg_nis;
	private EditText editReg_email;
	private Spinner spin_kelas;
	private Spinner spin_tipe;
	private Button btnRegister;
	private Button btnClear;
	private String[] kelas = { "X A", "X B", "X C", "X D", "X E", "XI IPA A", "XI IPA B", "XI IPA C", "XI IPS A", "XI IPS B", "XII IPA A", "XII IPA B", "XII IPA C", "XII IPS A", "XII IPS B"};
	private String[] tipe = {"siswa", "guru"};
	private String url = "http://unymlearning.comlu.com/android/adduser.php";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        editReg_user = (EditText) findViewById(R.id.editReg_user);
        editReg_pass = (EditText) findViewById(R.id.editReg_pass);
        editReg_nama = (EditText) findViewById(R.id.editReg_nama);
        editReg_nis = (EditText) findViewById(R.id.editReg_nis);
        editReg_email = (EditText) findViewById(R.id.editReg_email);
        spin_tipe = (Spinner) findViewById(R.id.spin_tipe);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, tipe);
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin_tipe.setAdapter(aa);
        spin_kelas = (Spinner) findViewById(R.id.spin_kelas);
        ArrayAdapter<String> aa1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, kelas);
		aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin_kelas.setAdapter(aa1);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnClear = (Button) findViewById(R.id.btnClear);
        
        spin_tipe.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if(spin_tipe.getSelectedItem().toString().equals("guru")) {
					spin_kelas.setVisibility(View.GONE);
				}
				else {
					spin_kelas.setVisibility(View.VISIBLE);
				}
				
			}

			
			public void onNothingSelected(AdapterView<?> arg0) {		
			}

        });
        
        btnRegister.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View arg0) {
				if (cekUser()) {
					registerUser();
				}
				
			}
		});
        
        btnClear.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View arg0) {
				editReg_user.setText("");
		        editReg_pass.setText("");
		        editReg_nama.setText("");
		        editReg_nis.setText("");
		        editReg_email.setText("");
				
			}
		});
        

	}
	
	public boolean cekUser() {
		String user = editReg_user.getText().toString();
		user = user.trim();
		String userReplace = user.replace(" ", "");
		if (user.length()!=userReplace.length() || user.length()>8) {
			Toast.makeText(this, "Maaf nama user tidak boleh menggunakan spasi dan harus kurang dari 8 karakter ", Toast.LENGTH_LONG).show();
			return false;
		}
		else {
			return true;
		}
	}
	
	public void registerUser() {
		try {
			String user = URLEncoder.encode(editReg_user.getText()
					.toString().trim(), "utf-8");
			String pass = URLEncoder.encode(editReg_pass.getText()
					.toString(), "utf-8");
			String tipe = URLEncoder.encode(spin_tipe.getSelectedItem().toString(), "utf-8");
			String nama = URLEncoder.encode(editReg_nama.getText()
					.toString(), "utf-8");
			String nis = URLEncoder.encode(editReg_nis.getText()
					.toString(), "utf-8");
			String email = URLEncoder.encode(editReg_email.getText()
					.toString(), "utf-8");
			String kelas = URLEncoder.encode(spin_kelas.getSelectedItem().toString(), "utf-8");
			
			
			url += "?user=" + user + "&password=" + pass + "&nama=" + nama + "&tipe=" + tipe + "&nis=" + nis + "&email=" + email + "&kelas=" + kelas;
			getRequest(url);			
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void getRequest(String Url) {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = client.execute(request);
			String result = request(response);
			if (result.substring(0, 5).equalsIgnoreCase("Error")) {
				Toast.makeText(this, "Registrasi gagal, mohon lengkapi form atau ganti username lain", Toast.LENGTH_LONG).show();
			}
			else {
				Toast.makeText(this, "Anda berhasil teregistrasi dalam M-Learning" ,Toast.LENGTH_SHORT).show();
				Intent i = new Intent(this, Login.class);
				startActivity(i);
			}
		} catch (Exception ex) {
			Toast.makeText(this, "Maaf koneksi ke server gagal !", Toast.LENGTH_SHORT)
					.show();
		}

	}

	public static String request(HttpResponse response) {
		String result = "";

		try {
			InputStream in = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder str = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				str.append(line + "\n");
			}
			in.close();
			result = str.toString();
		} catch (Exception ex) {
			result = "Error";
		}
		return result;
	}
	
	public void onAbout(View v)
    {
    	Toast.makeText (getApplicationContext(),"Semua form harap diisi. Username tidak boleh lebih dari 8 kata" , Toast.LENGTH_LONG).show ();
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
