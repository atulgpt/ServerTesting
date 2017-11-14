package com.example.atulgupta.testing;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.atulgupta.testing.adapter.AdapterClass;
import com.example.atulgupta.testing.rest.ApiClient;
import com.example.atulgupta.testing.rest.ApiInterface;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName ();
    final ArrayList<String> time1ArrayList = new ArrayList<> ();
    final ArrayList<String> time2ArrayList = new ArrayList<> ();
    final ArrayList<String> time3ArrayList = new ArrayList<> ();
    @BindView(R.id.numberEditText)
    EditText numberEditText;
    @BindView(R.id.submitBtn)
    Button submitBtn;
    @BindView(R.id.resultTextView)
    TextView resultTextView;
    @BindView(R.id.resultListView)
    RecyclerView recyclerView;
    AdapterClass adapterClass;
    CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        ButterKnife.bind (this);
        submitBtn.setOnClickListener (this);
        LinearLayoutManager llm = new LinearLayoutManager (this);
        llm.setOrientation (LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize (true);
        recyclerView.setLayoutManager (llm);
        adapterClass = new AdapterClass (time1ArrayList, time2ArrayList, time3ArrayList, this, null);
        recyclerView.setAdapter (adapterClass);
        adapterClass.notifyDataSetChanged ();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId ();
        switch (id) {
            case R.id.submitBtn:
                int numOfTry = Integer.parseInt (numberEditText.getText ().toString ());
                periodicSendToServer (numOfTry);
                //doSomeBS(numOfTry);
                break;
        }
    }

    private void periodicSendToServer(final int numOfTry) {
        Log.d (TAG, "periodicSendToServer: numOfTry = " + numOfTry);
        if (numOfTry <= 0) {
            Log.d (TAG, "periodicSendToServer: array1 = "+time1ArrayList+" array2 = "+time2ArrayList+" array3 = "+time3ArrayList);
            adapterClass.notifyDataSetChanged ();
            return;
        }
        doSomeBS ();
        mCountDownTimer = new CountDownTimer (1000, 3 * 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                periodicSendToServer (numOfTry - 1);
            }
        }.start ();
    }

    private void doSomeBS() {
        time1ArrayList.add (String.valueOf (System.currentTimeMillis ()));
        Callback<String> longTime = new Callback<String> () {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful ()) {
                    Log.d (TAG, "onResponse: response = " + response.body ());
                    time2ArrayList.add (response.body ());
                    time3ArrayList.add (String.valueOf (System.currentTimeMillis ()));
                    adapterClass.notifyDataSetChanged ();
                    Log.d (TAG, "onResponse: array2 = "+time2ArrayList+" array3 = "+time3ArrayList);
                } else {
                    Log.d ("QuestionsCallback", "Code: " + response.code () + " Message: " + response.message ());
                }
                Date date =     new Date ();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace ();
                Log.d (TAG, "onFailure: error = "+t.getMessage ());
            }
        };

        ApiInterface apiService =
                ApiClient.getClient ().create (ApiInterface.class);

        Call<String> call = apiService.getTiming ();
        call.enqueue (longTime);
    }
}
