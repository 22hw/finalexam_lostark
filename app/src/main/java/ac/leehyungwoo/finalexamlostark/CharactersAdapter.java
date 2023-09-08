package ac.leehyungwoo.finalexamlostark;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ViewHolder> {
    ArrayList<JSONObject> items = new ArrayList<>();

    @NonNull
    @Override
    public CharactersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.characterscardview, parent, false);
        return new CharactersAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CharactersAdapter.ViewHolder holder, int position) {
        JSONObject item = items.get(position);
        try {
            holder.setItem(item);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void addItem(JSONObject item){
        items.add(item);
    }
    public void setItems(ArrayList<JSONObject> items){
        this.items = items;
    }
    public JSONObject getItem(int position) {
        return items.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView servername, level, classname, itemlevel, name;
        ImageView classimage;
        CardView cardView;
        String Hexname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            classimage = itemView.findViewById(R.id.classimage);
            servername = itemView.findViewById(R.id.servername);
            level = itemView.findViewById(R.id.level);
            classname = itemView.findViewById(R.id.classname);
            itemlevel = itemView.findViewById(R.id.itemlevel);
            name = itemView.findViewById(R.id.name);
            cardView = itemView.findViewById(R.id.cardview);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Hexname = stringToHex(String.valueOf(name.getText()));
                    MainActivity activity = (MainActivity) itemView.getContext();
                    activity.setHexname(Hexname);
                    activity.onFragmentChanged(3);
                }
            });

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

        @SuppressLint("UseCompatLoadingForDrawables")
        public void setItem(JSONObject characters) throws JSONException {
            servername.setText(characters.getString("ServerName"));
            level.setText(characters.getString("CharacterLevel"));
            classname.setText(characters.getString("CharacterClassName"));
            itemlevel.setText(characters.getString("ItemAvgLevel"));
            name.setText(characters.getString("CharacterName"));
            String job = String.valueOf(classname.getText());
            switch (job){
                case "워로드" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.warlord_s));
                    break;
                case "버서커" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.berserker_s));
                    break;
                case "디스트로이어" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.destroyer_s));
                    break;
                case "홀리나이트" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.holyknight_s));
                    break;
                case "스트라이커" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.striker_s));
                    break;
                case "배틀마스터" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.battlemaster_s));
                    break;
                case "인파이터" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.infighter_s));
                    break;
                case "기공사" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.soulmaster_s));
                    break;
                case "창술사" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.lancemaster_s));
                    break;
                case "데빌헌터" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.devilhunter_s));
                    break;
                case "블래스터" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.blaster_s));
                    break;
                case "호크아이" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.hawkeye_s));
                    break;
                case "스카우터" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.scouter_s));
                    break;
                case "건슬링어" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.gunslinger_s));
                    break;
                case "바드" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.bard_s));
                    break;
                case "서머너" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.summoner_s));
                    break;
                case "아르카나" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.arcana_s));
                    break;
                case "소서리스" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.sorceress_s));
                    break;
                case "블레이드" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.blade_s));
                    break;
                case "데모닉" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.demonic_s));
                    break;
                case "리퍼" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.reaper_s));
                    break;
                case "도화가" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.artist_s));
                    break;
                case "기상술사" :
                    classimage.setImageDrawable(itemView.getContext().getDrawable(R.drawable.weather_artist_s));
                    break;
            }
        }

    }
}