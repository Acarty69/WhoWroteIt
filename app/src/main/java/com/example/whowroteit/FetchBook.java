package com.example.whowroteit;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class FetchBook extends AsyncTask<String, Void, String> {

    private WeakReference<TextView> mTitleText1;
    private WeakReference<TextView> mAuthorText1;
    private WeakReference<TextView> mTitleText2;
    private WeakReference<TextView> mAuthorText2;
    private WeakReference<TextView> mTitleText3;
    private WeakReference<TextView> mAuthorText3;

    FetchBook(TextView titleText1, TextView authorText1, TextView titleText2, TextView authorText2, TextView titleText3, TextView authorText3) {
        this.mTitleText1 = new WeakReference<>(titleText1);
        this.mAuthorText1 = new WeakReference<>(authorText1);
        this.mTitleText2 = new WeakReference<>(titleText2);
        this.mAuthorText2 = new WeakReference<>(authorText2);
        this.mTitleText3 = new WeakReference<>(titleText3);
        this.mAuthorText3 = new WeakReference<>(authorText3);
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < Math.min(3, itemsArray.length()); i++) {
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                String title = volumeInfo.optString("title");
                String authors = volumeInfo.optString("authors");

                if (i == 0) {
                    mTitleText1.get().setText(title);
                    mAuthorText1.get().setText(authors);
                } else if (i == 1) {
                    mTitleText2.get().setText(title);
                    mAuthorText2.get().setText(authors);
                } else if (i == 2) {
                    mTitleText3.get().setText(title);
                    mAuthorText3.get().setText(authors);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            mTitleText1.get().setText(R.string.no_results);
            mAuthorText1.get().setText("");
            mTitleText2.get().setText(R.string.no_results);
            mAuthorText2.get().setText("");
            mTitleText3.get().setText(R.string.no_results);
            mAuthorText3.get().setText("");
        }
    }
}
