package com.wyre;

public class StringProperty{
    private String mName;
    private String mValue;
    public StringProperty(String name,String value){
        mName = name;
        mValue = value;
    }
    public String getName(){
        return mName;
    }
    public String getValue(){
        return mValue;
    }
}
