package com.mrpoid.mrplist.view;

import java.io.FileFilter;

import com.mrpoid.mrplist.moduls.MpFile;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

/**
 * 本地应用列表
 * 
 * @author Yichou 2013-12-19
 *
 */
public class ExplorerFragment extends BaseFileFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	protected void initRootPath() {
		// Bisa dikosongkan atau diisi path default
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}

	// WAJIB: Method abstrak dari BaseFileFragment
	@Override
	protected FileFilter getFileFilter() {
		return BaseMrpListFragment.mrpFilter;
	}

	// WAJIB: Method abstrak dari BaseFileFragment
	@Override
	protected boolean onItemClick(int position, MpFile file) {
		com.mrpoid.MrpoidMain.runMrp(getActivity(), file.getPath());
		com.mrpoid.mrplist.app.HomeActivity.addToFavorate(file.getPath());
		return true;
	}
}
