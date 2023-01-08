package com.js.util.util;

import java.util.Random;

public class JSRandom {
  private static String[] charArray = new String[] { 
      "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", 
      "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", 
      "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", 
      "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", 
      "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", 
      "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", 
      "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", 
      "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", 
      "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", 
      "S", "T", "U", "V", "W", "X", "Y", "Z", "w", "s" };
  
  public String getRandomChar(int index) {
    return charArray[index];
  }
  
  public String getRandomString(int len) {
    StringBuffer sb = new StringBuffer();
    Random random = new Random();
    for (int i = 0; i < len; i++) {
      int index = getRandom(1, 100);
      sb.append(charArray[index]);
    } 
    return sb.toString();
  }
  
  public int getRandom(int min, int max) {
    Random random = new Random();
    int res = 0;
    while (res < min || res >= max)
      res = (int)(Math.random() * max) + 1; 
    return res;
  }
  
  public static void main(String[] args) {
    JSRandom random = new JSRandom();
    for (int i = 0; i < 1000; i++)
      System.out.println(random.getRandom(1, 100)); 
  }
}
