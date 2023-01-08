package com.js.oa.webservice.test;

import java.io.ByteArrayOutputStream;

class DirectByteArrayOutputStream extends ByteArrayOutputStream {
  DirectByteArrayOutputStream() {}
  
  DirectByteArrayOutputStream(int size) {
    super(size);
  }
  
  public byte[] getBuf() {
    return this.buf;
  }
}
