/*
 * Copyright (C) 2013 The Mrpoid Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mrpoid.mrplist.app;

import android.os.Environment;
import android.util.Log;

import com.mrpoid.app.EmulatorApplication;
import com.mrpoid.mrplist.moduls.FileType;
import com.mrpoid.mrplist.moduls.MyFavoriteManager;
import com.mrpoid.mrplist.moduls.PreferencesProvider;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * @author Yichou 2013-11-23
 * 
 */
public class MyApplication extends EmulatorApplication {
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		// ===== PASANG CRASH LOGGER =====
		installCrashHandler();
		// ===============================
		
		PreferencesProvider.load(this);
		
		FileType.loadIcons(getResources());
		
		MyFavoriteManager.getInstance().init(this);
	}
	
	/**
	 * Menangkap semua error yang tidak tertangani, lalu menyimpannya ke file.
	 * File disimpan di: /sdcard/MrpoidCrash/crash_log.txt
	 */
	private void installCrashHandler() {
		final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				try {
					// Buat folder di /sdcard/MrpoidCrash/
					File crashDir = new File(Environment.getExternalStorageDirectory(), "MrpoidCrash");
					if (!crashDir.exists()) {
						crashDir.mkdirs();
					}
					
					// Buat file dengan nama tanggal+jam
					String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
					File crashFile = new File(crashDir, "crash_" + timeStamp + ".txt");
					
					// Tulis detail error ke file
					FileWriter fw = new FileWriter(crashFile);
					PrintWriter pw = new PrintWriter(fw);
					
					pw.println("========== MRPOID CRASH LOG ==========");
					pw.println("Waktu: " + new Date().toString());
					pw.println("Thread: " + thread.getName());
					pw.println("=======================================");
					pw.println();
					
					// Stack trace lengkap
					StringWriter sw = new StringWriter();
					PrintWriter stackWriter = new PrintWriter(sw);
					ex.printStackTrace(stackWriter);
					pw.println(sw.toString());
					
					pw.flush();
					pw.close();
					fw.close();
					
					Log.e("MrpoidCrash", "Crash tersimpan di: " + crashFile.getAbsolutePath());
					
				} catch (Exception e) {
					Log.e("MrpoidCrash", "Gagal menulis crash log: " + e.getMessage());
				}
				
				// Panggil handler default agar aplikasi tetap tertutup dengan benar
				if (defaultHandler != null) {
					defaultHandler.uncaughtException(thread, ex);
				}
			}
		});
	}
}
