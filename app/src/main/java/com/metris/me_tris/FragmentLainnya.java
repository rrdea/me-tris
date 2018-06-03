package com.metris.me_tris;

//<<<<<<< HEAD
//import android.app.Fragment;
import android.content.Intent;
//=======
import android.support.v4.app.Fragment;
//>>>>>>> b7dac3a12688fee19f20d4c4df515c79cc9d7671
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class FragmentLainnya extends Fragment {

    View view;
    Button buttonKeluar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lainnya, container, false);

        // Sign out event handler
        // Added by Ayu for testing only
        buttonKeluar = (Button) view.findViewById(R.id.btn_lainnya_keluar);
        buttonKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), ActivityLogin.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}
