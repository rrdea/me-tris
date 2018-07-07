package com.metris.me_tris;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.auth.FirebaseUser;


public class FragmentAuthenication_Login extends Fragment {
    // UI references.
    private FirebaseAuth mAuth;
    private TextView textViewDaftarAkunbutt;
    private Button buttonLogin;
    private AutoCompleteTextView editTextEmail;
    private EditText editTextPassword;
    private ProgressBar progressBar;

    View view;

    public FragmentAuthenication_Login() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment_authenication__login, container, false);

        textViewDaftarAkunbutt = (TextView) view.findViewById(R.id.textviewLogin_daftar);
        buttonLogin = (Button) view.findViewById(R.id.email_sign_in_button);
        editTextEmail = (AutoCompleteTextView) view.findViewById(R.id.email);
        editTextPassword = (EditText) view.findViewById(R.id.password);
        progressBar = (ProgressBar) view.findViewById(R.id.login_progress);

        mAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                login();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI(mAuth.getCurrentUser());
    }


    private void login()
    {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if ( email.isEmpty() )
        {
            editTextEmail.setError("Masukkan Email");
            editTextEmail.requestFocus();
            return;
        }
        if ( !Patterns.EMAIL_ADDRESS.matcher(email).matches() )
        {
            editTextEmail.setError("Format email salah");
            editTextEmail.requestFocus();
            return;
        }
        if ( password.isEmpty() )
        {
            editTextPassword.setError("Masukkan password");
            editTextPassword.requestFocus();
            return;
        }
        if ( password.length() < 6 )
        {
            editTextPassword.setError("Password minimal 6 karakter");
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                        else
                        {
                            Toast.makeText(getContext().getApplicationContext(), task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });

    }

    private void updateUI(FirebaseUser user)
    {
        if (user != null)
        {
            Intent intent = new Intent(getContext(), HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
