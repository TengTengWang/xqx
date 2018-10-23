package com.xqx.monitor.client;

import java.io.ByteArrayOutputStream;

public class MyByteArrayOutputStream extends ByteArrayOutputStream {
	@Override
	public synchronized String toString() {
		String string = super.toString();
		this.buf = new byte[32];
		this.count = 0;
		return string;
	}
}
