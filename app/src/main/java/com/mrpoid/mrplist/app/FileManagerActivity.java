package com.mrpoid.mrplist.app;

import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;

import com.mrpoid.mrpliset.R;
import com.mrpoid.mrplist.view.ExplorerFragment;

public class FileManagerActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(android.R.id.content, new ExplorerFragment(), "main").commit();

		// DITAMBAHKAN PENGECEKAN NULL agar tidak crash
		if (getSupportActionBar() != null) {
			getSupportActionBar().setElevation(0);
		}
	}
}
