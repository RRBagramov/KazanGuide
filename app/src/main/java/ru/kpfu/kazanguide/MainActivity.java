package ru.kpfu.kazanguide;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import ru.kpfu.kazanguide.fragments.AddGuideFragment;
import ru.kpfu.kazanguide.fragments.FavoriteGuidesFragment;
import ru.kpfu.kazanguide.fragments.GuidesListFragment;
import ru.kpfu.kazanguide.fragments.UserFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentTransaction fragmentTransaction;

    private AddGuideFragment addGuideFragment;
    private FavoriteGuidesFragment favoriteGuidesFragment;
    private GuidesListFragment guidesListFragment;
    private UserFragment userFragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentTransaction = getFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction.replace(R.id.content, guidesListFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_favorite:
                    fragmentTransaction.replace(R.id.content, favoriteGuidesFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_add_guide:
                    fragmentTransaction.replace(R.id.content, addGuideFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_user:
                    fragmentTransaction.replace(R.id.content, userFragment);
                    fragmentTransaction.commit();
                    return true;
            }

            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        guidesListFragment = new GuidesListFragment();
        addGuideFragment = new AddGuideFragment();
        favoriteGuidesFragment = new FavoriteGuidesFragment();
        userFragment = new UserFragment();

        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, guidesListFragment);
        fragmentTransaction.commit();
    }

}
