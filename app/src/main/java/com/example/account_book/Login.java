package com.example.account_book;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

    public class Login extends Account {

        private EditText editTextPassword;
        private Button buttonLogin;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login);

            editTextPassword = findViewById(R.id.Password);
            buttonLogin = findViewById(R.id.Login);

            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String password = editTextPassword.getText().toString();

                    if (password.equals("1234")) {
                        Toast.makeText(Login.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(Login.this, Account.class);
                        startActivity(intent2);
                    } else {
                        Toast.makeText(Login.this, "비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
