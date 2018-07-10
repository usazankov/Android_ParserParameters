package com.inpas.types;

public class HexString {
	private byte[] array;
	public HexString(byte[] array){
		this.array = array;
	}
	
	public HexString(){
		this.array = new byte[0];
	}
	
	boolean isEmpty() {
		return array.length > 0 ? true : false;
	}
	
	@Override
	public String toString() {
		return bytesToHex(array);
	}
	
	public byte[] toByteArray() {
		return array;
	}
	
	public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
	
}
