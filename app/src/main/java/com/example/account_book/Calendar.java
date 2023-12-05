package com.example.account_book;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Random;

public class Calendar extends Account{

    private static final String TAG = "CalendarActivity";
    private CalendarView CalendarView;
    public TextView messageTextView;
    public Button generateButton;

    public String[] messages = {
            "부자되세요", "좋은 일이 생길거에요",
            "행운을 빕니다!", "행복한 하루 되세요!",
            "오늘도 좋은 하루 되세요!", "행복한 순간을 만끽하세요.",
            "모든 일이 잘 될 거에요!", "응원합니다","Good Luck"
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender);

        messageTextView = findViewById(R.id.messageTextView);
        generateButton = findViewById(R.id.generateButton);
        CalendarView = (CalendarView) findViewById(R.id.calendarView);

        CalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = i + "/" + (i1+1) + "/" + i2;
                Intent intent = new Intent(Calendar.this, Account.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RMessage();
            }
        });
    }
    public void RMessage() {
        Random random = new Random();
        int randomIndex = random.nextInt(messages.length);
        String randomMessage = messages[randomIndex];
        messageTextView.setText(randomMessage);
    }
}
