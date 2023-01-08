package rtx.rtxsms.util;

public class MD5 {
  static final byte[] a = new byte[] { Byte.MIN_VALUE };
  
  private long[] b;
  
  private long[] c;
  
  private byte[] d;
  
  public String digestHexStr;
  
  private byte[] e;
  
  public String getMD5ofStr(String s) {
    a();
    a(s.getBytes(), s.length());
    b();
    this.digestHexStr = "";
    for (int i = 0; i < 16; i++)
      this.digestHexStr = String.valueOf(this.digestHexStr) + byteHEX(this.e[i]); 
    return this.digestHexStr;
  }
  
  public byte[] getMD5(byte[] abyte0) {
    a();
    a(abyte0, abyte0.length);
    b();
    return this.e;
  }
  
  public MD5() {
    this.b = new long[4];
    this.c = new long[2];
    this.d = new byte[64];
    this.e = new byte[16];
    a();
  }
  
  private void a() {
    this.c[0] = 0L;
    this.c[1] = 0L;
    this.b[0] = 1732584193L;
    this.b[1] = 4023233417L;
    this.b[2] = 2562383102L;
    this.b[3] = 271733878L;
  }
  
  private static long a(long l, long l1, long l2) {
    return l & l1 | (l ^ 0xFFFFFFFFFFFFFFFFL) & l2;
  }
  
  private static long b(long l, long l1, long l2) {
    return l & l2 | l1 & (l2 ^ 0xFFFFFFFFFFFFFFFFL);
  }
  
  private static long c(long l, long l1, long l2) {
    return l ^ l1 ^ l2;
  }
  
  private static long d(long l, long l1, long l2) {
    return l1 ^ (l | l2 ^ 0xFFFFFFFFFFFFFFFFL);
  }
  
  private long a(long l, long l1, long l2, long l3, long l4, long l5, long l6) {
    return l = (l = ((int)(l += a(l1, l2, l3) + l4 + l6) << (int)l5 | 
      (int)l >>> (int)(32L - l5))) + 
      l1;
  }
  
  private long b(long l, long l1, long l2, long l3, long l4, long l5, long l6) {
    return l = (l = ((int)(l += b(l1, l2, l3) + l4 + l6) << (int)l5 | 
      (int)l >>> (int)(32L - l5))) + 
      l1;
  }
  
  private long c(long l, long l1, long l2, long l3, long l4, long l5, long l6) {
    return l = (l = ((int)(l += c(l1, l2, l3) + l4 + l6) << (int)l5 | 
      (int)l >>> (int)(32L - l5))) + 
      l1;
  }
  
  private long d(long l, long l1, long l2, long l3, long l4, long l5, long l6) {
    return l = (l = ((int)(l += d(l1, l2, l3) + l4 + l6) << (int)l5 | 
      (int)l >>> (int)(32L - l5))) + 
      l1;
  }
  
  private void a(byte[] abyte0, int i) {
    int j;
    byte[] abyte1 = new byte[64];
    int k = (int)(this.c[0] >>> 3L) & 0x3F;
    this.c[0] = this.c[0] + (i << 3);
    if (this.c[0] + (i << 3) < (i << 3))
      this.c[1] = this.c[1] + 1L; 
    this.c[1] = this.c[1] + (i >>> 29);
    int l = 64 - k;
    if (i >= l) {
      a(this.d, abyte0, k, 0, l);
      a(this.d);
      for (j = l; j + 63 < i; j += 64) {
        a(abyte1, abyte0, 0, j, 64);
        a(abyte1);
      } 
      k = 0;
    } else {
      j = 0;
    } 
    a(this.d, abyte0, k, j, i - j);
  }
  
  private void b() {
    byte[] abyte0;
    a(abyte0 = new byte[8], this.c, 8);
    int i, j = ((i = (int)(this.c[0] >>> 3L) & 0x3F) >= 56) ? (120 - i) : (56 - i);
    a(a, j);
    a(abyte0, 8);
    a(this.e, this.b, 16);
  }
  
  private static void a(byte[] abyte0, byte[] abyte1, int i, int j, int k) {
    for (int l = 0; l < k; l++)
      abyte0[i + l] = abyte1[j + l]; 
  }
  
  private void a(byte[] abyte0) {
    long l = this.b[0];
    long l1 = this.b[1];
    long l2 = this.b[2];
    long l3 = this.b[3];
    long[] al;
    a(al = new long[16], abyte0, 64);
    l = a(l, l1, l2, l3, al[0], 7L, 3614090360L);
    l3 = a(l3, l, l1, l2, al[1], 12L, 3905402710L);
    l2 = a(l2, l3, l, l1, al[2], 17L, 606105819L);
    l1 = a(l1, l2, l3, l, al[3], 22L, 3250441966L);
    l = a(l, l1, l2, l3, al[4], 7L, 4118548399L);
    l3 = a(l3, l, l1, l2, al[5], 12L, 1200080426L);
    l2 = a(l2, l3, l, l1, al[6], 17L, 2821735955L);
    l1 = a(l1, l2, l3, l, al[7], 22L, 4249261313L);
    l = a(l, l1, l2, l3, al[8], 7L, 1770035416L);
    l3 = a(l3, l, l1, l2, al[9], 12L, 2336552879L);
    l2 = a(l2, l3, l, l1, al[10], 17L, 4294925233L);
    l1 = a(l1, l2, l3, l, al[11], 22L, 2304563134L);
    l = a(l, l1, l2, l3, al[12], 7L, 1804603682L);
    l3 = a(l3, l, l1, l2, al[13], 12L, 4254626195L);
    l2 = a(l2, l3, l, l1, al[14], 17L, 2792965006L);
    l1 = a(l1, l2, l3, l, al[15], 22L, 1236535329L);
    l = b(l, l1, l2, l3, al[1], 5L, 4129170786L);
    l3 = b(l3, l, l1, l2, al[6], 9L, 3225465664L);
    l2 = b(l2, l3, l, l1, al[11], 14L, 643717713L);
    l1 = b(l1, l2, l3, l, al[0], 20L, 3921069994L);
    l = b(l, l1, l2, l3, al[5], 5L, 3593408605L);
    l3 = b(l3, l, l1, l2, al[10], 9L, 38016083L);
    l2 = b(l2, l3, l, l1, al[15], 14L, 3634488961L);
    l1 = b(l1, l2, l3, l, al[4], 20L, 3889429448L);
    l = b(l, l1, l2, l3, al[9], 5L, 568446438L);
    l3 = b(l3, l, l1, l2, al[14], 9L, 3275163606L);
    l2 = b(l2, l3, l, l1, al[3], 14L, 4107603335L);
    l1 = b(l1, l2, l3, l, al[8], 20L, 1163531501L);
    l = b(l, l1, l2, l3, al[13], 5L, 2850285829L);
    l3 = b(l3, l, l1, l2, al[2], 9L, 4243563512L);
    l2 = b(l2, l3, l, l1, al[7], 14L, 1735328473L);
    l1 = b(l1, l2, l3, l, al[12], 20L, 2368359562L);
    l = c(l, l1, l2, l3, al[5], 4L, 4294588738L);
    l3 = c(l3, l, l1, l2, al[8], 11L, 2272392833L);
    l2 = c(l2, l3, l, l1, al[11], 16L, 1839030562L);
    l1 = c(l1, l2, l3, l, al[14], 23L, 4259657740L);
    l = c(l, l1, l2, l3, al[1], 4L, 2763975236L);
    l3 = c(l3, l, l1, l2, al[4], 11L, 1272893353L);
    l2 = c(l2, l3, l, l1, al[7], 16L, 4139469664L);
    l1 = c(l1, l2, l3, l, al[10], 23L, 3200236656L);
    l = c(l, l1, l2, l3, al[13], 4L, 681279174L);
    l3 = c(l3, l, l1, l2, al[0], 11L, 3936430074L);
    l2 = c(l2, l3, l, l1, al[3], 16L, 3572445317L);
    l1 = c(l1, l2, l3, l, al[6], 23L, 76029189L);
    l = c(l, l1, l2, l3, al[9], 4L, 3654602809L);
    l3 = c(l3, l, l1, l2, al[12], 11L, 3873151461L);
    l2 = c(l2, l3, l, l1, al[15], 16L, 530742520L);
    l1 = c(l1, l2, l3, l, al[2], 23L, 3299628645L);
    l = d(l, l1, l2, l3, al[0], 6L, 4096336452L);
    l3 = d(l3, l, l1, l2, al[7], 10L, 1126891415L);
    l2 = d(l2, l3, l, l1, al[14], 15L, 2878612391L);
    l1 = d(l1, l2, l3, l, al[5], 21L, 4237533241L);
    l = d(l, l1, l2, l3, al[12], 6L, 1700485571L);
    l3 = d(l3, l, l1, l2, al[3], 10L, 2399980690L);
    l2 = d(l2, l3, l, l1, al[10], 15L, 4293915773L);
    l1 = d(l1, l2, l3, l, al[1], 21L, 2240044497L);
    l = d(l, l1, l2, l3, al[8], 6L, 1873313359L);
    l3 = d(l3, l, l1, l2, al[15], 10L, 4264355552L);
    l2 = d(l2, l3, l, l1, al[6], 15L, 2734768916L);
    l1 = d(l1, l2, l3, l, al[13], 21L, 1309151649L);
    l = d(l, l1, l2, l3, al[4], 6L, 4149444226L);
    l3 = d(l3, l, l1, l2, al[11], 10L, 3174756917L);
    l2 = d(l2, l3, l, l1, al[2], 15L, 718787259L);
    l1 = d(l1, l2, l3, l, al[9], 21L, 3951481745L);
    this.b[0] = this.b[0] + l;
    this.b[1] = this.b[1] + l1;
    this.b[2] = this.b[2] + l2;
    this.b[3] = this.b[3] + l3;
  }
  
  private static void a(byte[] abyte0, long[] al, int i) {
    int j = 0;
    for (int k = 0; k < i; k += 4) {
      abyte0[k] = (byte)(int)(al[j] & 0xFFL);
      abyte0[k + 1] = (byte)(int)(al[j] >>> 8L & 0xFFL);
      abyte0[k + 2] = (byte)(int)(al[j] >>> 16L & 0xFFL);
      abyte0[k + 3] = (byte)(int)(al[j] >>> 24L & 0xFFL);
      j++;
    } 
  }
  
  private static void a(long[] al, byte[] abyte0, int i) {
    int j = 0;
    for (int k = 0; k < i; k += 4) {
      al[j] = b2iu(abyte0[k]) | b2iu(abyte0[k + 1]) << 8L | 
        b2iu(abyte0[k + 2]) << 16L | b2iu(abyte0[k + 3]) << 24L;
      j++;
    } 
  }
  
  public static long b2iu(byte byte0) {
    return ((byte0 >= 0) ? byte0 : (byte0 & 0xFF));
  }
  
  public static String byteHEX(byte byte0) {
    char[] ac = { 
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'A', 
        'B', 'C', 'D', 'E', 'F' };
    char[] ac1;
    (ac1 = new char[2])[0] = ac[byte0 >>> 4 & 0xF];
    ac1[1] = ac[byte0 & 0xF];
    String s = null;
    s = new String(ac1);
    return s;
  }
}
