package com.example.mirco.civichacking;

/**
 * Created by mirco on 29/03/2018.
 */
import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


public class Tab_Home extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    private ImageView imageView;
    private ImageView fieraImageView;
    private ImageView manifestazioneImageView;
    private ImageView spettacoloImageView;
    private ImageView sportImageView;
    private TextView nameTextView;
    private TextView mailTextView;
    private Button button;
    private GoogleApiClient googleApiClient;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso )
                .build();


    }

    @Override
    public void onStart(){
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        }
        else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });

        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            nameTextView.setText(account.getDisplayName());
            mailTextView.setText(account.getEmail());
            Glide.with(getActivity()).load(account.getPhotoUrl()).into(imageView);

        }
        else {
            goLoginScreen();
        }
    }

    private void goLoginScreen() {
        Intent intent = new Intent(getActivity(), Start.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_home, container, false);

        imageView = (ImageView)rootView.findViewById(R.id.imageGoogle);
        fieraImageView = (ImageView) rootView.findViewById(R.id.fiera);
        manifestazioneImageView = (ImageView) rootView.findViewById(R.id.manifestazione);
        spettacoloImageView = (ImageView) rootView.findViewById(R.id.spettacolo);
        sportImageView = (ImageView) rootView.findViewById(R.id.sport);
        nameTextView = (TextView)rootView.findViewById(R.id.nameGoogle);
        mailTextView = (TextView) rootView.findViewById(R.id.mailGoogle);
        button = (Button) rootView.findViewById(R.id.butLogout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if(status.isSuccess()){
                            goLoginScreen();
                        }
                        else {
                            Toast.makeText(getActivity(), "Errore", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        fieraImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "Fiera";
                Intent intent = new Intent(getActivity(), Search.class);
                intent.putExtra("search", query);
                startActivity(intent);
            }
        });

        manifestazioneImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "Manifestazione";
                Intent intent = new Intent(getActivity(), Search.class);
                intent.putExtra("search", query);
                startActivity(intent);

            }
        });

        spettacoloImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "Spettacolo";
                Intent intent = new Intent(getActivity(), Search.class);
                intent.putExtra("search", query);
                startActivity(intent);

            }
        });

        sportImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "Sport";
                Intent intent = new Intent(getActivity(), Search.class);
                intent.putExtra("search", query);
                startActivity(intent);

            }
        });



        return rootView;

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

