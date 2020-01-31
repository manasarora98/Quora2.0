package com.project.quora20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.quora20.adapter.QuestionAnswerAdapter;
import com.project.quora20.dto.AnswerDTO;
import com.project.quora20.entity.Answer;
import com.project.quora20.retrofit.QuoraRetrofitService;
import com.project.quora20.retrofit.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionAnswer extends AppCompatActivity implements QuestionAnswerAdapter.AnswerCommunication {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager qa_answerLayoutManager;
    QuoraRetrofitService quoraRetrofitService;
    private SharedPreferences sharedPreferences;
    List<Answer> answerList;
    TextView qa_questionText;
    TextView qa_answerText;
    ImageButton sendAnswerButton;
    final AnswerDTO answerdto=new AnswerDTO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);
        recyclerView = findViewById(R.id.qa_answerListRecycler);
        qa_answerLayoutManager = new LinearLayoutManager(this);
        //final LinearLayout addAnswer = findViewById(R.id.addAnswerLayout);
        //addAnswer.onFoc
        /*addAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAnswer.setOnFocusChangeListener();
            }
        });*/
        Intent intent = getIntent();
//        String QID=intent.getStringExtra("QID");
//        String OrgId=intent.getStringExtra("OrgId");
//        String QuesBody=intent.getStringExtra("QuesBody");
//        String BestAns=intent.getStringExtra("BestAns");
//        String CategoryId=intent.getStringExtra("CategoryId");
        recyclerView.setLayoutManager(qa_answerLayoutManager);
        recyclerView.setHasFixedSize(true);

        //answerdto = new AnswerDTO();

        answerdto.setQuestionId(intent.getStringExtra("QID"));
        answerdto.setOrgId(intent.getStringExtra("OrgId"));
        qa_questionText = findViewById(R.id.qa_questionText);
        qa_questionText.setText(intent.getStringExtra("QuesBody"));


        sendAnswerButton = findViewById(R.id.qa_sendAnswerButton);
        sendAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAnswers(answerdto);
                viewAnswers();
                Toast toast = Toast.makeText(getApplicationContext(), "Answers posted ", Toast.LENGTH_SHORT);
                toast.show();

            }
        });


    }

    @Override
    public void onClick(Answer answer) {

    }

    //view All answers by a particular QuestionId
    void addAnswers(AnswerDTO answerDTO) {
        sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
        String userId = sharedPreferences.getString("UserId", "");
        answerDTO.setUserId(userId);


        qa_answerText = findViewById(R.id.qa_answerText);
        answerDTO.setAnswerBody(qa_answerText.getText().toString());

        quoraRetrofitService = RetrofitClientInstance.getRetrofitInstance().create(QuoraRetrofitService.class);
        Call<String> callAddAnswer = quoraRetrofitService.addAnswer(answerDTO);
        callAddAnswer.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println("OnResponse Add Answer");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("OnFailure Add Answer");

            }
        });


    }

    void viewAnswers()
    {
        Call<List<Answer>> callAnswerList = quoraRetrofitService.getAnswersByQuestionId(answerdto.getQuestionId());
        callAnswerList.enqueue(new Callback<List<Answer>>() {
            @Override
            public void onResponse(Call<List<Answer>> call, Response<List<Answer>> response) {
                answerList = response.body();
                adapter = new QuestionAnswerAdapter(answerList, QuestionAnswer.this);
                recyclerView.setAdapter(adapter);


                System.out.println("OnResponse View Answer By Qid");
            }

            @Override
            public void onFailure(Call<List<Answer>> call, Throwable t) {
                System.out.println("OnFailure View Answer By Qid");
            }
        });

    }
}
