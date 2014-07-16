package aditya.marriagetest1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import com.loopj.android.http.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Date;

import aditya.marriagetest1.R;

public class rsvp_form extends Activity {
	String URL = "http://marriagersvp.herokuapp.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsvp_form);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rsvp_form, menu);
        return true;
    }

	public void submit(View view) {
		try {
			RequestParams params = new RequestParams();
			EditText editText = (EditText)findViewById(R.id.editText);
			if(editText.getText().toString().equals("")) {
				Toast.makeText(getApplicationContext(),"Please fill in the Name",Toast.LENGTH_SHORT).show();
			}
			else {
				params.put("name",editText.getText().toString());
			}

			editText = (EditText)findViewById(R.id.edit_noPeople);
			if(editText.getText().toString().equals("")) {
				Toast.makeText(getApplicationContext(),"Please fill in the Number of people",Toast.LENGTH_SHORT).show();
			}
			else {
				try {
					params.put("nopeople",editText.getText().toString());
				} catch (NumberFormatException e) {
					Toast.makeText(getApplicationContext(),"Invalid number of people",Toast.LENGTH_SHORT).show();
				}
			}

			editText = (EditText)findViewById(R.id.editText2);
			if(editText.getText().toString().equals("")) {
				Toast.makeText(getApplicationContext(),"Please fill in the contact info",Toast.LENGTH_SHORT).show();
			}
			else {
				params.put("phno",editText.getText().toString());
			}

			editText = (EditText)findViewById(R.id.editText3);
			if(editText.getText().toString().equals("")) {
				Toast.makeText(getApplicationContext(),"Please fill in the contact info",Toast.LENGTH_SHORT).show();
			}
			else {
				params.put("email",editText.getText().toString());
			}
			/*
			editText = (EditText)findViewById(R.id.arrdate);
			if(editText.getText().toString().equals("")) {
				Toast.makeText(getApplicationContext(),"Please fill in the contact info",Toast.LENGTH_SHORT).show();
			}
			else {
				params.put("Arrdate", new Date(editText.getText().toString()));
			}

			editText = (EditText)findViewById(R.id.depdate);
			if(editText.getText().toString().equals("")) {
				Toast.makeText(getApplicationContext(),"Please fill in the contact info",Toast.LENGTH_SHORT).show();
			}
			else {
				params.put("Depdate", new Date(editText.getText().toString()));
			}

			CheckBox checkBox = (CheckBox)findViewById(R.id.marriage);
			params.put("Marriage",checkBox.isChecked());

			checkBox = (CheckBox)findViewById(R.id.ringCeremony);
			params.put("Ring",checkBox.isChecked());

			checkBox = (CheckBox)findViewById(R.id.cocktail);
			params.put("Cocktail",checkBox.isChecked());

			checkBox = (CheckBox)findViewById(R.id.sangeet);
			params.put("Sangeet",checkBox.isChecked());

			checkBox = (CheckBox)findViewById(R.id.goddBharai);
			params.put("Godd",checkBox.isChecked());

			checkBox = (CheckBox)findViewById(R.id.mehendi);
			params.put("Mehendi",checkBox.isChecked());

			checkBox = (CheckBox)findViewById(R.id.tilak);
			params.put("Tilak",checkBox.isChecked());

			checkBox = (CheckBox)findViewById(R.id.pathSahib);
			params.put("Path",checkBox.isChecked());*/

			Toast.makeText(getApplicationContext(),"Posting RSVP",Toast.LENGTH_SHORT).show();
			StringEntity stringEntity = new StringEntity(params.toString());

			stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			Log.i("post params : ", params.toString());
			AsyncHttpClient client = new AsyncHttpClient();
			client.post(URL, params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
					Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
					Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_SHORT).show();
				}
			});
		} catch (UnsupportedEncodingException e) {
			Toast.makeText(getApplicationContext(),"Encoding Exception",Toast.LENGTH_SHORT).show();
		}
	}

	public void serverTest(RequestParams params) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http;//192.168.1.11:3000/rsvps",params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT);
			}
		});
	}/*
	public void postToServer(RequestParams params) {

	}*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
