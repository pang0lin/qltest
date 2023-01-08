package com.qq.weixin.mp.aes;

import java.util.ArrayList;

class ByteGroup {
  ArrayList<Byte> byteContainer = new ArrayList<Byte>();
  
  public byte[] toBytes() {
    byte[] bytes = new byte[this.byteContainer.size()];
    for (int i = 0; i < this.byteContainer.size(); i++)
      bytes[i] = ((Byte)this.byteContainer.get(i)).byteValue(); 
    return bytes;
  }
  
  public ByteGroup addBytes(byte[] bytes) {
    byte b;
    int i;
    byte[] arrayOfByte;
    for (i = (arrayOfByte = bytes).length, b = 0; b < i; ) {
      byte b1 = arrayOfByte[b];
      this.byteContainer.add(Byte.valueOf(b1));
      b++;
    } 
    return this;
  }
  
  public int size() {
    return this.byteContainer.size();
  }
}
