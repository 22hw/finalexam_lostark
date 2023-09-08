package ac.leehyungwoo.finalexamlostark;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class DetailCharactersFragment extends Fragment {
    String urlStr = "https://developer-lostark.game.onstove.com/armories/characters/";
    String [] armories = {"/profiles","/equipment","/engravings","/cards","/gems"};
    ImageView charaterimg;
    ImageView head,shoulder,top,pants,gloves,weapon,necklace,earring1,earring2,ring1,ring2,bracelet,stone;
    ImageView jewel1,jewel2,jewel3,jewel4,jewel5,jewel6,jewel7,jewel8,jewel9,jewel10,jewel11;
    TextView engravings;
    ImageView card1,card2,card3,card4,card5,card6;
    final String accept = "application/json";
    String JWT, Hexname;
    Button button;
    static RequestQueue requestQueue;
    public DetailCharactersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_detail_characters, container, false);
        if (getArguments() != null)
        {
            JWT = getArguments().getString("JWT"); // MainActivity에서 받아온 값 넣기
            Hexname = getArguments().getString("Hexname"); // MainActivity에서 받아온 값 넣기
        }
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        charaterimg = rootView.findViewById(R.id.charaterimg);

        head = rootView.findViewById(R.id.head);shoulder = rootView.findViewById(R.id.shoulder);top = rootView.findViewById(R.id.top);
        pants = rootView.findViewById(R.id.pants);gloves = rootView.findViewById(R.id.gloves);weapon = rootView.findViewById(R.id.weapon);
        necklace = rootView.findViewById(R.id.necklace);earring1 = rootView.findViewById(R.id.earring1);earring2 = rootView.findViewById(R.id.earring2);
        ring1 = rootView.findViewById(R.id.ring1);ring2 = rootView.findViewById(R.id.ring2);bracelet = rootView.findViewById(R.id.bracelet);
        stone = rootView.findViewById(R.id.stone);

        jewel1 = rootView.findViewById(R.id.jewel1);jewel2 = rootView.findViewById(R.id.jewel2);jewel3 = rootView.findViewById(R.id.jewel3);
        jewel4 = rootView.findViewById(R.id.jewel4);jewel5 = rootView.findViewById(R.id.jewel5);jewel6 = rootView.findViewById(R.id.jewel6);
        jewel7 = rootView.findViewById(R.id.jewel7);jewel8 = rootView.findViewById(R.id.jewel8);jewel9 = rootView.findViewById(R.id.jewel9);
        jewel10 = rootView.findViewById(R.id.jewel10);jewel11 = rootView.findViewById(R.id.jewel11);

        engravings = rootView.findViewById(R.id.engravings);

        card1 = rootView.findViewById(R.id.card1);card2 = rootView.findViewById(R.id.card2);card3 = rootView.findViewById(R.id.card3);
        card4 = rootView.findViewById(R.id.card4);card5 = rootView.findViewById(R.id.card5);card6 = rootView.findViewById(R.id.card6);


        profilesmakeRequest(urlStr + Hexname + armories[0]);
        equimentmakeRequest(urlStr + Hexname + armories[1]);
        gackinmakeRequest(urlStr + Hexname + armories[2]);
        cardmakeRequest(urlStr + Hexname + armories[3]);
        julymakeRequest(urlStr + Hexname + armories[4]);

        button = rootView.findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                activity.navigationsetvisibility(true);
                activity.onFragmentChanged(2);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false);
        ;
        return rootView;
    }
    ///////////////////////// 프로파일 정보를 가져온다
    private void profilesmakeRequest(String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        profilesprocessResponse(response);
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
    private void profilesprocessResponse(String response) { //Json 데이터 파싱
        try {
            JSONObject jsonObject = new JSONObject(response);
            new DownloadFilesTask(charaterimg).execute(jsonObject.getString("CharacterImage"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    ////////////////////////////// 장비 정보를 가져온다
    private void equimentmakeRequest(String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        equimentprocessResponse(response);
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
    private void equimentprocessResponse(String response) { //Json 데이터 파싱
        try {
            int check = 0;
            JSONArray jsonArray = new JSONArray(response);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String itemname = jsonObject.getString("Type");
                switch (itemname){
                    case "무기" :
                        new DownloadFilesTask(weapon).execute(jsonObject.getString("Icon"));
                        switch (jsonObject.getString("Grade")){
                            case "희귀" :
                                weapon.setBackgroundColor(Color.parseColor("#113652"));
                            case "영웅" :
                                weapon.setBackgroundColor(Color.parseColor("#3B104C"));
                            case "전설" :
                                weapon.setBackgroundColor(Color.parseColor("#905704"));
                            case "유물" :
                                weapon.setBackgroundColor(Color.parseColor("#973D07"));
                            case "고대" :
                                weapon.setBackgroundColor(Color.parseColor("#CAB88C"));
                            case "에스더" :
                                weapon.setBackgroundColor(Color.parseColor("#2A9895"));
                        }
                        break;
                    case "투구" :
                        new DownloadFilesTask(head).execute(jsonObject.getString("Icon"));
                        switch (jsonObject.getString("Grade")){
                            case "희귀" :
                                head.setBackgroundColor(Color.parseColor("#113652"));
                            case "영웅" :
                                head.setBackgroundColor(Color.parseColor("#3B104C"));
                            case "전설" :
                                head.setBackgroundColor(Color.parseColor("#905704"));
                            case "유물" :
                                head.setBackgroundColor(Color.parseColor("#973D07"));
                            case "고대" :
                                head.setBackgroundColor(Color.parseColor("#CAB88C"));
                        }
                        break;
                    case "상의" :
                        new DownloadFilesTask(top).execute(jsonObject.getString("Icon"));
                        switch (jsonObject.getString("Grade")){
                            case "희귀" :
                                top.setBackgroundColor(Color.parseColor("#113652"));
                            case "영웅" :
                                top.setBackgroundColor(Color.parseColor("#3B104C"));
                            case "전설" :
                                top.setBackgroundColor(Color.parseColor("#905704"));
                            case "유물" :
                                top.setBackgroundColor(Color.parseColor("#973D07"));
                            case "고대" :
                                top.setBackgroundColor(Color.parseColor("#CAB88C"));
                        }
                        break;
                    case "하의" :
                        new DownloadFilesTask(pants).execute(jsonObject.getString("Icon"));
                        switch (jsonObject.getString("Grade")){
                            case "희귀" :
                                pants.setBackgroundColor(Color.parseColor("#113652"));
                            case "영웅" :
                                pants.setBackgroundColor(Color.parseColor("#3B104C"));
                            case "전설" :
                                pants.setBackgroundColor(Color.parseColor("#905704"));
                            case "유물" :
                                pants.setBackgroundColor(Color.parseColor("#973D07"));
                            case "고대" :
                                pants.setBackgroundColor(Color.parseColor("#CAB88C"));
                        }
                        break;
                    case "장갑" :
                        new DownloadFilesTask(gloves).execute(jsonObject.getString("Icon"));
                        switch (jsonObject.getString("Grade")){
                            case "희귀" :
                                gloves.setBackgroundColor(Color.parseColor("#113652"));
                            case "영웅" :
                                gloves.setBackgroundColor(Color.parseColor("#3B104C"));
                            case "전설" :
                                gloves.setBackgroundColor(Color.parseColor("#905704"));
                            case "유물" :
                                gloves.setBackgroundColor(Color.parseColor("#973D07"));
                            case "고대" :
                                gloves.setBackgroundColor(Color.parseColor("#CAB88C"));
                        }
                        break;
                    case "어깨" :
                        new DownloadFilesTask(shoulder).execute(jsonObject.getString("Icon"));
                        switch (jsonObject.getString("Grade")){
                            case "희귀" :
                                shoulder.setBackgroundColor(Color.parseColor("#113652"));
                            case "영웅" :
                                shoulder.setBackgroundColor(Color.parseColor("#3B104C"));
                            case "전설" :
                                shoulder.setBackgroundColor(Color.parseColor("#905704"));
                            case "유물" :
                                shoulder.setBackgroundColor(Color.parseColor("#973D07"));
                            case "고대" :
                                shoulder.setBackgroundColor(Color.parseColor("#CAB88C"));
                        }
                        break;
                    case "목걸이" :
                        new DownloadFilesTask(necklace).execute(jsonObject.getString("Icon"));
                        switch (jsonObject.getString("Grade")){
                            case "희귀" :
                                necklace.setBackgroundColor(Color.parseColor("#113652"));
                            case "영웅" :
                                necklace.setBackgroundColor(Color.parseColor("#3B104C"));
                            case "전설" :
                                necklace.setBackgroundColor(Color.parseColor("#905704"));
                            case "유물" :
                                necklace.setBackgroundColor(Color.parseColor("#973D07"));
                            case "고대" :
                                necklace.setBackgroundColor(Color.parseColor("#CAB88C"));
                        }
                        break;
                    case "귀걸이" :
                        if(check == 0){
                            new DownloadFilesTask(earring1).execute(jsonObject.getString("Icon"));
                            switch (jsonObject.getString("Grade")){
                                case "희귀" :
                                    earring1.setBackgroundColor(Color.parseColor("#113652"));
                                case "영웅" :
                                    earring1.setBackgroundColor(Color.parseColor("#3B104C"));
                                case "전설" :
                                    earring1.setBackgroundColor(Color.parseColor("#905704"));
                                case "유물" :
                                    earring1.setBackgroundColor(Color.parseColor("#973D07"));
                                case "고대" :
                                    earring1.setBackgroundColor(Color.parseColor("#CAB88C"));
                            }
                            check = 1;
                        }else {
                            new DownloadFilesTask(earring2).execute(jsonObject.getString("Icon"));
                            switch (jsonObject.getString("Grade")){
                                case "희귀" :
                                    earring2.setBackgroundColor(Color.parseColor("#113652"));
                                case "영웅" :
                                    earring2.setBackgroundColor(Color.parseColor("#3B104C"));
                                case "전설" :
                                    earring2.setBackgroundColor(Color.parseColor("#905704"));
                                case "유물" :
                                    earring2.setBackgroundColor(Color.parseColor("#973D07"));
                                case "고대" :
                                    earring2.setBackgroundColor(Color.parseColor("#CAB88C"));
                            }
                            check = 0;
                        }
                        break;
                    case "반지" :
                        if (check == 0){
                            new DownloadFilesTask(ring1).execute(jsonObject.getString("Icon"));
                            switch (jsonObject.getString("Grade")){
                                case "희귀" :
                                    ring1.setBackgroundColor(Color.parseColor("#113652"));
                                case "영웅" :
                                    ring1.setBackgroundColor(Color.parseColor("#3B104C"));
                                case "전설" :
                                    ring1.setBackgroundColor(Color.parseColor("#905704"));
                                case "유물" :
                                    ring1.setBackgroundColor(Color.parseColor("#973D07"));
                                case "고대" :
                                    ring1.setBackgroundColor(Color.parseColor("#CAB88C"));
                            }
                            check = 1;
                        }else {
                            new DownloadFilesTask(ring2).execute(jsonObject.getString("Icon"));
                            switch (jsonObject.getString("Grade")){
                                case "희귀" :
                                    ring2.setBackgroundColor(Color.parseColor("#113652"));
                                case "영웅" :
                                    ring2.setBackgroundColor(Color.parseColor("#3B104C"));
                                case "전설" :
                                    ring2.setBackgroundColor(Color.parseColor("#905704"));
                                case "유물" :
                                    ring2.setBackgroundColor(Color.parseColor("#973D07"));
                                case "고대" :
                                    ring2.setBackgroundColor(Color.parseColor("#CAB88C"));
                            }
                            check = 0 ;
                        }
                        break;
                    case "어빌리티 스톤" :
                        new DownloadFilesTask(stone).execute(jsonObject.getString("Icon"));
                        switch (jsonObject.getString("Grade")){
                            case "희귀" :
                                stone.setBackgroundColor(Color.parseColor("#113652"));
                            case "영웅" :
                                stone.setBackgroundColor(Color.parseColor("#3B104C"));
                            case "전설" :
                                stone.setBackgroundColor(Color.parseColor("#905704"));
                            case "유물" :
                                stone.setBackgroundColor(Color.parseColor("#973D07"));
                            case "고대" :
                                stone.setBackgroundColor(Color.parseColor("#CAB88C"));
                        }
                        break;
                    case "팔찌" :
                        new DownloadFilesTask(bracelet).execute(jsonObject.getString("Icon"));
                        switch (jsonObject.getString("Grade")){
                            case "희귀" :
                                bracelet.setBackgroundColor(Color.parseColor("#113652"));
                            case "영웅" :
                                bracelet.setBackgroundColor(Color.parseColor("#3B104C"));
                            case "전설" :
                                bracelet.setBackgroundColor(Color.parseColor("#905704"));
                            case "유물" :
                                bracelet.setBackgroundColor(Color.parseColor("#973D07"));
                            case "고대" :
                                bracelet.setBackgroundColor(Color.parseColor("#CAB88C"));
                        }
                        break;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /////////////////////////////// 각인 정보를 가져온다
    private void gackinmakeRequest(String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        gackinprocessResponse(response);
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
    private void gackinprocessResponse(String response) { //Json 데이터 파싱
        try {
            JSONObject jObject = new JSONObject(response);
            JSONArray jsonArray = jObject.getJSONArray("Effects");
            StringBuilder str = new StringBuilder();
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                str.append("\t"+jsonObject.getString("Name")+"\n");
                str.append(jsonObject.getString("Description")+"\n\n");
            }
            engravings.setText(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    ///////////////////////////////////// 카드 정보를 가져온다
    private void cardmakeRequest(String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        cardprocessResponse(response);
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
    private void cardprocessResponse(String response) { //Json 데이터 파싱
        try {
            JSONObject jObject = new JSONObject(response);
            JSONArray jsonArray = jObject.getJSONArray("Cards");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                switch (i){
                    case 0 :
                        new DownloadFilesTask(card1).execute(jsonObject.getString("Icon"));
                    case 1 :
                        new DownloadFilesTask(card2).execute(jsonObject.getString("Icon"));
                    case 2 :
                        new DownloadFilesTask(card3).execute(jsonObject.getString("Icon"));
                    case 3 :
                        new DownloadFilesTask(card4).execute(jsonObject.getString("Icon"));
                    case 4 :
                        new DownloadFilesTask(card5).execute(jsonObject.getString("Icon"));
                    case 5 :
                        new DownloadFilesTask(card6).execute(jsonObject.getString("Icon"));

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //////////////////////////////////// 보석 정보를 가져온다
    private void julymakeRequest(String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        julyprocessResponse(response);
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
    private void julyprocessResponse(String response) { //Json 데이터 파싱
        try {
            JSONObject jObject = new JSONObject(response);
            JSONArray jsonArray = jObject.getJSONArray("Gems");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                switch (i){
                    case 0 :
                        new DownloadFilesTask(jewel1).execute(jsonObject.getString("Icon"));
                    case 1 :
                        new DownloadFilesTask(jewel2).execute(jsonObject.getString("Icon"));
                    case 2 :
                        new DownloadFilesTask(jewel3).execute(jsonObject.getString("Icon"));
                    case 3 :
                        new DownloadFilesTask(jewel4).execute(jsonObject.getString("Icon"));
                    case 4 :
                        new DownloadFilesTask(jewel5).execute(jsonObject.getString("Icon"));
                    case 5 :
                        new DownloadFilesTask(jewel6).execute(jsonObject.getString("Icon"));
                    case 6 :
                        new DownloadFilesTask(jewel7).execute(jsonObject.getString("Icon"));
                    case 7 :
                        new DownloadFilesTask(jewel8).execute(jsonObject.getString("Icon"));
                    case 8 :
                        new DownloadFilesTask(jewel9).execute(jsonObject.getString("Icon"));
                    case 9 :
                        new DownloadFilesTask(jewel10).execute(jsonObject.getString("Icon"));
                    case 10 :
                        new DownloadFilesTask(jewel11).execute(jsonObject.getString("Icon"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //////////////////////////////////// URL 이미지를 가져와 설정해준다.
    private class DownloadFilesTask extends AsyncTask<String, Void, Bitmap> {
        ImageView img;
        public DownloadFilesTask(ImageView imageView) {
            img = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bmp = null;
            try {
                String img_url = strings[0]; //url of the image
                URL url = new URL(img_url);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // doInBackground 에서 받아온 total 값 사용 장소
            img.setImageBitmap(result);
        }

    }
}
