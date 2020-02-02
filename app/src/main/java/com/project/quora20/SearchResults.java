package com.project.quora20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.project.quora20.adapter.SearchAdapter;
import com.project.quora20.dto.SearchRequestDTO;
import com.project.quora20.dto.SearchResponseOrganizationDTO;
import com.project.quora20.dto.SearchResponseQuestionDTO;
import com.project.quora20.dto.SearchResponseUserDTO;
import com.project.quora20.entity.Question;
import com.project.quora20.retrofit.QuoraRetrofitService;
import com.project.quora20.retrofit.RetrofitSearchInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResults extends AppCompatActivity implements SearchAdapter.QuestionCommunication {
    private RecyclerView searchRecyclerView;
    private RecyclerView.Adapter searchAdapter;

    @Override
    public void onClick(Question question) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Intent intent=getIntent();
        String query=intent.getStringExtra("searchKey");
        final SearchRequestDTO searchRequestDTO=new SearchRequestDTO();
        searchRequestDTO.setSearchTerm(query);

        QuoraRetrofitService quoraRetrofitService= RetrofitSearchInstance.getRetrofitInstance().create(QuoraRetrofitService.class);
        Call <List<SearchResponseUserDTO>> call=quoraRetrofitService.searchUser(searchRequestDTO);
        call.enqueue(new Callback<List<SearchResponseUserDTO>>() {
            @Override
            public void onResponse(Call<List<SearchResponseUserDTO>> call, Response<List<SearchResponseUserDTO>> response) {
                if(response.body()!=null){
                    List<SearchResponseUserDTO>userList=response.body();
                    for(SearchResponseUserDTO s:userList){
                        System.out.println(s);
                    }
                }
                else{
                    System.out.println("NULL USER LIST");
                }
                System.out.println("OnResponse SearchUser");
            }
            @Override
            public void onFailure(Call<List<SearchResponseUserDTO>> call, Throwable t) {
                System.out.println("OnFailure SearchUser"+t.getMessage());
            }
        });

        QuoraRetrofitService quoraRetrofitService1=RetrofitSearchInstance.getRetrofitInstance().create(QuoraRetrofitService.class);
        Call <List<SearchResponseQuestionDTO>>searchResponseQuestionDTOCall=quoraRetrofitService1.searchQuestion(searchRequestDTO);
        searchResponseQuestionDTOCall.enqueue(new Callback<List<SearchResponseQuestionDTO>>() {
            @Override
            public void onResponse(Call<List<SearchResponseQuestionDTO>> call, Response<List<SearchResponseQuestionDTO>> response) {
                if(response.body()!=null){
                    List<SearchResponseQuestionDTO>questionList=response.body();
                    System.out.println("QUESTIONS");
                    for(SearchResponseQuestionDTO s:questionList){
                        System.out.println(s);
                    }
                }
                else{
                    System.out.println("NULL QUESTION LIST");
                }
                System.out.println("OnResponse SearchQuestion");
            }

            @Override
            public void onFailure(Call<List<SearchResponseQuestionDTO>> call, Throwable t) {
                System.out.println("OnFailure SearchQuestion"+t.getMessage());
            }
        });

        QuoraRetrofitService quoraRetrofitService2=RetrofitSearchInstance.getRetrofitInstance().create(QuoraRetrofitService.class);
        Call<List<SearchResponseOrganizationDTO>>searchResponseOrganizationDTOCall=quoraRetrofitService2.searchOrganization(searchRequestDTO);
        searchResponseOrganizationDTOCall.enqueue(new Callback<List<SearchResponseOrganizationDTO>>() {
            @Override
            public void onResponse(Call<List<SearchResponseOrganizationDTO>> call, Response<List<SearchResponseOrganizationDTO>> response) {
               if(response.body()!=null){
                   System.out.println("ORGANIZATIONS");
                List<SearchResponseOrganizationDTO>organizationList=response.body();
                for(SearchResponseOrganizationDTO s:organizationList){
                    System.out.println(s);
                }
               }
               else{
                   System.out.println("NULL ORGANIZATION LIST");
               }
            }

            @Override
            public void onFailure(Call<List<SearchResponseOrganizationDTO>> call, Throwable t) {
                System.out.println("On Failure SearchOrganization"+t.getMessage());
            }
        });


    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
