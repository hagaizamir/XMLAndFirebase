package hagai.edu.xmlandfirebase;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 1;
    @BindView(R.id.btnVerify)
    BootstrapButton btnVerify;
    @BindView(R.id.btnGoogle)
    SignInButton btnGoogle;

    //Properties:
    private GoogleApiClient mApiClient;//Take away
    private FirebaseAuth mAuth;
    @BindView(R.id.btnLogin)
    BootstrapButton btnLogin;
    @BindView(R.id.btnReigster)
    BootstrapButton btnReigster;
    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @BindView(R.id.tilEmail)
    TextInputLayout tilEmail;
    @BindView(R.id.tilPassword)
    TextInputLayout tilPassword;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.enableAutoManage(
                this/*Activity for onPause / Resume*/,
                this /*FailureListener*/);

        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestProfile()
                .build();

        builder.addApi(Auth.GOOGLE_SIGN_IN_API, gso);

        mApiClient = builder.build();
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
            showProgress(false);
            btnReigster.animate().
                    alpha(0).
                    rotation(360).
                    setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            btnLogin.setVisibility(View.GONE);
                            btnReigster.setVisibility(View.GONE);
                            etPassword.setVisibility(View.GONE);
                            etEmail.setVisibility(View.GONE);
                            tilEmail.setVisibility(View.GONE);
                            tilPassword.setVisibility(View.GONE);

                            btnVerify.setVisibility(View.VISIBLE);
                            btnVerify.animate().scaleX(2).scaleY(2);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });


            //  gotoMain();
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
            dialog.setCancelable(false);
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
        return isEmailValid() & isPasswordValid();

    }

    @OnClick(R.id.btnReigster)
    public void register() {
        if (!validateForm()) return;
        showProgress(true);
        mAuth.createUserWithEmailAndPassword(getEmail(), getPassword()).
                addOnFailureListener(onFailureListener).
                addOnSuccessListener(onSuccessListener);
    }

    boolean sent = false;

    @OnClick(R.id.btnVerify)
    public void onBtnVerifyClicked() {
        final FirebaseUser user = mAuth.getCurrentUser();

        if (!sent) {
            if (user == null) return; //we hates nulls.

            user.sendEmailVerification();
            Toast.makeText(this, "Sent", Toast.LENGTH_SHORT).show();
            sent = true;

            btnVerify.setText("Refresh");

        } else {
            user.reload().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    if (user.isEmailVerified())
                        gotoMain();
                }
            });
        }
        //user.isEmailValid()
        //user.sendEmailVerification()
        //user.reload()
    }

    @OnClick(R.id.btnGoogle)
    public void onViewClicked() {
        //Intent...GoogleApiClient
        Intent gsIntent = Auth.GoogleSignInApi.getSignInIntent(mApiClient);
        //startActivityForResult
        startActivityForResult(gsIntent,RC_SIGN_IN);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
    }
}