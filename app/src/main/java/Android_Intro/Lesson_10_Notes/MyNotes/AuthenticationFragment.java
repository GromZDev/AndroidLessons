package Android_Intro.Lesson_10_Notes.MyNotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import Android_Intro.Lesson_10_Notes.MainActivity;
import Android_Intro.Lesson_10_Notes.R;

public class AuthenticationFragment extends Fragment {

    private static final String TAG = "GoogleAuth";
    private View authView;
    private com.google.android.gms.common.SignInButton signInButton;
    private GoogleSignInOptions googleSignInOptions;
    private MaterialButton goToNotes;
    private GoogleSignInClient googleSignInClient;

    private static final int RC_SIGN_IN = 40404;
    private TextView emailView;


    public static AuthenticationFragment newInstance() {
        return new AuthenticationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        authView = inflater.inflate(R.layout.fragment_authentication, container, false);

        initGoogleSign();
        initView(authView);
        enableSign();



        return authView;
    }

    private void initView(View authView) {
        signInButton = authView.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSignIn();
            }
        });
        emailView = authView.findViewById(R.id.email);
        goToNotes = authView.findViewById(R.id.go_to_my_notes);

        goToNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = NoteScreenFragment.newInstance(null);
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    private void initGoogleSign() {
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(getContext()), googleSignInOptions);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task =
                    GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account =
                    task.getResult(ApiException.class);
// Регистрация прошла успешно
            disableSign();
            updateUI(account.getEmail());
        } catch (ApiException e) {
            Log.w(TAG, ">>>>>>>>>>>>>>>>>>>>  signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account =
                GoogleSignIn.getLastSignedInAccount(Objects.requireNonNull(getContext()));
        if (account != null) {
// Пользователь уже входил, сделаем кнопку недоступной
            disableSign();
// Обновим почтовый адрес этого пользователя и выведем его на экран
            updateUI(account.getEmail());
        }
    }

    private void disableSign() {
        signInButton.setEnabled(false);
        goToNotes.setEnabled(true);
    }

    private void updateUI(String email) {
        emailView.setText(email);
    }

    private void goSignIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void enableSign() {
        signInButton.setEnabled(true);
        goToNotes.setEnabled(false);
    }


}