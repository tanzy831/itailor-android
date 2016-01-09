package com.thea.itailor.community.view;

/**
 * Created by Thea on 2015/8/15 0015.
 */
public interface ILoginView {
    String getEmail();

    String getPassword();

    void setEmail(String email);

    void setPassword(String password);

    void setEmailError(String error);

    void setPasswordError(String error);
}
