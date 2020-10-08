package com.potalab.testcase.servlet.forwarddata;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public final class Util {

  /**
   * 소트된 속성 객체를 리턴한다.
   * @param request
   * @return
   */
  public final static Map<String, Object> getAttributes(HttpServletRequest request) {
    SortedMap<String, Object> map = new TreeMap<>();
    Enumeration attributes = request.getAttributeNames();
    if (attributes != null) {
      while (attributes.hasMoreElements()) {
        String name = (String) attributes.nextElement();
        Object value = request.getAttribute(name);
        map.put(name, value);
      }
    }
    return map;
  }

  /**
   * 소트된 파라미터 객체를 리턴한다.
   * @param request
   * @return
   */
  public final static Map<String, String> getParameters(HttpServletRequest request) {
    SortedMap<String, String> map = new TreeMap<>();
    Enumeration<String> params = request.getParameterNames();
    if (params != null) {
      while (params.hasMoreElements()) {
        String name = (String) params.nextElement();
        String value = request.getParameter(name);
        map.put(name, value);
      }
    }
    return map;
  }
}
