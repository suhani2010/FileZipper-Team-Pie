package com.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public interface IDBOperations {
    public Connection connectDB(String databaseName) ;
    public void insertIntoDB(String checksum, byte[] freqMap) ;
    public void deleteFromDB() throws SQLException,ClassNotFoundException;
    public void updateIntoDB() throws SQLException,ClassNotFoundException;
    public Map<String,Integer> readFromDB(String checksum) throws SQLException;
    public void createNewTable();
    public void readTable();
}
