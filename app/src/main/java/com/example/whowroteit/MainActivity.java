package com.example.whowroteit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText mBookInput;
    private TextView mTitleText1;
    private TextView mAuthorText1;
    private TextView mTitleText2;
    private TextView mAuthorText2;
    private TextView mTitleText3;
    private TextView mAuthorText3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mBookInput = (EditText)findViewById(R.id.bookInput);
        mTitleText1 = findViewById(R.id.titleText1);
        mAuthorText1 = findViewById(R.id.authorText1);
        mTitleText2 = findViewById(R.id.titleText2);
        mAuthorText2 = findViewById(R.id.authorText2);
        mTitleText3 = findViewById(R.id.titleText3);
        mAuthorText3 = findViewById(R.id.authorText3);
    }

    public void searchBooks(View view) {
        String queryString = mBookInput.getText().toString();
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected() && queryString.length() != 0) {
            new FetchBook(mTitleText1, mAuthorText1, mTitleText2, mAuthorText2, mTitleText3, mAuthorText3).execute(queryString);
            mAuthorText1.setText("");
            mTitleText1.setText(R.string.loading);
            mAuthorText2.setText("");
            mTitleText2.setText("");
            mAuthorText3.setText("");
            mTitleText3.setText("");
        } else {
            if (queryString.length() == 0) {
                mAuthorText1.setText("");
                mTitleText1.setText(R.string.no_search_term);
                mAuthorText2.setText("");
                mTitleText2.setText("");
                mAuthorText3.setText("");
                mTitleText3.setText("");
            } else {
                mAuthorText1.setText("");
                mTitleText1.setText(R.string.no_network);
                mAuthorText2.setText("");
                mTitleText2.setText("");
                mAuthorText3.setText("");
                mTitleText3.setText("");
            }
        }
    }
}