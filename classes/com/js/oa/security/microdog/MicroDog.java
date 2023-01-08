package com.js.oa.security.microdog;

public class MicroDog extends SecurityAPI {
  public int CallDogCheck() {
    int retCode = -1;
    retCode = DogCheck();
    return retCode;
  }
  
  public int CallDogConvert() {
    int retCode = DogConvert();
    return retCode;
  }
  
  public int CallWriteDog() {
    int retCode = WriteDog();
    return retCode;
  }
  
  public int CallReadDog() {
    int retCode = ReadDog();
    return retCode;
  }
  
  public int CallGetCurrentNo() {
    int retCode = GetCurrentNo();
    return retCode;
  }
  
  public int CallDisableShare() {
    int retCode = DisableShare();
    return retCode;
  }
}
