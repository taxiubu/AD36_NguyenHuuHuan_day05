package com.example.khongmuonchepphat.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.khongmuonchepphat.R;
import com.example.khongmuonchepphat.model.CheckUser;
import com.example.khongmuonchepphat.model.SQLHelper;
import com.example.khongmuonchepphat.model.User;
import com.example.khongmuonchepphat.presenter.MainPresenter;

import java.util.List;

public class CreatAccount extends Fragment {
    Button btDone;
    SQLHelper sqlHelper;
    EditText etCreatUsername, etCreatPassword, etRePassword;
    TextView tvErrCreatUsername, tvErrCreatPassword, tvErrRePassword;
    MainPresenter mainPresenter;
    String creatUsername, creatPass, rePass;

    public static CreatAccount newInstance() {

        Bundle args = new Bundle();

        CreatAccount fragment = new CreatAccount();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.creat_account, container, false);
        etCreatUsername= view.findViewById(R.id.etCreatUsername);
        etCreatPassword= view.findViewById(R.id.etCreatPassword);
        etRePassword= view.findViewById(R.id.etRePassword);

        tvErrCreatUsername= view.findViewById(R.id.tvErrorCreatUser);
        tvErrCreatPassword= view.findViewById(R.id.tvErrorCreatPass);
        tvErrRePassword= view.findViewById(R.id.tvErrorRePass);

        sqlHelper= new SQLHelper(getContext());
        final List<User> userList= sqlHelper.getAllUserAdvanced();

        mainPresenter= new MainPresenter();

        btDone= view.findViewById(R.id.btDone);
        btDone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                creatUsername=etCreatUsername.getText().toString();
                creatPass= etCreatPassword.getText().toString();
                rePass= etRePassword.getText().toString();

                int dk1= mainPresenter.duplicateUsername(creatUsername, userList);
                boolean dk2= mainPresenter.passwordConditions(creatPass);
                int dk3= mainPresenter.checkRePass(creatPass, rePass);

                if(dk1!=0)
                    tvErrCreatUsername.setText(R.string.errorCreatName);
                else {
                    tvErrCreatUsername.setText(R.string.done);
                    tvErrCreatUsername.setTextColor(R.color.colorGreen);
                    if(dk2==false ){
                        tvErrCreatPassword.setText(R.string.errorCreatPass);
                    }
                    else if(dk2==true && dk3==0){
                        tvErrCreatPassword.setText(R.string.done);
                        tvErrCreatPassword.setTextColor(R.color.colorGreen);
                        tvErrRePassword.setText(R.string.errorRePass);
                    }
                    else if (dk1==0 && dk2==true && dk3!=0){
                        Toast.makeText(getContext(), "Done", Toast.LENGTH_LONG).show();
                        sqlHelper.insertUser(creatUsername, creatPass);
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }







                /*Intent intent= new Intent(getContext(), MainActivity.class);
                startActivity(intent);*/
            }
        });
        return view;
    }
}
