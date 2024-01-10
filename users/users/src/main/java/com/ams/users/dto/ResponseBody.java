package com.ams.users.dto;

import java.lang.reflect.Field;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseBody {

  private Object data;

  private String message;

  private Integer statusCode;

  public Integer getStatusCode() {
    return statusCode;
  }

  /**
   * @param statusCode
   */
  public void setStatusCode(Integer statusCode) {
    this.statusCode = statusCode;
  }

  /**
   * @param obj
   * @return boolean
   */
  public boolean hasPasswordAttribute(Object obj) {
    try {
      Field field = obj.getClass().getDeclaredField("password");
      return field != null;
    } catch (NoSuchFieldException e) {
      return false;
    }
  }

  /**
   * @param obj
   * @param fieldName
   * @return Object
   */
  public Object removeField(Object obj, String fieldName) {
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> dataMap = objectMapper.convertValue(obj, Map.class);
    dataMap.remove(fieldName);
    return dataMap;
  }

  public Object getData() {
    // if response is and object and contain password field
    if (this.data != null && !this.data.getClass().isArray() && hasPasswordAttribute(this.data)) {
      return removeField(this.data, "password");
    } else {
      return this.data;
    }
  }

  /**
   * @param data
   */
  public void setData(Object data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  /**
   * @param message
   */
  public void setMessage(String message) {
    this.message = message;
  }

}
