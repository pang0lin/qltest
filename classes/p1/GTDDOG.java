package p1;

public class GTDDOG {
  public int DogAddr;
  
  public int DogBytes;
  
  public byte[] DogData;
  
  static {
    try {
      System.loadLibrary("DOGJava");
    } catch (UnsatisfiedLinkError e) {
      System.err.println("Can't find library DOGJava.DLL");
      System.exit(-1);
    } 
  }
  
  public native int WriteDog();
  
  public native int ReadDog();
  
  public String getData() {
    this.DogData = new byte[100];
    this.DogBytes = 100;
    try {
      ReadDog();
    } catch (Exception exception) {}
    return new String(this.DogData);
  }
  
  public int getSN() {
    this.DogBytes = 0;
    this.DogData = new byte[4];
    int sn = 0;
    try {
      ReadDog();
      sn = (this.DogData[0] + 256) % 256 + (
        this.DogData[1] + 256) % 256 * 256 + (
        this.DogData[2] + 256) % 256 * 65536 + (
        this.DogData[3] + 256) % 256 * 256 * 65536;
    } catch (Exception exception) {}
    return sn;
  }
}
