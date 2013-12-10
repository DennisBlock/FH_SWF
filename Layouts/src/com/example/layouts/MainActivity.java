package com.example.layouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void openLinearLayout(View view) {
		Intent i = new Intent(this, LinearLayoutActivity.class);
		startActivity(i);
	}

	public void openRelativeLayout(View view) {
		Intent i = new Intent(this, RelativeLayoutActivity.class);
		startActivity(i);
	}

	public void openListView(View view) {
		Intent i = new Intent(this, ListViewLoader.class);
		startActivity(i);
	}

	public void openGridView(View view) {
		Intent i = new Intent(this, GridViewActivity.class);
		startActivity(i);
	}

}
