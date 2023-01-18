package util;

import java.util.Random;

public class kadai1Salt {

	public static String getSalt(int len){
		Random rnd = new Random();

		final String CHARACTORS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < len; i++){
			sb.append(CHARACTORS.charAt(rnd.nextInt(CHARACTORS.length())));
		}

		return sb.toString();
	}
	
	public static void main (String[] args) {
		String salt = kadai1Salt.getSalt(20);
		System.out.print(salt);
	}
}
