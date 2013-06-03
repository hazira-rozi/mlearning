package com.uny.mlearning;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;

public class UploadFile extends Activity {
    private TextView txtPath;
    private Button btnPilih;
    private Button btnUpload;
    private Button btnClear;
    
    private EditText editUpl_judul;
    private EditText editUpl_diskripsi;
    private ProgressBar progressBar;
    private TextView txtLoading;
    private TextView textUpload_judul;
    private Button btn1;
    public String namaKelas;
    public String namaMapel;
    public String filename;
    public String namaFile;
    public String user;
    public String kategori;
    private String url = "http://unymlearning.comlu.com/android/";
    private String urlServer = url+"handle_upload.php";
    private static final int FILE_SELECT_CODE = 0;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
        	return;
        }
        String value1 = extras.getString("value1");
        String value2 = extras.getString("value2");
        String user =  extras.getString("user");
        String kategori = extras.getString("kategori");
        if (value1 != null ) {
        	namaKelas = value1;
        }
        
        if (value2 != null ) {
        	namaMapel = value2;
        }
        if (user != null ) {
        	this.user = user;
        }
        if (kategori !=null ) {
        	this.kategori = kategori;
        	
        }
        textUpload_judul = (TextView) findViewById(R.id.textUpload_judul);
        editUpl_judul = (EditText) findViewById(R.id.editUpl_judul);
        editUpl_diskripsi = (EditText) findViewById(R.id.editUpl_diskripsi);
        txtPath = (TextView) findViewById(R.id.txtPath);
        btnPilih = (Button) findViewById(R.id.btnPilih);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        btn1 = (Button) findViewById(R.id.btn1);
        btnClear = (Button) findViewById(R.id.btnClear);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        txtLoading = (TextView) findViewById(R.id.txtLoading);
        textUpload_judul.setText("Upload "+this.kategori);
        progressBar.setVisibility(View.GONE);
        btnUpload.setVisibility(View.GONE);
        txtPath.setVisibility(View.GONE);
        btn1.setVisibility(View.GONE);
        btnClear.setVisibility(View.GONE);
   
        btnPilih.setOnClickListener(new View.OnClickListener() {

			
			public void onClick(View arg0) {
				 showFileChooser();
				 
			}
		});
        
        btnUpload.setOnClickListener(new View.OnClickListener() {

			
			public void onClick(View arg0) {
				if (editUpl_judul.getText().toString().equals("")) {
					errorJudul();
				}
				else {
					progressBar.setVisibility(View.VISIBLE);
					txtLoading.setText("Uploading.....");
					uploadFiles(txtPath.getText().toString());
					
				}
				 
				 
			}
		});
        
        btn1.setOnClickListener(new View.OnClickListener() {

			
			public void onClick(View arg0) {
				
				 
			}
		});
        
        btnClear.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View arg0) {
				
			}
		});
        
        
        
        txtPath.addTextChangedListener(new TextWatcher() {
        	public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){
            	txtPath.setVisibility(View.VISIBLE);
            	btnUpload.setVisibility(View.VISIBLE);
            }
        	
        });
        
        
    }
	

	
	public void clearMateri() {
		txtPath.setText("");
		editUpl_judul.setText("");
		editUpl_diskripsi.setText("");
	}
	
	public void errorJudul() {
		Toast.makeText(this, "Judul harus diisi", Toast.LENGTH_SHORT).show();
	}
	
	public void callIntent(String sk, String sm, String jdl, String dsk, String kt, String nf, String usr) {
		Intent i = new Intent(this, KonekDatabase.class);
    	i.putExtra("value1", sk );
    	i.putExtra("value2", sm );
    	i.putExtra("value3", jdl );
    	i.putExtra("value4", dsk );
    	i.putExtra("value5", kt );
    	i.putExtra("value6", nf );
    	i.putExtra("value7", usr );
    	startActivity(i);
	}
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        switch (requestCode) {
	            case FILE_SELECT_CODE:      
	            if (resultCode == RESULT_OK) {   
	                Uri uri = data.getData();
	                try {
	                	String path = getPath(this, uri);
	                	txtPath.setText(path);
	                } catch (URISyntaxException ese) {}
	            }           
	            break;
	        }
	    super.onActivityResult(requestCode, resultCode, data);
	    }

	public String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor
                .getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                	Uri filePath = uri.parse(cursor.getString(column_index));
                	namaFile = filePath.getLastPathSegment();
                    return cursor.getString(column_index);
                    
                	
                }
            } catch (Exception e) {
            
            }
        }

        else if ("file".equalsIgnoreCase(uri.getScheme())) {
        	namaFile = uri.getLastPathSegment();
            return uri.getPath();
            
            
        }

        return null;
    }
	
	public void namaFile(String s) {
		
	}
	
	    private void showFileChooser() {
	    	
	        Intent intent = new Intent(Intent.ACTION_GET_CONTENT); 
	        intent.setType("*/*"); 
	        intent.addCategory(Intent.CATEGORY_OPENABLE);

	        try {
	            startActivityForResult(
	                    Intent.createChooser(intent, "Select a File to Upload"),
	                    FILE_SELECT_CODE);
	        } catch (android.content.ActivityNotFoundException ex) {
	            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
	        }
	        
	    }
	    

    public void uploadFiles(String pathToOurFile) {
    	HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        DataInputStream inputStream = null;
        
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;

        try
        {
        FileInputStream fileInputStream = new FileInputStream(new File(pathToOurFile) );

        URL url = new URL(urlServer);
        connection = (HttpURLConnection) url.openConnection();

        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);

        connection.setRequestMethod("POST");

        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

        outputStream = new DataOutputStream( connection.getOutputStream() );
        outputStream.writeBytes(twoHyphens + boundary + lineEnd);
        outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + pathToOurFile +"\"" + lineEnd);
        outputStream.writeBytes(lineEnd);

        bytesAvailable = fileInputStream.available();
        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        buffer = new byte[bufferSize];

        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0)
        {
        outputStream.write(buffer, 0, bufferSize);
        bytesAvailable = fileInputStream.available();
        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        outputStream.writeBytes(lineEnd);
        outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

        int serverResponseCode = connection.getResponseCode();
        String serverResponseMessage = connection.getResponseMessage();

        fileInputStream.close();
        outputStream.flush();
        outputStream.close();
        if (serverResponseMessage.equals("OK")) {
        	progressBar.setVisibility(View.GONE);
        	
        	Toast.makeText(this, "File berhasil di upload", Toast.LENGTH_SHORT).show();
        	String jdl = editUpl_judul.getText().toString();
			String dsk = editUpl_diskripsi.getText().toString();
			String kt = this.kategori;
			callIntent(namaKelas, namaMapel, jdl, dsk, kt, this.url+namaFile, user);
        	txtLoading.setText("");
        	clearMateri();
        }
        else {
        	progressBar.setVisibility(View.GONE);
        	Toast.makeText(this, "File gagal di upload", Toast.LENGTH_SHORT).show();
        }
        }
        catch (Exception ex)
        {
        	progressBar.setVisibility(View.GONE);
        	Toast.makeText(this, "Error, file gagal di upload", Toast.LENGTH_SHORT).show();
        	txtLoading.setText("");
        }
    }
    
    public void onAbout(View v)
    {
    	Toast.makeText (getApplicationContext(),"Upload file dengan format dokumen. Ex: doc, docx, xls, ppt, pdf, dll" , Toast.LENGTH_LONG).show ();
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