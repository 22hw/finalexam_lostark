package ac.leehyungwoo.finalexamlostark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


public class NewsFragment extends Fragment {
    String urlStr = "https://developer-lostark.game.onstove.com/news/events";
    static RequestQueue requestQueue;
    NewsAdapter adapter;
    RecyclerView recyclerView;
    final String accept = "application/json";
    String JWT= null;
    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_news, container, false);

        if (getArguments() != null)
        {
            JWT = getArguments().getString("JWT"); // MainActivity에서 받아온 값 넣기
        }
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        makeRequest();
        recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NewsAdapter();
        recyclerView.setAdapter(adapter);
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
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                adapter.addItem(jsonObject);
            }
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
