package com.metris.me_tris;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class ActivityDaftarAkun extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    // UI references.
    private AutoCompleteTextView editTextEmailView;
    private AutoCompleteTextView editTextUsername;
    private EditText editTextPasswordView;
    private EditText editTextPasswordValidation;

    private ProgressBar progressBar;
    private Button buttonDaftar;

    private TextView tvLogin;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_akun);
        // Set up the login form.
        editTextEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        editTextPasswordView = (EditText) findViewById(R.id.password);
        editTextPasswordValidation = (EditText) findViewById(R.id.validate_password);
        editTextUsername =(AutoCompleteTextView) findViewById(R.id.username);
        buttonDaftar = (Button) findViewById(R.id.buton_daftar);
        tvLogin = findViewById(R.id.textviewDaftar_login);

        progressBar = findViewById(R.id.daftar_progress);

        mAuth = FirebaseAuth.getInstance();

        populateAutoComplete();
        editTextPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    return true;
                }
                return false;
            }
        });

        tvLogin.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v) {
                updateUI();
            }
        });

        buttonDaftar.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser()
    {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmailView.getText().toString().trim();
        String password = editTextPasswordView.getText().toString().trim();
        String passwordVal = editTextPasswordValidation.getText().toString().trim();

        if ( email.isEmpty() )
        {
            editTextEmailView.setError("Masukkan Email");
            editTextEmailView.requestFocus();
            return;
        }
        if ( !Patterns.EMAIL_ADDRESS.matcher(email).matches() )
        {
            editTextEmailView.setError("Format email salah");
            editTextEmailView.requestFocus();
            return;
        }
        if ( username.isEmpty() )
        {
            editTextUsername.setError("Masukkan nama");
            editTextUsername.requestFocus();
            return;
        }
        if ( password.isEmpty() )
        {
            editTextPasswordView.setError("Masukkan password");
            editTextPasswordView.requestFocus();
            return;
        }
        if ( password.length() < 6 )
        {
            editTextPasswordView.setError("Password minimal 6 karakter");
            editTextPasswordView.requestFocus();
            return;
        }
        if ( passwordVal.isEmpty() )
        {
            editTextPasswordValidation.setError("Masukkan validasi password");
            editTextPasswordValidation.requestFocus();
            return;
        }
        if ( !password.equals( passwordVal) )
        {
            editTextPasswordValidation.setError("Password dan validasi password tidak sama");
            editTextPasswordValidation.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI();
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(ActivityDaftarAkun.this,  "Akun Anda sudah pernah terdaftar.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI();
                            }
                            else
                            {
                                Toast.makeText(ActivityDaftarAkun.this,  "Gagal mendaftar. Coba periksa koneksi Anda.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void updateUI()
    {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null) {
            Intent intent = new Intent(ActivityDaftarAkun.this, ActivityLogin.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(ActivityDaftarAkun.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(editTextEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }
}

