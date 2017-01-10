package com.bhuvanesh.talenthive.database;

/**
 * Created by Bhuvanesh on 12/5/2016.
 */

public interface DBQuery {

    String PROFILE_TABLE_NAME = "profileRecords";

    String CREATE_TABLE_PROFILE_DETAILS = "CREATE TABLE "+ PROFILE_TABLE_NAME +"(_id integer PRIMARY KEY, "+
            "Name varchar, Email varchar, Password varchar, MobileNo varchar, StateName varchar, DistrictName varchar);";
    String GET_PROFILE = "SELECT * FROM " + PROFILE_TABLE_NAME + " WHERE Email = \'%s\' AND Password = \'%s\'";
}
