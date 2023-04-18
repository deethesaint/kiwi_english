package com.example.kiwienglish;

import org.bson.types.ObjectId;

public class CurrentAccount {
    private static CurrentAccount account_instance = null;

    private ObjectId accountID;
    private String username;
    private String password;

    private CurrentAccount() {

    }

    public static synchronized CurrentAccount getInstance() {
        if (account_instance == null)
            account_instance = new CurrentAccount();
        return account_instance;
    }

    public ObjectId getAccountID() {
        return accountID;
    }

    public void setAccountID(ObjectId accountID) {
        this.accountID = accountID;
    }

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
