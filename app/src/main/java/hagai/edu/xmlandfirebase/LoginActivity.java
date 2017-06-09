package hagai.edu.xmlandfirebase;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.etPassword)
    TextInputLayout etPassword;
    @BindView(R.id.etEmail)
    TextInputLayout etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


    }


    @OnClick(R.id.btnRgister)
    public void Login() {
        Toast.makeText(this, "Login" , Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnLogin)
    public void Register() {
        Toast.makeText(this, "Register" , Toast.LENGTH_SHORT).show();
    }
}
