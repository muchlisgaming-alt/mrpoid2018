package com.mrpoid.mrplist.view;

import java.io.File;
import java.io.FileFilter;

import com.mrpoid.MrpoidMain;
import com.mrpoid.mrpliset.R;
import com.mrpoid.mrplist.app.HomeActivity;
import com.mrpoid.mrplist.moduls.FileType;
import com.mrpoid.mrplist.moduls.MpFile;
import com.mrpoid.mrplist.utils.ShortcutUtils;

import androidx.fragment.app.ListFragment; // <--- DIUBAH KE ListFragment
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.BaseAdapter; // <--- DITAMBAHKAN

// DIUBAH MENJADI extends ListFragment
public abstract class BaseMrpListFragment extends ListFragment {
	
	// VARIABEL YANG HILANG DITAMBAHKAN DI SINI
	private static final String TAG = "BaseMrpListFragment";
	protected BaseAdapter mAdapter;
	protected int mLongPressIndex;

	// @Override DIHAPUS KARENA BUKAN METHOD BAWAAN ListFragment
	protected FileFilter getFileFilter() {
		return mrpFilter;
	}
	
	public static final FileFilter mrpFilter = new FileFilter() {
		
		@Override
		public boolean accept(File f) {
			if (f.isDirectory()) {
				return  true;
			} else if (f.isFile()) {
				String name = f.getName();
				int ss = name.lastIndexOf('.');
				if(ss != -1) {
					return name.regionMatches(true, ss, ".mrp", 0, 4);
				}
			}

			return false;
		}
	};

	public boolean isContainMrp(File path) {
		return true;
	}
	
	public boolean isContainMrp1(File path) {
		File[] files = path.listFiles(mrpFilter);

		if (files == null || files.length == 0)
			return false;

		for (File f : files) {
			if (f.isFile())
				return true;

			if (isContainMrp(f))
				return true;
		}

		return false;
	}
	
	private void delete(int position) {
		MpFile file = mAdapter.getItem(position);
		
		Log.d(TAG, "delete file = " + file.getName());
		
		if (file.isFile()) {
			if (file.toFile().delete()) {
				Log.i(TAG, "remove file suc!");
				mAdapter.remove(position);
			}
		}
	}
	
	// @Override DIHAPUS KARENA BUKAN METHOD BAWAAN ListFragment
	protected boolean onItemClick(int position, MpFile file) {
		if (file.getType() == FileType.MRP) {
			MrpoidMain.runMrp(getActivity(), file.getPath());
			
			HomeActivity.addToFavorate(file.getPath());
		}
		
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if(v == getListView()) {
			MpFile file = mAdapter.getItem(mLongPressIndex);
			
			if(file.isFile()) {
				menu.add(0, R.id.mi_run_mode, 0, R.string.run_mode);
				menu.add(0, R.id.mi_remove, 0, R.string.remove);
				menu.add(0, R.id.mi_add_favorite, 0, R.string.add_favorite);
				menu.add(0, R.id.mi_create_shortcut, 0, R.string.create_shortcut);
			}
		} else {
			super.onCreateContextMenu(menu, v, menuInfo);
		}
	}

	@Override
	public boolean onContextItemSelected(android.view.MenuItem item) {
		MpFile file = mAdapter.getItem(mLongPressIndex);
		
		if (item.getItemId() == R.id.mi_remove) {
			delete(mLongPressIndex);
		} else if (item.getItemId() == R.id.mi_create_shortcut) {
			ShortcutUtils.createShortCut(getActivity(), 
					file.getTtile(), 
					ShortcutUtils.getAppIcon(getActivity()),
					file.toFile());
		} else if (item.getItemId() == R.id.mi_add_favorite) {
			HomeActivity.addToFavorate(file.getPath());
		} else if (item.getItemId() == R.id.mi_run_mode) {
			HomeActivity.showRunMrpModeDialogFragment(getFragmentManager(), file.getPath());
		} else {
			return super.onContextItemSelected(item);
		}

		return true;
	}
}
