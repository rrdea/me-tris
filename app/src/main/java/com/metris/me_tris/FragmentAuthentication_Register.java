package com.metris.me_tris;

import android.content.Intent;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.hash.Hashing;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.metris.me_tris.Model.User;

import java.nio.charset.StandardCharsets;


public class FragmentAuthentication_Register extends Fragment {
    // UI references.
    private AutoCompleteTextView editTextEmailView;
    private AutoCompleteTextView editTextUsername;
    private EditText editTextPasswordView;
    private EditText editTextPasswordValidation;

    private ProgressBar progressBar;
    private Button buttonDaftar;

    private FirebaseAuth mAuth;

    private View view;

    public FragmentAuthentication_Register() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_authentication_register, container, false);

        editTextEmailView = (AutoCompleteTextView) view.findViewById(R.id.email);
        editTextPasswordView = (EditText) view.findViewById(R.id.password);
        editTextPasswordValidation = (EditText) view.findViewById(R.id.validate_password);
        editTextUsername =(AutoCompleteTextView) view.findViewById(R.id.username);
        buttonDaftar = (Button) view.findViewById(R.id.buton_daftar);

        progressBar = view.findViewById(R.id.daftar_progress);

        mAuth = FirebaseAuth.getInstance();


        buttonDaftar.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


    private void registerUser()
    {
        final String username = editTextUsername.getText().toString().trim();
        final String email = editTextEmailView.getText().toString().trim();
        final String password = editTextPasswordView.getText().toString().trim();
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
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // push user data to database
                            User newUser = new User();
                            try {
                                String digest = Hashing.sha256()
                                        .hashString(password, StandardCharsets.UTF_8)
                                        .toString();
                                newUser.setName(username);
                                newUser.setEmail(email);
                                newUser.setPassword(digest);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            FirebaseUser user = mAuth.getCurrentUser();

                            updateUI();
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(getContext(),  "Akun Anda sudah pernah terdaftar.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI();
                            }
                            else
                            {
                                Toast.makeText(getContext(),  "Gagal mendaftar. Coba periksa koneksi Anda.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void updateUI()
    {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null) {
            Intent intent = new Intent(getContext(), ActivityAuthentication.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(getContext(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

}
