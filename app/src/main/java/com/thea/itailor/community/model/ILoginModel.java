package com.thea.itailor.community.model;

/**
 * Created by Thea on 2015/9/7 0007.
 */
public interface ILoginModel {
    void register(String email, String password, String udid);

    void login(String email, String password, String udid);

}
