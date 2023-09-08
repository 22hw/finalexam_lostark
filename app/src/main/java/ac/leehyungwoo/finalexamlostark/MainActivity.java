package ac.leehyungwoo.finalexamlostark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    NewsFragment newsFragment;
    LoginFragment loginFragment;
    CharactersFragment charactersFragment;
    DetailCharactersFragment detailCharactersFragment;
    FragmentManager fragmentManager;
    NavigationBarView navigationBarView;
    boolean Visibility;
    String JWT = null;
    String Hexname = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        newsFragment = new NewsFragment();
        loginFragment = new LoginFragment();
        charactersFragment = new CharactersFragment();
        detailCharactersFragment = new DetailCharactersFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,loginFragment);
        fragmentTransaction.commit();
        navigationBarView = findViewById(R.id.bottom_navigationview);
        if(JWT == null){
            navigationBarView.setVisibility(View.GONE);
        }
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.news:
                        onFragmentChanged(1);
                        return true;
                    case R.id.namesearch:
                        onFragmentChanged(2);
                        return true;
                }
                return false;
            }
        });
    }
    public void setJWT(String jwt){ // 로그인 프레그먼트에서 JWT를 받아 저장
        navigationsetvisibility(true);
        JWT = jwt;
        Bundle bundle = new Bundle(); // 번들을 통해 값 전달
        bundle.putString("JWT",JWT);//번들에 넘길 값 저장
        newsFragment.setArguments(bundle);
        charactersFragment.setArguments(bundle);
    }
    public void setHexname(String hexname){ // 캐릭터검색 어뎁터에서 hexname 받아 저장
        Hexname = hexname;
        Bundle bundle = new Bundle(); // 번들을 통해 값 전달
        bundle.putString("JWT",JWT);//번들에 넘길 값 저장
        bundle.putString("Hexname",Hexname);//번들에 넘길 값 저장
        detailCharactersFragment.setArguments(bundle);
    }
    public void navigationsetvisibility(boolean set){
        if(set){
            navigationBarView.setVisibility(View.VISIBLE);
        }else
        {
            navigationBarView.setVisibility(View.GONE);
        }
    }

    public void onFragmentChanged(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (index == 0) {
            fragmentTransaction.replace(R.id.container, loginFragment);
            fragmentTransaction.commit();
        } else if (index == 1) {
            fragmentTransaction.replace(R.id.container, newsFragment);
            fragmentTransaction.commit();
        } else if (index == 2) {
            fragmentTransaction.replace(R.id.container, charactersFragment);
            fragmentTransaction.commit();
        }else if (index == 3) {
            navigationsetvisibility(false);
            fragmentTransaction.replace(R.id.container, detailCharactersFragment);
            fragmentTransaction.commit();
        }
    }
}