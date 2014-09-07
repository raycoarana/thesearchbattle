package com.raycoarana.thesearchbattle.io;

import android.util.Log;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamExtensions {

    private static final String TAG = StreamExtensions.class.getSimpleName();

	public void copy(InputStream inputStream, OutputStream outputStream) {
		try {
			copyContents(inputStream, outputStream);
		} catch (IOException ex) {
			throw new RuntimeException("Can't copy database from assets", ex);
		} finally {
			safeClose(outputStream, inputStream);
		}
	}

	private void copyContents(InputStream inputStream, OutputStream outputStream) throws IOException {
		int SIXTEEN_KB = 16384;
		byte[] buffer = new byte[SIXTEEN_KB];
		int readCount;
		while((readCount = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, readCount);
		}
	}

	public void safeClose(Closeable... closeables) {
		for(Closeable closeable : closeables) {
			tryToClose(closeable);
		}
	}

	private void tryToClose(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException ex) {
			Log.e(TAG, "Can't close the Closeable object", ex);
		}
	}

}