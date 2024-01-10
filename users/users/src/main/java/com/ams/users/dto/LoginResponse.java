package com.ams.users.dto;

public class LoginResponse {

  private String jwt;

  private boolean success;

  private String message;

  public String getJwt() {
    return jwt;
  }

  /**
   * @param jwt
   */
  public void setJwt(String jwt) {
    this.jwt = jwt;
  }

  public boolean isSuccess() {
    return success;
  }

  /**
   * @param success
   */
  public void setSuccess(boolean success) {
    this.success = success;
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
