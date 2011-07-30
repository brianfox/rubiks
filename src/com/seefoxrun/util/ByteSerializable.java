package com.seefoxrun.util;

public interface ByteSerializable {
	byte[] byteSerialize();
	Object byteDeserialize(byte[] source);
}
