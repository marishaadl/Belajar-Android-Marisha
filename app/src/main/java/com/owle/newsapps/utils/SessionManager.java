package com.owle.newsapps.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.owle.newsapps.LoginActivity;
import com.owle.newsapps.MainActivity;

import java.util.HashMap;

public class SessionManager {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    public static final String KEY_ID_PENGUNJUNG = "id_pengunjung";
    private static final String is_login = "loginstatus";
    private final String SHARE_NAME = "loginsession";
    private final int MODE_PRIVATE = 0;
    private Context _context;

    public SessionManager(Context context)
    {
        this._context = context;
        sp = _context.getSharedPreferences(SHARE_NAME, MODE_PRIVATE);
        editor = sp.edit();

    }

    public void storeLogin(String id_pengunjung){

        editor.putBoolean(is_login, true);
        editor.putString(KEY_ID_PENGUNJUNG, id_pengunjung);
        editor.commit();

    }

    public HashMap getDetailLogin(){
        HashMap<String,String> map = new HashMap<>();
        map.put(KEY_ID_PENGUNJUNG, sp.getString(KEY_ID_PENGUNJUNG,null));
        return map;

    }

    public Boolean checkLogin(){
        if(!this.Loggin()){
            Intent login = new Intent(_context, MainActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            _context.startActivity(login);
            return false;
        }
        return true;
    }

    public void logout(){
        editor.clear();
        editor.commit();


    }

    public  Boolean Loggin(){
        return sp.getBoolean(is_login,false);
    }

}
