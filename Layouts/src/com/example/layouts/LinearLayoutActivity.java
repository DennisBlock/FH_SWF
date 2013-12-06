package com.example.layouts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class LinearLayoutActivity extends Activity {

	private TextView text;
	private TextView to;
	private TextView subject;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.linerlayout);
		text = (TextView) findViewById(R.id.message);
		to = (TextView) findViewById(R.id.to);
		subject = (TextView) findViewById(R.id.subject);
	}
	
	public void send(View view)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append(to.getText());
		buffer.append("\n");
		buffer.append(subject.getText());
		buffer.append("\n");
		buffer.append(text.getText());
		
		Toast.makeText(this, buffer, Toast.LENGTH_SHORT).show();;
	}
	
}
