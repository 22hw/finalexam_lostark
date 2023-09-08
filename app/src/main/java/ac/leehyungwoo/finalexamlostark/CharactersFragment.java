package ac.leehyungwoo.finalexamlostark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class CharactersFragment extends Fragment {
    String urlStr = null;
    RequestQueue requestQueue;
    CharactersAdapter adapter;
    RecyclerView recyclerView;
    EditText editText;
    Button button;
    String name = null;
    String namehex = null;
    String JWT = null;
    final String accept = "application/json";
    public CharactersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_characters, container, false);
        if(JWT == null) {
            if (getArguments() != null) {
                JWT = getArguments().getString("JWT"); // mainactivity에서 받아온 값 넣기
            }
        }
        name = null;
        namehex = null;
        recyclerView = rootView.findViewById(R.id.recyclerView);
        editText = rootView.findViewById(R.id.editTextName);
        button = rootView.findViewById(R.id.search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editText.getText().toString();
                if(name.equals("") || name.equals(null)){
                    Toast.makeText(getActivity().getApplicationContext(),"검색할 이름을 입력해주세요.",Toast.LENGTH_LONG).show();
                }else{
                    namehex = stringToHex(name);
                    urlStr = "https://developer-lostark.game.onstove.com/characters/"+namehex+"/siblings";
                    requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    makeRequest();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new CharactersAdapter();
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        return rootView;
    }
    public static String stringToHex(String s) { // 검색을 위해 문자열을 16진수 값으로 변환
        byte[] getBytesFromString = s.getBytes(StandardCharsets.UTF_8);
        BigInteger bigInteger = new BigInteger(1, getBytesFromString);
        String convertedResult = String.format("%X", bigInteger);

        StringBuffer buf = new StringBuffer(convertedResult);
        StringBuffer buf2 = new StringBuffer();

        for(int i = 0; i < buf.length(); i++){
            if(i % 2 == 0){
                buf2.append("%");
                buf2.append(buf.substring(i,i+1));
            }else{
                buf2.append(buf.substring(i,i+1));
            }
        }
        return String.valueOf(buf2);
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
            }
        }) {

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
                if (jsonArray == null) {
                    Toast.makeText(getActivity().getApplicationContext(), "캐릭터가 없습니다.", Toast.LENGTH_LONG).show();
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        adapter.addItem(jsonObject);
                    }
                    adapter.notifyDataSetChanged();
                }
                } catch(JSONException e){
                    e.printStackTrace();
                }
        }
}
