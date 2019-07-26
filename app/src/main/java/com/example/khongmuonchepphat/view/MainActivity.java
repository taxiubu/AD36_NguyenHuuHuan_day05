package com.example.khongmuonchepphat.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khongmuonchepphat.Complete;
import com.example.khongmuonchepphat.R;
import com.example.khongmuonchepphat.model.SQLHelper;
import com.example.khongmuonchepphat.model.User;
import com.example.khongmuonchepphat.presenter.MainPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    MainPresenter mainPresenter;
    Button btLogin, btCreatAcc;
    EditText etUserName, etPassWord;
    TextView tvErrorUser, tvErrorPass;
    User user;
    SQLHelper sqlHelper;
    List<User> userList;
    String usernameLogin, passwordLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(R.string.login);

        tvErrorUser= findViewById(R.id.tvErrorUser);
        tvErrorPass= findViewById(R.id.tvErrorPass);

        etUserName= findViewById(R.id.etUserName);
        etPassWord= findViewById(R.id.etPassWord);



        // khởi tạo ở trong hàm oncreate
        sqlHelper= new SQLHelper(getBaseContext());
        sqlHelper.insertUser("mocoinguoiyeu99", "Huan1999@-@");
        mainPresenter= new MainPresenter();
        userList= sqlHelper.getAllUserAdvanced();

        btLogin= findViewById(R.id.btLogin);
        btCreatAcc= findViewById(R.id.btCreatAccount);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameLogin= etUserName.getText().toString();
                passwordLogin= etPassWord.getText().toString();
                user= new User(usernameLogin, passwordLogin);
                if(mainPresenter.userCheck(user, userList)!=0){
                    Toast.makeText(getBaseContext(), "Complect", Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(getBaseContext(), Complete.class);
                    startActivity(intent);
                }
                else{
                    if(mainPresenter.duplicateUsername(usernameLogin, userList)==0)
                        tvErrorUser.setText(R.string.errorUser);
                    else if (mainPresenter.duplicatePassword(passwordLogin, userList)==0)
                        tvErrorPass.setText(R.string.errorPass);
                    Toast.makeText(getBaseContext(), "Fail", Toast.LENGTH_LONG).show();
                }
            }

        });
        btCreatAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragment(CreatAccount.newInstance());
            }
        });

    }

    public void getFragment(Fragment fragment){
        try{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment).commit();

        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "getFragment: "+e.getMessage());
        }
    }

}
