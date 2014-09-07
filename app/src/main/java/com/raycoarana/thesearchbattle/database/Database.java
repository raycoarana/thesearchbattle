package com.raycoarana.thesearchbattle.database;

import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.raycoarana.thesearchbattle.io.StreamExtensions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Database {

	private static final String ASSETS_DATABASE_PATH = "cars.db";

	private AssetManager mAssetManager;
	private StreamExtensions mStreamExtensions;
	private SQLiteDatabase mDatabase;
	private File mDatabaseFile;

	public Database(File databasePath, AssetManager assetManager, StreamExtensions streamExtensions) {
		mDatabaseFile = databasePath;
		mAssetManager = assetManager;
		mStreamExtensions = streamExtensions;
	}

	public void initilize() {
		if(mDatabase == null) {
			copyDatabaseIfNotExists();
			mDatabase = SQLiteDatabase.openDatabase(mDatabaseFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
		}
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	private void copyDatabaseIfNotExists() {
		mDatabaseFile.delete();
		if(!mDatabaseFile.exists()) {
			copyTo(mDatabaseFile);
		}
	}

	private void copyTo(File databaseDestinationFile) {
		InputStream assetsDatabaseStream = getDatabaseAssetStream();
		OutputStream databaseDestinationStream = getDatabaseDestinationStream(databaseDestinationFile);
		mStreamExtensions.copy(assetsDatabaseStream, databaseDestinationStream);
	}

	private OutputStream getDatabaseDestinationStream(File file) {
		try {
			File directory = file.getParentFile();
			if(directory.exists() || directory.mkdirs()) {
				return new FileOutputStream(file);
			} else {
				throw new RuntimeException("Can't create database destination directory " + file.getAbsolutePath());
			}
		} catch (IOException ex) {
			throw new RuntimeException("Can't open database destination file", ex);
		}
	}

	private InputStream getDatabaseAssetStream() {
		try {
			return mAssetManager.open(ASSETS_DATABASE_PATH, AssetManager.ACCESS_STREAMING);
		} catch (IOException ex) {
			throw new RuntimeException("Can't open database from assets", ex);
		}
	}

	public Cursor executeQuery(String query, Object... args) {
		String[] stringArgs = convertArgsToStringArgs(args);
		return mDatabase.rawQuery(query, stringArgs);
	}

	private String[] convertArgsToStringArgs(Object[] args) {
		String[] stringArgs = new String[args.length];
		for(int i = 0; i < args.length; i++) {
			stringArgs[i] = String.valueOf(args[i]);
		}
		return stringArgs;
	}


}
