package trendy.coloc;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import trendy.coloc.entities.User;
import trendy.coloc.tools.SaveSharedPreference;


public class FbLogin extends AppCompatActivity {

    private TextView info;
    private LoginButton loginButton;
    //private Button loginButton;
    String email = "", birthday = "", gender = "", first_name = "", last_name = "";
    private CallbackManager callbackManager;
    TextView tvwelcome;
    User user_added, fb_usr_exists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_fb_login);
        info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button3);
        tvwelcome = (TextView) findViewById(R.id.connec3);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));

        // loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                tvwelcome.setVisibility(View.VISIBLE);

                String userId = loginResult.getAccessToken().getUserId();


                ProfilePictureView profilePictureView;

                profilePictureView = (ProfilePictureView) findViewById(R.id.image);

                profilePictureView.setProfileId(userId);


                Toast.makeText(getApplicationContext(), loginResult.getAccessToken().getToken(), Toast.LENGTH_SHORT).show();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                try {
//                                    JSONObject objecttmp = new JSONObject(object.get("email").toString());

                                    //email = object.get("email").toString();
                                    email = object.getString("email");
                                    birthday = object.getString("birthday");
                                    gender = object.getString("gender");
                                    first_name = object.getString("first_name");
                                    last_name = object.getString("last_name");
                                    Toast.makeText(getApplicationContext(), "Email:" + email, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), "Birthday:" + birthday, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), "Gender:" + gender, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), "Fisrtt Name:" + first_name, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), "First Name:" + last_name, Toast.LENGTH_SHORT).show();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                //    parameters.putString("fields", "email,user_birthday");
                // parameters.putString("fields", "id, first_first_name, last_first_name, email,gender, birthday, location");

                parameters.putString("fields", "id,first_name,last_name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();

                Toast.makeText(getApplicationContext(), "Email:" + email, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Birthday:" + birthday, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Gender:" + gender, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "First Name:" + first_name, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Last Name:" + last_name, Toast.LENGTH_SHORT).show();
///////////////////////we test to check if user has logged with this account before, if he did not then we insert else we open a session


                User user = new User();

                User fb_usr_exists = new User();

                fb_usr_exists = user.CheckUserExistsByKey("id", userId);

                if (fb_usr_exists != null) {

                    ////open a session for user:


                    SaveSharedPreference.setUserId(FbLogin.this, userId);
                    Intent i = new Intent(FbLogin.this, MenuActivity.class);


                }
//////////////////////////////user does not exist:
                else {

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

                    try {
                        Date converted_birthday = dateFormat.parse(birthday);

                        user_added = new User();
                        user_added = user.addUser(userId, first_name, last_name, converted_birthday, email, null, gender, null, null, null, null);

                    } catch (ParseException e) {
                    }


                }


            }

            @Override
            public void onCancel() {

                info.setText("Login attempt canceled.");

            }

            @Override
            public void onError(FacebookException e) {

                info.setText("Login attempt failed.");

            }


        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void get_user_info(AccessToken accessToken) {


    }

}
