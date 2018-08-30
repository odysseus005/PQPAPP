package pasigqueueportal.com.pqpapp.ui.feedback;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.realm.Realm;

import io.realm.RealmResults;
import pasigqueueportal.com.pqpapp.R;
import pasigqueueportal.com.pqpapp.databinding.ActivityFeedbackBinding;
import pasigqueueportal.com.pqpapp.model.data.Feedback;
import pasigqueueportal.com.pqpapp.model.data.Token;
import pasigqueueportal.com.pqpapp.model.data.User;
import pl.aprilapps.easyphotopicker.EasyImage;


public class FeedbackListActivity
        extends MvpViewStateActivity<FeedbackListView, FeedbackListPresenter>
        implements SwipeRefreshLayout.OnRefreshListener, FeedbackListView {

    private static final String TAG = FeedbackListActivity.class.getSimpleName();
    private ActivityFeedbackBinding binding;
    private Realm realm;
    private User user;
    private Token token;
    private RealmResults<Feedback> feedbackRealmResults;
    private FeedbackListAdapter feedbackListAdapter;
    private String searchText;
    public String id;
    private ProgressDialog progressDialog;
    private static final int PERMISSION_READ_EXTERNAL_STORAGE = 124;
    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 125;
    private static final int PERMISSION_CAMERA = 126;
    private Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


        searchText = "";

        realm = Realm.getDefaultInstance();
        user = realm.where(User.class).findFirst();
        token =realm.where(Token.class).findFirst();


        binding = DataBindingUtil.setContentView(this, R.layout.activity_feedback);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Feedback");

        feedbackListAdapter = new FeedbackListAdapter(this, getMvpView());
        binding.recyclerView.setAdapter(feedbackListAdapter);





        //binding.swipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.swipe_refresh_layout_color_scheme));
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
      //   binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0)
                        ? 0 : recyclerView.getChildAt(0).getTop();
                binding.swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });








    }






    @NonNull
    @Override
    public FeedbackListPresenter createPresenter() {
        return new FeedbackListPresenter();
    }

    @NonNull
    @Override
    public ViewState<FeedbackListView> createViewState() {
        setRetainInstance(true);
        return new FeedbackViewState();
    }

    @Override
    public void onNewViewStateInstance() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);


        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchText = newText;
                //prepareList();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /*private void prepareList() {
        if (eventRealmResults.isLoaded() && eventRealmResults.isValid()) {
            if (searchText.isEmpty()) {
                getMvpView().setData(realm.copyFromRealm(eventRealmResults));
            } else {
                getMvpView().setData(realm.copyToRealmOrUpdate(eventRealmResults.where()
                        .contains("eventName", searchText, Case.INSENSITIVE)
                        .or()
                        .contains("tags", searchText, Case.INSENSITIVE)
                        .findAll()));
            }
        }
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
              this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        loadData();
    }


    @Override
    public void onDestroy() {
       // feedbackRealmResults.removeChangeListeners();
        realm.close();
        super.onDestroy();
    }


    @Override
    public void onRefresh() {

            presenter.loadFeedbackList(token.getToken());


    }


    public void loadData()
    {
        realm = Realm.getDefaultInstance();

        feedbackRealmResults = realm.where(Feedback.class).findAll();


           // if (feedbackRealmResults.isLoaded() && feedbackRealmResults.isValid()) {
           if(feedbackRealmResults.size()>0){
                getMvpView().setFeedbackList();


            }else
            {
                presenter.loadFeedbackList(token.getToken());

            }


    }

    @Override
    public void setFeedbackList(){



        feedbackRealmResults = realm.where(Feedback.class).findAllAsync();
        feedbackListAdapter.setEventResult(realm.copyToRealmOrUpdate(feedbackRealmResults.where()
                .findAll()));//Sorted("eventDateFrom", Sort.ASCENDING)));
        feedbackListAdapter.notifyDataSetChanged();



        if(feedbackListAdapter.getItemCount()==0)
        {
            binding.feedbackNoRecyclerview.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
        }
    }



    @Override
    public void stopRefresh() {
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }








    @Override
    public void startLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Updating...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    @Override
    public void stopLoading() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }





}
