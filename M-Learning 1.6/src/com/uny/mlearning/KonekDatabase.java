package com.uny.mlearning;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class KonekDatabase extends Activity {
	private EditText editText_kelas;
	private EditText editText_mapel;
	private EditText editText_judul;
	private EditText editText_diskripsi;
	private EditText editText_kategori;
	private EditText editText_url;
	private EditText editText_user;

	private String url = "http://unymlearning.comlu.com/android/addmateri.php";

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.konek);
		editText_kelas = (EditText) findViewById(R.id.editText_kelas);
		editText_mapel = (EditText) findViewById(R.id.editText_mapel);
		editText_judul = (EditText) findViewById(R.id.editText_judul);
		editText_diskripsi = (EditText) findViewById(R.id.editText_diskripsi);
		editText_kategori = (EditText) findViewById(R.id.editText_kategori);
		editText_url = (EditText) findViewById(R.id.editText_url);
		editText_user = (EditText) findViewById(R.id.editText_user);
		Bundle extras = getIntent().getExtras();
        if (extras == null) {
        	return;
        }
        String value1 = extras.getString("value1");
        String value2 = extras.getString("value2");
        String value3 = extras.getString("value3");
        String value4 = extras.getString("value4");
        String value5 = extras.getString("value5");
        String value6 = extras.getString("value6");
        String value7 = extras.getString("value7");
        

        if (value1 != null ) {
        	editText_kelas.setText(value1);
        }
        
        if (value2 != null ) {
        	editText_mapel.setText(value2);
        }
        if (value3 != null ) {
        	editText_judul.setText(value3);
        }
        
        if (value4 != null ) {
        	editText_diskripsi.setText(value4);
        }
        if (value5 != null ) {
        	editText_kategori.setText(value5);
        }
        if (value6 != null ) {
        	editText_url.setText(value6);
        }
        if (value7 != null ) {
        	editText_user.setText(value7);
        }
        
        try {
			String kelas = URLEncoder.encode(editText_kelas.getText()
					.toString(), "utf-8");
			String mapel = URLEncoder.encode(editText_mapel.getText()
					.toString(), "utf-8");
			String judul = URLEncoder.encode(editText_judul.getText()
					.toString(), "utf-8");
			String diskripsi = URLEncoder.encode(editText_diskripsi.getText()
					.toString(), "utf-8");
			String kategori = URLEncoder.encode(editText_kategori.getText()
					.toString(), "utf-8");
			String urlmat = URLEncoder.encode(editText_url.getText()
					.toString(), "utf-8");
			String user = URLEncoder.encode(editText_user.getText()
					.toString(), "utf-8");
			
			url += "?kelas=" + kelas + "&pelajaran=" + mapel + "&judul=" + judul + "&diskripsi=" + diskripsi + "&kategori=" + kategori + "&url=" + urlmat + "&user=" + user;
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
			Toast.makeText(this, "Tambah Materi " + request(response) + " ",
					Toast.LENGTH_SHORT).show();
		} catch (Exception ex) {
			Toast.makeText(this, "Tambah Data Gagal !", Toast.LENGTH_SHORT)
					.show();
		}
		finish();
  
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
}
