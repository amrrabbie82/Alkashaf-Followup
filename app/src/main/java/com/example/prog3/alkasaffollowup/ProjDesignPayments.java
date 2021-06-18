package com.example.prog3.alkasaffollowup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.prog3.alkasaffollowup.Adapters.DesignPaymentsAdapter;
import com.example.prog3.alkasaffollowup.Adapters.ScdualeFeesAdapter;
import com.example.prog3.alkasaffollowup.Data.UrlPath;
import com.example.prog3.alkasaffollowup.Model.DesPay;
import com.example.prog3.alkasaffollowup.Model.DesignPayement;
import com.example.prog3.alkasaffollowup.Model.ProjectFees.ProjectFee;
import com.example.prog3.alkasaffollowup.Model.SchFees;
import com.example.prog3.alkasaffollowup.Services.ProjectsInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjDesignPayments extends AppCompatActivity {

    private ListView designpaymentlist;
    private String guid;
    private int sn;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proj_design_payments);


        designpaymentlist=findViewById(R.id.desgnpaymentslist);

        mProgressBar=findViewById(R.id.progress_bar);


        guid = getIntent().getExtras().getString("guid");
        sn = getIntent().getExtras().getInt("sn");



        getDesignPayement();


    }

    private void getDesignPayement() {
        String url= UrlPath.path;

        Retrofit.Builder builder=new Retrofit.Builder();
        builder.baseUrl(url).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ProjectsInterface projectsInterface = retrofit.create(ProjectsInterface.class);
        Call<List<com.example.prog3.alkasaffollowup.Model.DesignPay.DesignPayement>> contractInfo = projectsInterface.getDesignPayment(sn, guid);
        contractInfo.enqueue(new Callback<List<com.example.prog3.alkasaffollowup.Model.DesignPay.DesignPayement>>() {
            @Override
            public void onResponse(Call<List<com.example.prog3.alkasaffollowup.Model.DesignPay.DesignPayement>> call, Response<List<com.example.prog3.alkasaffollowup.Model.DesignPay.DesignPayement>> response) {
                List<com.example.prog3.alkasaffollowup.Model.DesignPay.DesignPayement> designPayements = response.body();


                viewDesignPayment(designPayements);
            }

            @Override
            public void onFailure(Call<List<com.example.prog3.alkasaffollowup.Model.DesignPay.DesignPayement>> call, Throwable t) {
                Toast.makeText(ProjDesignPayments.this,t.getMessage().toString(),Toast.LENGTH_LONG).show();

            }
        });

    }

    private void viewDesignPayment(List<com.example.prog3.alkasaffollowup.Model.DesignPay.DesignPayement> designPayements) {
        DesignPaymentsAdapter adp = new DesignPaymentsAdapter(this, designPayements);

        designpaymentlist.setAdapter(adp);

        designpaymentlist.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    public List<DesPay> getallClientsActivity() {
        List<DesPay> screens = new ArrayList<>();

        screens.add(new DesPay("Down", "Design","50%","Upon sign of contract ","Instant"));
        screens.add(new DesPay("1st", "Design","50%","Upon Completion of the Project ","Instant"));
        screens.add(new DesPay("Down", "Design","50%","Upon sign of contract ","Instant"));
        screens.add(new DesPay("Down", "Design","50%","Upon sign of contract ","Instant"));

        return screens;
    }
}