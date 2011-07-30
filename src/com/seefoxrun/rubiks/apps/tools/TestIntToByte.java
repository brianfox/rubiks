package com.seefoxrun.rubiks.apps.tools;

import com.seefoxrun.util.ArrayUtil;


public class TestIntToByte {
    public static void main(String[] args) {
    	double last = 0;
    	int total = 0;
    	int correct = 0;
    	double range = Integer.MAX_VALUE * 1.0 - Integer.MIN_VALUE * 1.0;
        
    	for (int i=Integer.MIN_VALUE; i < Integer.MAX_VALUE; i+=1) {
        	double diff = i*1.0 - Integer.MIN_VALUE*1.0;
        	double percent = diff / range;
        	
        	total++;
        	if (ArrayUtil.byteArrayToInt(ArrayUtil.intToByteArray(i)) == i) {
        		correct++;
        	}
        	if ((int)(percent*100) > (int)(last*100)) {
        		 System.out.printf("%3.1f%%    i=%d   incorrect= %d correct=%d total=%d%n", percent*100,i, total - correct, correct, total);
        		 correct = 0;
        		 total = 0;
        		last = percent;
        	}
        }
    }   
}
