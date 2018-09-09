package pasigqueueportal.com.pqpapp.ui.main.pastAppointment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;
import pasigqueueportal.com.pqpapp.R;
import pasigqueueportal.com.pqpapp.databinding.ActivityAppointmentPastBinding;
import pasigqueueportal.com.pqpapp.databinding.DialogAddFeedbackBinding;
import pasigqueueportal.com.pqpapp.databinding.DialogAppointmentDetailPastBinding;
import pasigqueueportal.com.pqpapp.databinding.DialogAppointmentRemindersBinding;
import pasigqueueportal.com.pqpapp.model.data.Appointment;
import pasigqueueportal.com.pqpapp.model.data.CurrentServing;
import pasigqueueportal.com.pqpapp.model.data.Token;
import pasigqueueportal.com.pqpapp.model.data.User;
import pasigqueueportal.com.pqpapp.model.data.Window;
import pasigqueueportal.com.pqpapp.ui.location.MapActivity;


public class PastAppointmentActivity
        extends MvpViewStateFragment<PastAppointmentView, PastAppointmentPresenter>
        implements SwipeRefreshLayout.OnRefreshListener, PastAppointmentView {

    private ProgressDialog progressDialog;
    private static final String TAG = PastAppointmentActivity.class.getSimpleName();
    private ActivityAppointmentPastBinding binding;
    private Realm realm;
    private User user;
    private Token token;
    private RealmResults<Appointment> appointmentlmResults;
    private String searchText;
    public String id;
    private PastAppointmentAdapter appointmentListAdapter;
    private Dialog dialogDetail,dialogDetailReminder,dialogfeedback;
    private DialogAppointmentDetailPastBinding detailBinding;
    private DialogAppointmentRemindersBinding reminderBinding;
    private DialogAddFeedbackBinding feedbackBinding;


    public PastAppointmentActivity(){

    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }




    @NonNull
    @Override
    public ViewState<PastAppointmentView> createViewState() {
        setRetainInstance(true);
        return new PastAppointmentViewState();
    }

    @Override
    public void onNewViewStateInstance() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_appointment_past, container, false);
        return binding.getRoot();
    }



    @Override
    public void onStart() {
        super.onStart();
        searchText = "";

        realm = Realm.getDefaultInstance();
        user = realm.where(User.class).findFirst();
        token = realm.where(Token.class).findFirst();
        if (user == null) {
            Log.d(TAG, "No User found");
            //  finish();
        }

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Appointment");

        presenter.onStart();


        appointmentListAdapter = new PastAppointmentAdapter(getActivity(), getMvpView(),realm);
        binding.recyclerView.setAdapter(appointmentListAdapter);


        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
       // binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
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
    public PastAppointmentPresenter createPresenter() {
        return new PastAppointmentPresenter();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main, menu);
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

    }
//    private void prepareList() {
//        if (appointmentlmResults.isLoaded() && appointmentlmResults.isValid()) {
//            if (searchText.isEmpty()) {
//
//
//                appointmentlmResults = realm.where(Appointment.class).findAllAsync();
//                appointmentListAdapter.setAppointmentResult(realm.copyToRealmOrUpdate(appointmentlmResults.where()
//                        .findAll()));//Sorted("eventDateFrom", Sort.ASCENDING)));
//                appointmentListAdapter.notifyDataSetChanged();
//
//            } else {
//
//                appointmentlmResults = realm.where(Appointment.class).findAllAsync();
//                appointmentListAdapter.setAppointmentResult(realm.copyToRealmOrUpdate(appointmentlmResults.where()
//                        .contains("emailAddress",searchText, Case.INSENSITIVE)
//                        .or()
//                        .contains("firstName",searchText, Case.INSENSITIVE)
//                        .or()
//                        .contains("lastName",searchText, Case.INSENSITIVE)
//                        .findAll()));//Sorted("eventDateFrom", Sort.ASCENDING)));
//                appointmentListAdapter.notifyDataSetChanged();
//            }
//        }
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

       // presenter.loadAppointmentList(token.getToken());

        setAppointmentList();


    }


    @Override
    public void onDestroy() {
        presenter.onStop();
//        appointmentlmResults.removeChangeListeners();
        realm.close();
        super.onDestroy();
    }


    @Override
    public void onRefresh() {

        token = realm.where(Token.class).findFirst();
            presenter.loadAppointmentList(token.getToken());
    }



    @Override
    public void startLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    @Override
    public void stopLoading() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        binding.swipeRefreshLayout.setRefreshing(false);
    }


//    public void loadData()
//    {
//        realm = Realm.getDefaultInstance();
//        User user = realm.where(User.class).findFirst();
//        appointmentlmResults = realm.where(PastAppointment.class).findAll();
//
//
//
//            if (appointmentlmResults.isLoaded() && appointmentlmResults.isValid()) {
//                getMvpView().setAppointmentList();
//
//            }else
//            {
//                presenter.loadAppointmentList(String.valueOf(user.getUserId()));
//
//            }
//
//
//    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void setAppointmentList(){



        Date c = Calendar.getInstance().getTime();
        appointmentlmResults = realm.where(Appointment.class).lessThan("appointmentTimestamp",c )
                .findAll();
        appointmentListAdapter.setAppointmentResult(appointmentlmResults);//Sorted("eventDateFrom", Sort.ASCENDING)));
        appointmentListAdapter.notifyDataSetChanged();





        if(appointmentListAdapter.getItemCount()==0)
        {

            binding.appointmentpasNoRecyclerview.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
        }else
        {
            binding.appointmentpasNoRecyclerview.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
        }
    }




    @Override
    public void stopRefresh() {
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showReturn(String message) {



        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        feedbackBinding.etFeedback.setText("");
        feedbackBinding.ratingBar.setRating(0);

    }






    @Override
    public void viewDetails(final Appointment appointment) {




        token = realm.where(Token.class).findFirst();
        dialogDetail = new Dialog(getContext(),R.style.RaffleDialogTheme);

        dialogDetail.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        detailBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.dialog_appointment_detail_past,
                null,
                false);



        try {
            appointment.getPaymentWindow().isLoaded();
        }catch (Exception e)
        {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    appointment.setPaymentWindow(appointment.getAssessmentWindow());
                }
            });

        }


        String channelSwitch;

        if(appointment.getAppointmentTransType().equals("2")) {
            channelSwitch = "window." +   (appointment.getPaymentWindow().getPaymentDesc()).replaceAll("[^0-9]", "");
            presenter.currentServing(token.getToken(),appointment.getPaymentWindow().getPaymentAssignId());
        }
        else {
            channelSwitch = "window." +   (appointment.getAssessmentWindow().getPaymentDesc()).replaceAll("[^0-9]", "");
            presenter.currentServing(token.getToken(),appointment.getAssessmentWindow().getPaymentAssignId());
        }


        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        Pusher pusher = new Pusher("a193f173e233c07d855d", options);



        Channel channel = pusher.subscribe(channelSwitch);

        Log.d(">>>>channelswitch>>",channelSwitch);


        channel.bind("App\\Events\\QueueUpdated", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                Log.d(">>>>",data);
              String text="";
                try {
                    JSONObject data2 = new JSONObject(data).getJSONObject("queue");
                    text = data2.getString("queue_no");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loadNowServing(text);
            }
        });



//        channel.bind("App\\Events\\CallAgain", new SubscriptionEventListener() {
//            @Override
//            public void onEvent(String channelName, String eventName, final String data) {
//                Log.d(">>>>",data);
//                String text="";
//                try {
//                 JSONObject data2 = new JSONObject(data).getJSONObject("window");
//                text = data2.getString("id");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//
//            }
//        });


        pusher.connect();



        detailBinding.setView(getMvpView());
        detailBinding.setAppointment(appointment);



        detailBinding.detailTaxType.setText("Tax Type: "+(presenter.getTaxType(appointment.getAppointmentTaxType())).getTaxTypeDesc());
        detailBinding.detailTransType.setText("Transaction Type: "+presenter.getTransactionType(Integer.parseInt(appointment.getAppointmentTransType())));
        detailBinding.detailBarangay.setText("Barangay: "+(presenter.getBarangay(appointment.getAppointmentBaranagay())).getBarangayName());



        detailBinding.appointmentDetailsStatus.setTextColor(appointmentListAdapter.getStatusColor(appointment.getAppointmentTransStatus()));

        detailBinding.feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            showfeedback(appointment.getAppointmentId(),appointment.getAssessmentWindow().getAssignedUser().getAssignId(),appointment.getPaymentWindow().getAssignedUser().getAssignId());
           }
        });



        detailBinding.reminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showReminder();
            }
        });

        detailBinding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDetail.dismiss();

            }
        });


        detailBinding.appointmentDetailsClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(detailBinding.appointmentDetailsHide.getVisibility()==View.VISIBLE)
                    detailBinding.appointmentDetailsHide.setVisibility(View.GONE);
                else
                    detailBinding.appointmentDetailsHide.setVisibility(View.VISIBLE);
            }
        });

        dialogDetail.setContentView(detailBinding.getRoot());
        dialogDetail.setCancelable(true);
        dialogDetail.show();


    }




    @Override
    public void loadNowServing(final String currentServing){


        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {

                detailBinding.currentServing.setText("Now Serving: "+currentServing);
            }
        });



    }




    public void showReminder()
    {



        dialogDetailReminder = new Dialog(getContext(),R.style.RaffleDialogTheme);

        dialogDetailReminder.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        reminderBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.dialog_appointment_reminders,
                null,
                false);





        reminderBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDetailReminder.dismiss();
            }
        });

        dialogDetailReminder.setContentView(reminderBinding.getRoot());
        dialogDetailReminder.setCancelable(true);
        dialogDetailReminder.show();

    }


    public void showfeedback(final String queueid, final int frontlinerid, final int cashierid)
    {



        dialogfeedback = new Dialog(getContext(),R.style.RaffleDialogTheme);

        dialogfeedback.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        feedbackBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.dialog_add_feedback,
                null,
                false);


        feedbackBinding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {


                String comment="";
                switch (Math.round(rating)) {
                    case 0:
                        feedbackBinding.ratingText.setVisibility(View.GONE);
                    break;
                    case 1:
                        comment = "Very Bad!";
                        feedbackBinding.ratingText.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        comment = "Not good!";
                        feedbackBinding.ratingText.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        comment = "Quite ok!";
                        feedbackBinding.ratingText.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        comment = "Very Good!";
                        feedbackBinding.ratingText.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        comment = "Excellent!";
                        feedbackBinding.ratingText.setVisibility(View.VISIBLE);
                        break;
                }


                feedbackBinding.ratingText.setText(comment);
            }
        });


        feedbackBinding.frontliner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(feedbackBinding.etFeedback.getText().toString().equals(""))
                    feedbackBinding.etFeedback.setText(feedbackBinding.ratingText.getText().toString());

                if(feedbackBinding.ratingBar.getRating()!=0)
                    presenter.sendFeedback(token.getToken(),String.valueOf(frontlinerid),queueid,String.valueOf(feedbackBinding.ratingBar.getRating()),feedbackBinding.etFeedback.getText().toString());
                else
                    showError("Please Select Rating");
            }
        });

        feedbackBinding.cashier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(feedbackBinding.etFeedback.getText().toString().equals(""))
                    feedbackBinding.etFeedback.setText(feedbackBinding.ratingText.getText().toString());

                if(feedbackBinding.ratingBar.getRating()!=0)
                    presenter.sendFeedback(token.getToken(),String.valueOf(cashierid),queueid,String.valueOf(feedbackBinding.ratingBar.getRating()),feedbackBinding.etFeedback.getText().toString());
                else
                    showError("Please Select Rating");
            }
        });

        feedbackBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogfeedback.dismiss();
            }
        });

        dialogfeedback.setContentView(feedbackBinding.getRoot());
        dialogfeedback.setCancelable(true);
        dialogfeedback.show();

    }


}
