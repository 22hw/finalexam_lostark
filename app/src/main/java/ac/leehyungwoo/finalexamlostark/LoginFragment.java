package ac.leehyungwoo.finalexamlostark;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {
    static RequestQueue requestQueue;
    String urlStr = "https://developer-lostark.game.onstove.com/news/events";
    String JWT = null;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);
        EditText editText = rootView.findViewById(R.id.JWTKEY);
        Button loginbutton = rootView.findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JWT = "bearer " + editText.getText();
                requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                makeRequest();
            }
        });
        Button singbutton = rootView.findViewById(R.id.signbutton);
        singbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://developer-lostark.game.onstove.com/"));
                view.getContext().startActivity(intent);
            }
        });
        return rootView;
    }
    private void makeRequest() {
        StringRequest request = new StringRequest(Request.Method.GET, urlStr, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(),"연결오류",Toast.LENGTH_LONG).show();
            }
        }) {
            final String accept = "application/json";
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map  params = new HashMap();
                params.put("accept", accept);
                params.put("authorization", JWT);

                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }
    private void processResponse(String response) { //Json 데이터 파싱
        try {
            JSONArray jsonArray = new JSONArray(response);
            MainActivity activity = (MainActivity) getActivity();
            activity.setJWT(JWT);
            activity.onFragmentChanged(1);
            Toast.makeText(getActivity().getApplicationContext(),"연결성공",Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            Toast.makeText(getActivity().getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}