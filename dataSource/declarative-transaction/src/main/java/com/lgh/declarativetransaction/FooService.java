package com.lgh.declarativetransaction;

public interface FooService {

    void insertRecord();

    void insertThenRollback() throws RollBackException;

    void invokeInsertThenRollback1() throws RollBackException;

    void invokeInsertThenRollback2() throws RollBackException;
}
