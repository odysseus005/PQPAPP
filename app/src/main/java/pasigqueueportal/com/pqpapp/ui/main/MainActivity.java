package pasigqueueportal.com.pqpapp.ui.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import io.realm.Realm;
import pasigqueueportal.com.pqpapp.R;
import pasigqueueportal.com.pqpapp.app.Endpoints;
import pasigqueueportal.com.pqpapp.databinding.ActivityMainBinding;
import pasigqueueportal.com.pqpapp.model.data.User;
import pasigqueueportal.com.pqpapp.ui.feedback.FeedbackListActivity;
import pasigqueueportal.com.pqpapp.ui.location.MapActivity;
import pasigqueueportal.com.pqpapp.ui.login.LoginActivity;
import pasigqueueportal.com.pqpapp.ui.profile.ProfileActivity;
import pasigqueueportal.com.pqpapp.util.CircleTransform;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Realm realm;
    private User user;
    private ActivityMainBinding binding;
    private TextView txtName;
    private TextView txtEmail;
    private ImageView imgProfile;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        user = realm.where(User.class).findFirst();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.appBarMain.toolbar);
        //getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle("PQPP App");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout,
                binding.appBarMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);
        Menu nav_Menu =  binding.navView.getMenu();

        MainTabAdapter mAdapter = new MainTabAdapter(getSupportFragmentManager());
        binding.appBarMain.viewPager.setAdapter(mAdapter);
        binding.appBarMain.tabs.setupWithViewPager(binding.appBarMain.viewPager, true);

        //binding.appBarMain.tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        txtName = (TextView) binding.navView.getHeaderView(0).findViewById(R.id.txt_name);
        txtEmail = (TextView) binding.navView.getHeaderView(0).findViewById(R.id.txt_email);
        imgProfile = (ImageView) binding.navView.getHeaderView(0).findViewById(R.id.imageView);




        if(user != null)
                    updateUI();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(user != null)
            updateUI();
    }



    private void updateUI() {

            txtName.setText(user.getFullName());
        txtEmail.setText(user.getEmail());
        String imageURL = "";

        if (user.getImage() != null && !user.getImage().isEmpty()) {
            imageURL = Endpoints.URL_IMAGE + (user.getImage());
        }

        Log.d(">>>>>>>>>>", "imageUrl: " + imageURL);
        Glide.with(this)
                .load(imageURL)
                .transform(new CircleTransform(this))
                .error(R.drawable.placeholder_profile)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
        }else if (id == R.id.nav_feedback) {
            startActivity(new Intent(this, FeedbackListActivity.class));
        }
        else if (id == R.id.nav_location) {
           // startActivity(new Intent(this, MapActivity.class));
            Intent i = new Intent(this, MapActivity.class);
            i.putExtra("time", "");
            startActivity(i);
        }
        else if (id == R.id.nav_logout) {
            logOut(user);
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logOut(User user) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                realm.close();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
                realm.close();
                Toast.makeText(MainActivity.this, "Realm Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
