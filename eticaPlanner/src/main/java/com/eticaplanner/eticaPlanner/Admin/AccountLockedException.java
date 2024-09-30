package com.eticaplanner.eticaPlanner.Admin;

public class AccountLockedException extends RuntimeException{
    public AccountLockedException(String message){
        super(message);
    }
}
