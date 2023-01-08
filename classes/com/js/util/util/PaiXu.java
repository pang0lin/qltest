package com.js.util.util;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class PaiXu {
  public static ArrayList<String> HanziPaiXu(ArrayList<String> list) {
    Comparator<? super String> cmp = Collator.getInstance(Locale.CHINA);
    Collections.sort(list, cmp);
    return list;
  }
}
