package com.qq.weixin.mp.test;

import com.qq.weixin.mp.pojo.AccessToken;
import com.qq.weixin.mp.pojo.AccessTokenRoom;

public class TestRoom {
  public static void main(String[] args) {
    AccessToken at = null;
    at = new AccessToken();
    at.setToken("token");
    at.setExpiresIn(7200);
    AccessTokenRoom.setAccessToken(at);
    AccessToken at1 = AccessTokenRoom.getAccessTokenItem().getAccessToken();
    System.out.println("at1 = " + at1.getToken());
  }
}
