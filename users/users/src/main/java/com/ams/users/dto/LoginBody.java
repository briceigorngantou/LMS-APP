package com.ams.users.dto;

import javax.validation.constraints.*;

/**
 * The body for the login requests.
 */
public class LoginBody {

  /** The username to log in with. */
  @NotNull
  @NotBlank
  private String username;
  /** The password to log in with. */
  @NotNull
  @NotBlank
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}