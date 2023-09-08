package ac.leehyungwoo.finalexamlostark;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    ArrayList<JSONObject> items = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.cardview, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
        TextView textView, textView2;
        ImageView imageView;
        String Link;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(Link));
                    view.getContext().startActivity(intent);
                }
            });
        }

        public void setItem(JSONObject news) throws JSONException {
            new DownloadFilesTask().execute(news.getString("Thumbnail"));
            textView.setText(news.getString("Title"));
            if(news.getString("RewardDate") != "null"){
                textView2.setText("이벤트 기간 : "+news.getString("StartDate")+" ~ "+news.getString("EndDate")+"\n보상 수령 기간 : "+news.getString("RewardDate"));
            }else{
                textView2.setText("이벤트 기간 : "+news.getString("StartDate")+" ~ "+news.getString("EndDate"));
            }
            Link = news.getString("Link");
        }

        private class DownloadFilesTask extends AsyncTask<String, Void, Bitmap> {
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
                imageView.setImageBitmap(result);
            }

        }
    }
}