package com.js.util.util.eval;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class NewEval {
  public static String javaEval(String evalStr) {
    String returnValue = "no result";
    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("JavaScript");
    String js = "function likeEval(str){return eval(str);}";
    try {
      engine.eval(js);
      Invocable inv = (Invocable)engine;
      returnValue = inv.invokeFunction("likeEval", new Object[] { evalStr }).toString();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return returnValue;
  }
}
