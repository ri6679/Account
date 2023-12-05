package com.example.account_book;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Account extends AppCompatActivity {

    private TextView thedate, sum_view;
    private Button btn_go_calendar;

    MyDBHelper Helper;  SQLiteDatabase db;
    Cursor cursor;  MyCursorAdapter myAdapter;

    final static String KEY_ID = "_id",KEY_CONTEXT = "context",KEY_PRICE = "price",
            TABLE_NAME = "MyAccountList",KEY_DATE = "date";
    public static String View_DATE = getToday_date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("가계부");

        //데이터베이스 생성
        Helper = new MyDBHelper(this);
        db = Helper.getWritableDatabase();

        ListView list = (ListView) findViewById( R.id.account_list );
        thedate = (TextView) findViewById(R.id.date);
        btn_go_calendar = (Button) findViewById(R.id.btn_go_calendar);
        sum_view = (TextView) findViewById(R.id.total_sum);

        //날짜 표시 인텐트 설정
        Intent comingIntent = getIntent();
        String date = comingIntent.getStringExtra("date");
        if(!TextUtils.isEmpty(date)){
            View_DATE = date;
            thedate.setText(date);
        } else{
            thedate.setText(View_DATE);
        }

        // 총합 표시
        String queryPriceSum = String.format( " SELECT SUM(price) FROM %s WHERE date = '%s'", TABLE_NAME, View_DATE);
        cursor = db.rawQuery( queryPriceSum, null );
        cursor.moveToNext();

        String sum = String.valueOf(cursor.getInt(0));
        sum_view.setText(sum);

        //캘린더로 이동 버튼 설정
        btn_go_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account.this, Calendar.class);
                startActivity(intent);
            }

        });

        String querySelectAll = String.format( "SELECT * FROM %s WHERE date = '%s'", TABLE_NAME, View_DATE);
        cursor = db.rawQuery( querySelectAll, null );
        myAdapter = new MyCursorAdapter ( this, cursor );

        list.setAdapter( myAdapter );

    }

    public void OnClick_addButton( View v ){

        EditText eContext ,ePrice;

        eContext = findViewById( R.id.context );
        ePrice =  findViewById( R.id.price );

        String contexts = eContext.getText().toString();
        int price = Integer.parseInt( ePrice.getText().toString() );
       // String today_Date = getToday_date();

        String query = String.format( "INSERT INTO %s VALUES ( null, '%s', %d, '%s' );", TABLE_NAME, contexts, price, View_DATE);
        db.execSQL( query );


        // 총합 가격 표시
        String queryPriceSum = String.format( " SELECT SUM(price) FROM %s WHERE date = '%s'", TABLE_NAME, View_DATE);
        cursor = db.rawQuery( queryPriceSum, null );
        cursor.moveToNext();
        String sum = String.valueOf(cursor.getInt(0));
        sum_view.setText(sum);

        String querySelectAll = String.format( "SELECT * FROM %s WHERE date = '%s'", TABLE_NAME, View_DATE);
        cursor = db.rawQuery( querySelectAll, null );
        myAdapter.changeCursor( cursor );

        eContext.setText( "" );
        ePrice.setText( "" );

        try {
            int total= Integer.valueOf(sum);
            if(total>=10000&&total<20000){
                Toast.makeText(getApplicationContext(),"10,000원 이상 사용하였습니다",
                        Toast.LENGTH_LONG).show();
            }else if(total>=20000&&total<30000){
                Toast.makeText(getApplicationContext(),"20,000원 이상 사용하였습니다",
                        Toast.LENGTH_LONG).show();
            }else if(total>=30000&&total<40000){
                Toast.makeText(getApplicationContext(),"30,000원 이상 사용하였습니다",
                        Toast.LENGTH_LONG).show();
            }else if(total>=40000&&total<50000){
                Toast.makeText(getApplicationContext(),"40,000원 이상 사용하였습니다",
                        Toast.LENGTH_LONG).show();
            }else if(total>=50000&&total<100000){
                Toast.makeText(getApplicationContext(),"50,000원 이상 사용하였습니다",
                        Toast.LENGTH_LONG).show();
            }else if(total>=100000){
                Toast.makeText(getApplicationContext(),"100,000원 이상 사용하였습니다",
                        Toast.LENGTH_LONG).show();
            }
        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }

        // 추가 버튼 누른 후 키보드 안보이게 설정..
        InputMethodManager key =  (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE );
        key.hideSoftInputFromWindow( ePrice.getWindowToken(), 0 );
    }
    public void onClick_removeButton(View v){
        db = Helper.getWritableDatabase();
        Helper.onUpgrade(db, 1, 2);
        db.close();
        Toast.makeText(getApplicationContext(),"초기화 되었습니다",Toast.LENGTH_SHORT).show();

    }
    static public String getToday_date(){
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy/M/d", Locale.KOREA);
        Date currentTime = new Date();
        String Today_day = mSimpleDateFormat.format(currentTime).toString();
        return Today_day;
    }

}