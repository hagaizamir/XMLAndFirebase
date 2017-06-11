package hagai.edu.xmlandfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.btnLogin)
    BootstrapButton btnLogin;
    @BindView(R.id.btnRgister)
    BootstrapButton btnReigster;
    //Properties:
    private FirebaseAuth mAuth;

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        BootstrapText text = new BootstrapText.
                Builder(this).addText("Hello").
                addFontAwesomeIcon("fa_user").build();

        btnReigster.setBootstrapText(text);

        mAuth = FirebaseAuth.getInstance();
    }


    private String getEmail() {
        return etEmail.getText().toString();
    }

    private String getPassword() {
        return etPassword.getText().toString();
    }

    private boolean isEmailValid() {
        String email = getEmail();
        boolean isEmailValid = email.contains("@") && email.length() > 5;
        //Pattern emailAddress = Patterns.EMAIL_ADDRESS;
        //return emailAddress.matcher(email).matches();
        if (!isEmailValid)
            etEmail.setError("Invalid Email Address");
        else
            etEmail.setError(null);
        return isEmailValid;
    }

    private boolean isPasswordValid() {
        String password = getPassword();
        boolean isPasswordValid = password.length() > 5;

        if (!isPasswordValid)
            etPassword.setError("Password Must contain At least 6 Characters");
        else
            etPassword.setError(null);
        return isPasswordValid;
    }

    OnFailureListener onFailureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            showError(e);
        }
    };

    OnSuccessListener<AuthResult> onSuccessListener = new OnSuccessListener<AuthResult>() {
        @Override
        public void onSuccess(AuthResult authResult) {
            gotoMain();
        }
    };


    private void gotoMain() {
        showProgress(false);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK
        );
        startActivity(intent);
    }

    private ProgressDialog dialog;

    //A Progress dialog contains:
    //Title, Message, Icon AND A ProgressBar.
    private void showProgress(boolean show) {
        //Lazy Loading... Not initialized in onCreate/start
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            //TODO: Dismiss
            dialog.setMessage("Logging You In");
            dialog.setTitle("Connecting...");
        }

        if (show)
            dialog.show();
        else
            dialog.dismiss();
    }

    private void showError(Exception e) {
        showProgress(false);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        };

        Snackbar.make(etEmail, e.getMessage(), Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", listener).show();
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        if (!validateForm()) return;
        mAuth.
                signInWithEmailAndPassword(getEmail(), getPassword()).
                addOnSuccessListener(onSuccessListener).
                addOnFailureListener(onFailureListener);
    }


    private boolean validateForm() {
        //get the email
        String email = getEmail();
        //get the password
        String password = getPassword();
        //isEmail valid
        //isPassword valid
        return !isEmailValid() | !isPasswordValid();

    }

    @OnClick(R.id.btnRgister)
    public void register() {
        if (!validateForm()) return;
        showProgress(true);
        mAuth.createUserWithEmailAndPassword(getEmail(), getPassword()).
                addOnFailureListener(onFailureListener).
                addOnSuccessListener(onSuccessListener);
    }
}