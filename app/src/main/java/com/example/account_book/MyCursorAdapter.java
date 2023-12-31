package com.example.account_book;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MyCursorAdapter extends CursorAdapter {
    String TAG = "MyCursorAdapter";
    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from( context );
        View v = inflater.inflate( R.layout.list, parent,false );
        return v;
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView item_context = (TextView) view.findViewById( R.id.item_context );
        TextView item_price = (TextView) view.findViewById( R.id.item_price );

        //getColumnindex(name) : name에 해당하는 필드의 인덱스 번호를 반환한다.
        //cursor.getString(index) : 해당 커서가 위치한 인덱스 위치의 값을 반환한다.
        String contexts = cursor.getString( cursor.getColumnIndex( Account.KEY_CONTEXT ) );
        String price = cursor.getString( cursor.getColumnIndex( Account.KEY_PRICE ) );

        item_context.setText( contexts );
        item_price.setText( price );

    }
}