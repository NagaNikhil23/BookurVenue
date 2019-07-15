package panduchinnu.venubooking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



/**
 * Created by Nikhil on 6/22/2017.
 */

public class Dbcus extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="BOOKURVENUE.DB";
    public static final String TABLE_NAME="Customer_details";
    public static final String COL_1 = "name";
    public static final String COL_2 = "password";
    public static final String COL_3 = "email";
    public static final String COL_4 = "mno";
    public static final String TABLE_NAMEE="Owner_details";
    public static final String COL_11 = "ownname";
    public static final String COL_12 = "ownpassword";
    public static final String COL_13 = "ownemail";
    public static final String COL_14 = "ownmno";
    public static final String COL_15 = "ownaddress";
    public static final String COL_16 = "ownadharnum";
    public static final String TABLE_NAMEA="Functionhall_details";
    public static final String COL_21 = "hallname";
    public static final String COL_22 = "hallarea";
    public static final String COL_23 = "halladdress";
    public static final String COL_24 = "hallcapacity";
    public static final String COL_25 = "hallrooms";
    public static final String COL_26 = "hallsuited";
    public static final String COL_27 = "hallac";
    public static final String COL_28="owner";
    public static final String COL_29="hallcost";
    public static final String COL_30="hallimage";
    public static final String COL_31="halllatti";
    public static final String COL_32="halllongi";
    public static final String TABLE_NAMEW="HallsBooking";
    public static final String COL_41="hallname";
    public static final String COL_42="Date";
    public static final String COL_43="Bookedby";
    public static final String COL_44="status";
    public static final String COL_45="owner";





    public Dbcus(Context context) {
        super(context,DATABASE_NAME ,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        db.execSQL("drop table if exists "+TABLE_NAMEE);
        db.execSQL("drop table if exists "+TABLE_NAMEA);
        db.execSQL("drop table if exists "+TABLE_NAMEW);

        db.execSQL("create table "+TABLE_NAME+"(Name text,password text,email text,mno text);");
        db.execSQL("create table "+TABLE_NAMEE+"(ownname text,ownpassword text,ownemail text,ownmno text,ownaddress text,ownadharnum text);");
        db.execSQL("create table "+TABLE_NAMEA+"(hallname text,hallarea text,halladdress text,hallcapacity text,hallrooms text,hallsuited text,hallac text,owner text,hallcost text,hallimage blog,halllatti real,halllongi real);");
        db.execSQL("create table "+TABLE_NAMEW+ " (hallname text,Date text,Bookedby text,Status text,owner text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        db.execSQL("drop table if exists "+TABLE_NAMEE);
        db.execSQL("drop table if exists "+TABLE_NAMEA);
        db.execSQL("drop table if exists "+TABLE_NAMEW);
        onCreate(db);
    }

    public String insertEntry(String cuname, String cuemail, String cupwd, String cumobile) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_1,cuname);
        cv.put(COL_2,cupwd);
        cv.put(COL_3,cuemail);
        cv.put(COL_4,cumobile);
        Long res=db.insert(TABLE_NAME,null,cv);
        if(res==-1)
        {
            return "Failure";
        }
        else
        {
            return "Success";
        }
    }
    public String insert(String owname, String owpwd, String owmail, String owmobile, String owadd, String owaad) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_11,owname);
        cv.put(COL_12,owpwd);
        cv.put(COL_13,owmail);
        cv.put(COL_14,owmobile);
        cv.put(COL_15,owadd);
        cv.put(COL_16,owaad);

        Long res=db.insert(TABLE_NAMEE,null,cv);
        if(res==-1)
        {
            return "Failure";
        }
        else
        {
            return "Success";
        }

    }

    public String insertHalls(String venname, String venarea, String venadd, String vencapa, String venroom, String vensuited, String venac, String user, String vencost, byte[] image, double latti, double longi) {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_21,venname);
        cv.put(COL_22,venarea);
        cv.put(COL_23,venadd);
        cv.put(COL_24,vencapa);
        cv.put(COL_25,venroom);
        cv.put(COL_26,vensuited);
        cv.put(COL_27,venac);
        cv.put(COL_28,user);
        cv.put(COL_29,vencost);
        cv.put(COL_30,image);
        cv.put(COL_31,latti);
        cv.put(COL_32,longi);
        Long res=db.insert(TABLE_NAMEA,null,cv);
        if(res==-1)
        {
            return "Failure";
        }
        else
        {
            return "Success";
        }
    }
    public String insertBooking(String hallname,String date,String bookedby,String status,String owner){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_41,hallname);
        cv.put(COL_42,date);
        cv.put(COL_43,bookedby);
        cv.put(COL_44,status);
        cv.put(COL_45,owner);
        Long res=db.insert(TABLE_NAMEW,null,cv);
        if(res==-1)
        {
            return "Failure";
        }
        else
        {
            return "Success";
        }
    }
    public Cursor getStatusofhall(String hallname,String date){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res4=db.rawQuery("Select status from "+TABLE_NAMEW+" "+
                " where hallname="+"'"+hallname+"'"+" AND date="+"'"+date+"'",null);
        return res4;
    }
    public String updateBooking(String hallname, String date, String bookedby, String status, String owner){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_43,bookedby);
        cv.put(COL_44,status);
        cv.put(COL_45,owner);
        int resu=db.update(TABLE_NAMEW,cv,"hallname="+"'"+hallname+"'"+" AND date="+"'"+date+"'",null);
        if (resu == -1) {
            return "failure";
        }else {
            return "success";
        }
    }

    public Cursor getOwnerDetails(String owusst) {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res4=db.rawQuery("Select ownpassword from "+TABLE_NAMEE+" "+
                " where ownemail="+"'"+owusst+"'",null);
        return res4;

    }

    public Cursor getCustDetails(String cuusst) {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res7=db.rawQuery("Select password from "+TABLE_NAME+" "+
                " where email="+"'"+cuusst+"'",null);
        return res7;
    }

    public Cursor getHallsName(String user) {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res8=db.rawQuery("Select hallname,hallimage from "+TABLE_NAMEA+" "+" where owner="+"'"+user+"'",null);
        return res8;
    }

    public Cursor gethallDetails(String value) {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res9=db.rawQuery("Select hallname,hallarea from "+TABLE_NAMEA+" "+" where hallname="+"'"+value+"'",null);
        return res9;
    }

    public Cursor getowneralldetails(String username) {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res4=db.rawQuery("Select * from "+TABLE_NAMEE+" "+
                " where ownemail="+"'"+username+"'",null);
        return res4;
    }
    public Cursor getBookings(String owner){

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res4=db.rawQuery("Select * from "+TABLE_NAMEW+" "+
                " where owner="+"'"+owner+"'",null);
        return res4;

    }

    public String updateOwnerdetails(String name,String password,String username,String mobile,String address,String adhar){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_11,name);
        cv.put(COL_12,password);
        cv.put(COL_14,mobile);
        cv.put(COL_15,address);
        cv.put(COL_16,adhar);
        int resu=db.update(TABLE_NAMEE,cv,COL_13+"="+"'"+username+"'",null);
        //db.execSQL("commit",null);
        if (resu == -1) {
            return "failure";
        }else {
            return "success";
        }
    }

    public Cursor getselectedhalls(String area, String capa, String cost, String ac) {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res4=db.rawQuery("Select hallname,hallimage,halllatti,halllongi from "+TABLE_NAMEA+" "+
                " where hallarea="+"'"+area+"'"+"  OR hallcapacity >="+capa+" OR hallcost >="+cost+" OR hallac="+"'"+ac+"'",null);
        return res4;

    }

    public Cursor gethallcompleteDetails(String value) {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res9=db.rawQuery("Select hallname,hallarea,halladdress,hallcapacity,hallac,hallcost,owner from "+TABLE_NAMEA+" "+" where hallname="+"'"+value+"'",null);
        return res9;

    }

    public Cursor getPendingBookings(String owner) {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res4=db.rawQuery("Select * from "+TABLE_NAMEW+" "+
                " where owner="+"'"+owner+"'"+" AND status='Pending'",null);
        return res4;

    }

    public Cursor getCustomerDetails(String customername) {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res7=db.rawQuery("Select * from "+TABLE_NAME+" "+
                " where email="+"'"+customername+"'",null);
        return res7;

    }

    public Cursor getcustBookings(String customer) {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res4=db.rawQuery("Select * from "+TABLE_NAMEW+" "+
                " where Bookedby="+"'"+customer+"'",null);
        return res4;

    }

    public Cursor getCustPendingBookings(String user) {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res4=db.rawQuery("Select * from "+TABLE_NAMEW+" "+
                " where bookedby="+"'"+user+"'"+" AND status='Pending'",null);
        return res4;

    }

    public String updatecusdetails(String proname, String propwd, String promail, String promob) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_1,proname);
        cv.put(COL_2,propwd);
        cv.put(COL_4,promob);
        int resu=db.update(TABLE_NAME,cv,COL_3+"="+"'"+promail+"'",null);
        //db.execSQL("commit",null);
        if (resu == -1) {
            return "failure";
        }else {
            return "success";
        }
    }

    public Cursor getlatlang(String hallname) {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res4=db.rawQuery("Select halllatti,halllongi from "+TABLE_NAMEA+" "+
                " where hallname="+"'"+hallname+"'",null);
        return res4;

    }

    public Cursor gethalldetailsatlatlang(double latti, double longi) {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res9=db.rawQuery("Select hallname,hallarea,halladdress,hallcapacity,hallac,hallcost,owner from "+TABLE_NAMEA+" "+" where halllatti="+latti+" OR halllongi= "+longi,null);
        return res9;


    }
}
