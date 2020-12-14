package com.example.c_note;

public class CongViec {
    private  int IdCV;
    private  String TenCV;
    public  CongViec(int idCV , String tenCV){
        IdCV = idCV;
        TenCV = tenCV;
    }

    public int getIdCV() {
        return IdCV;
    }

    public void setIdCV(int idCV) {
        IdCV = idCV;
    }

    public String getTenCV() {
        return TenCV;
    }
}
