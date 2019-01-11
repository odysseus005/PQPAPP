package pasigqueueportal.com.pqpapp.ui.main.currentAppointment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.InstanceIdResult;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import pasigqueueportal.com.pqpapp.R;
import pasigqueueportal.com.pqpapp.databinding.ActivityAppointmentCurrentBinding;
import pasigqueueportal.com.pqpapp.databinding.DialogAddAppointmentBinding;
import pasigqueueportal.com.pqpapp.databinding.DialogAddFeedbackBinding;
import pasigqueueportal.com.pqpapp.databinding.DialogAppointmentDetailBinding;
import pasigqueueportal.com.pqpapp.databinding.DialogAppointmentRemindersBinding;
import pasigqueueportal.com.pqpapp.databinding.DialogDateslotBinding;
import pasigqueueportal.com.pqpapp.model.data.Appointment;
import pasigqueueportal.com.pqpapp.model.data.Barangay;
import pasigqueueportal.com.pqpapp.model.data.CurrentServing;
import pasigqueueportal.com.pqpapp.model.data.Slot;
import pasigqueueportal.com.pqpapp.model.data.TaxType;
import pasigqueueportal.com.pqpapp.model.data.Token;
import pasigqueueportal.com.pqpapp.model.data.User;
import pasigqueueportal.com.pqpapp.ui.location.MapActivity;
import pasigqueueportal.com.pqpapp.ui.login.LoginActivity;
import pasigqueueportal.com.pqpapp.ui.main.MainActivity;
import pasigqueueportal.com.pqpapp.util.FunctionUtils;
import pasigqueueportal.com.pqpapp.util.YearMonthPickerDialog;


public class AppointmentActivity
        extends MvpViewStateFragment<AppointmentView, AppointmentPresenter>
        implements SwipeRefreshLayout.OnRefreshListener, AppointmentView,DatePickerDialog.OnDateSetListener, OnDateSelectedListener {


    private ProgressDialog progressDialog;
    private static final String TAG = AppointmentActivity.class.getSimpleName();
    private ActivityAppointmentCurrentBinding binding;
    private Realm realm;
    private User user;
    private RealmResults<Appointment> appointmentlmResults;
    private RealmResults<Barangay> barangayRealmResults;
    private RealmResults<Slot> slotRealmResults;
    private RealmResults<TaxType> taxTypeRealmResults;
    private String searchText;
    public String id,appointid,fcmID="";
    private AppointmentAdapter appointmentListAdapter;
    private AssesTypeAdapter assesTypeAdapter;
    private UnpaidAdapter unpaidAdapter;
    private DialogAddAppointmentBinding dialogBinding;
    private DialogAppointmentDetailBinding detailBinding;
    private DialogAppointmentRemindersBinding reminderBinding;
    private DialogDateslotBinding dateslotBinding;
    private Dialog dialog,dialogDetail,dialogDetailReminder,dialogfeedback,dialogDateSlot;
    private Token token;
    private boolean reserveChecker=false;
    private Pusher pusher;
    private  String selectedBaranagay="",selectedTaxtype="",selectedDate="";
    private int selectYear=0,selectMonth=0,totalslot=0;

    public AppointmentActivity(){

    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


    }




    @NonNull
    @Override
    public ViewState<AppointmentView> createViewState() {
        setRetainInstance(true);
        return new AppointmentViewState();
    }

    @Override
    public void onNewViewStateInstance() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_appointment_current, container, false);
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

        presenter.onStart(token.getTokenRefresh());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Appointment");


        appointmentListAdapter = new AppointmentAdapter(getActivity(), getMvpView(),realm);
        assesTypeAdapter = new AssesTypeAdapter(getActivity(),getMvpView());
        unpaidAdapter = new UnpaidAdapter(getActivity(),getMvpView(),realm);
        binding.recyclerView.setAdapter(appointmentListAdapter);
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0)
                        ? 0 : recyclerView.getChildAt(0).getTop();
                binding.swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });


        binding.attendeeScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

               // presenter.loadNotif(token.getToken());
                dialogSetAppointment();


            }
        });

        dialog = new Dialog(getContext());

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        dialogBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.dialog_add_appointment,
                null,
                false);


        binding.txtDateToday.setText(FunctionUtils.convertDateToString("MMMM dd, yyyy", Calendar.getInstance()));

        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        pusher = new Pusher("a193f173e233c07d855d", options);




    }

    @NonNull
    @Override
    public AppointmentPresenter createPresenter() {
        return new AppointmentPresenter();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main_hide, menu);
//        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        search.setVisibility(View.GONE);
//        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                searchText = newText;
//                prepareList();
//                return true;
//            }
//        });

    }

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


    }


    @Override
    public void onDestroy() {
        presenter.onStop();
        realm.close();
        super.onDestroy();
    }


    @Override
    public void onRefresh() {

        presenter.loadBarangay(token.getToken());
        presenter.loadTaxType(token.getToken());
            presenter.loadAppointmentList(token.getToken());
    }







    @Override
    public  void loadBarangay()
    {


        List<String> barangay = new ArrayList<>();
        barangayRealmResults = realm.where(Barangay.class).findAll();

        if(!barangayRealmResults.isEmpty()) {
            for (Barangay value : barangayRealmResults) {
                barangay.add(value.getBarangayName());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_custom_item, barangay);
            dialogBinding.spBarangay.setAdapter(arrayAdapter);

            selectedBaranagay=String.valueOf(barangayRealmResults.get(0).getBarangayId());

            dialogBinding.spBarangay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    selectedBaranagay=String.valueOf(barangayRealmResults.get(position).getBarangayId());

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        else
            showError("No Available Barangay");

    }


    @Override
    public void loadTaxType()
    {

        List<String> tax = new ArrayList<>();
        taxTypeRealmResults = realm.where(TaxType.class).findAll();


        if(!taxTypeRealmResults.isEmpty()) {
            for (TaxType value : taxTypeRealmResults) {
                tax.add(value.getTaxTypeDesc());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_custom_item, tax);
            dialogBinding.spTaxType.setAdapter(arrayAdapter);

            selectedTaxtype = taxTypeRealmResults.get(0).getTaxTypeId();

            dialogBinding.spTaxType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    selectedTaxtype=String.valueOf(taxTypeRealmResults.get(position).getTaxTypeId());

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        else
            showError("No Available Tax Type");

    }




    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    private void prepareList() {
        if (appointmentlmResults.isLoaded() && appointmentlmResults.isValid()) {
            Date c = Calendar.getInstance().getTime();
            if (searchText.isEmpty()) {


                appointmentlmResults = realm.where(Appointment.class).findAllAsync();
                appointmentListAdapter.setAppointmentResult(realm.copyToRealmOrUpdate(appointmentlmResults.where()
                        .greaterThan("appointmentTimestamp",c )
                        .findAll()));//Sorted("eventDateFrom", Sort.ASCENDING)));
                appointmentListAdapter.notifyDataSetChanged();

            } else {

                appointmentlmResults = realm.where(Appointment.class).findAllAsync();
                appointmentListAdapter.setAppointmentResult(realm.copyToRealmOrUpdate(appointmentlmResults.where()
                        .greaterThan("appointmentTimestamp",c )
                        .beginGroup()
                        .contains("appointmentTransStatus",searchText, Case.INSENSITIVE)
                        .or()
                        .contains("appointmentTransDate",searchText, Case.INSENSITIVE)
                        .or()
                        .contains("appointmentTransTime",searchText, Case.INSENSITIVE)
                        .or()
                        .contains("appointmentTransQueue",searchText, Case.INSENSITIVE)
                        .endGroup()
                        .findAll()));//Sorted("eventDateFrom", Sort.ASCENDING)));
                appointmentListAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void setAppointmentList(){

        Date c = Calendar.getInstance().getTime();
        appointmentlmResults = realm.where(Appointment.class).findAll();
        appointmentListAdapter.setAppointmentResult(realm.copyToRealmOrUpdate(appointmentlmResults.where()
               .greaterThan("appointmentTimestamp",c )
               .findAll()));//Sorted("eventDateFrom", Sort.ASCENDING)));

        appointmentListAdapter.notifyDataSetChanged();



        if(appointmentListAdapter.getItemCount()==0)
        {

            binding.appointmentcurrentNoRecyclerview.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
        }else
        {
            binding.appointmentcurrentNoRecyclerview.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
        }
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


    @Override
    public void onPause() {
        super.onPause();
        if(dialogDetail!=null)
        dialogDetail.dismiss();
        reserveChecker=false;

    }


    @Override
    public  void onFinishTokenRef()
    {
        token = realm.where(Token.class).findFirst();
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(">>>>", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        // String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(">>>>", token);
                        fcmID=token;
                        updateFCM();

                        //   Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


        presenter.loadBarangay(token.getToken());
        presenter.loadTaxType(token.getToken());
        presenter.loadAppointmentList(token.getToken());
    }




    public void updateFCM()
    {
        presenter.sendFCMId(token.getToken(),fcmID);
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
    public void showReturn(String message,Appointment appointment) {

        presenter.loadAppointmentList(token.getToken());
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
//        selectedBaranagay="";selectedTaxtype="";selectedDate="";
        assesTypeAdapter.reset();
        dialog.dismiss();
        dialogDateSlot.dismiss();
        hideAssessTypeAdapter();
        reserveChecker=true;
       Log.d(">>>",appointment.toString());
        showAppointmentDetails(appointment);

    }

    @Override
    public void showReturnCancel(String message) {

        presenter.loadAppointmentList(token.getToken());
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
      dialogDetail.dismiss();

    }






    @Override
    public void showAppointmentDetails(final Appointment appointment) {



        token = realm.where(Token.class).findFirst();
        dialogDetail = new Dialog(getContext(),R.style.RaffleDialogTheme);
//        else
//            dialogDetail = new Dialog(getContext(),android.R.style.Theme_NoTitleBar_Fullscreen);

        dialogDetail.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        detailBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.dialog_appointment_detail,
                null,
                false);


        try {
            if(!appointment.getPaymentWindow().isLoaded())
            appointment.setPaymentWindow(appointment.getAssessmentWindow());
        }catch (Exception e)
        {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    appointment.setPaymentWindow(appointment.getAssessmentWindow());
                }
            });

        }

        final String channelSwitch;

        if(appointment.getAppointmentTransType().equals("2")) {
            channelSwitch = "window." +   (appointment.getPaymentWindow().getPaymentDesc()).replaceAll("[^0-9]", "");
            presenter.currentServing(token.getToken(),appointment.getPaymentWindow().getPaymentAssignId());
        }
        else {
            channelSwitch = "window." +   (appointment.getAssessmentWindow().getPaymentDesc()).replaceAll("[^0-9]", "");
            presenter.currentServing(token.getToken(),appointment.getAssessmentWindow().getPaymentAssignId());
        }





        final Channel channel = pusher.subscribe(channelSwitch);

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


//
//        channel.bind("App\\Events\\CallAgain", new SubscriptionEventListener() {
//            @Override
//            public void onEvent(String channelName, String eventName, final String data) {
//                Log.d(">>>>",data);
//                String text="";
//                try {
//                    JSONObject data2 = new JSONObject(data).getJSONObject("window");
//                    text = data2.getString("id");
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



        if(reserveChecker) {
            detailBinding.textSuccess.setVisibility(View.VISIBLE);
            detailBinding.currentServing.setVisibility(View.GONE);
            detailBinding.appointmentServingClick.setVisibility(View.GONE);

        }

        if(appointment.getAppointmentTransStatus().equals("C")) {
            detailBinding.cancel.setVisibility(View.GONE);
            detailBinding.detailMap.setVisibility(View.GONE);
            detailBinding.reminders.setVisibility(View.GONE);
        }



        try {


            detailBinding.detailTaxType.setText("Tax Type: " + (presenter.getTaxType(appointment.getAppointmentTaxType())).getTaxTypeDesc());
            detailBinding.detailTransType.setText("Transaction Type: " + presenter.getTransactionType(Integer.parseInt(appointment.getAppointmentTransType())));
            detailBinding.detailBarangay.setText("Barangay: " + (presenter.getBarangay(appointment.getAppointmentBaranagay())).getBarangayName());
        }
        catch (Exception e)
        {
            Log.e("Error",e+"");
        }


        detailBinding.appointmentDetailsStatus.setTextColor(appointmentListAdapter.getStatusColor(appointment.getAppointmentTransStatus()));




        detailBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getContext())
                        .setTitle("Are you sure you want to cancel your appointment?")
                        .setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.cancelAppointment(token.getToken(),String.valueOf(appointment.getAppointmentId()));
                            }
                        })
                        .setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })

                        .show();

            }
        });

        detailBinding.detailMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             Intent i = new Intent(getContext(), MapActivity.class);
                i.putExtra("time", appointment.getAppointmentTransDate()+" "+appointment.getAppointmentTransTime());
                startActivity(i);
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
                reserveChecker=false;
                pusher.unsubscribe(channelSwitch);
                pusher.disconnect();

            }
        });

        detailBinding.appointmentDetailsClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(detailBinding.appointmentDetailsHide.getVisibility()==View.VISIBLE)
                  detailBinding.appointmentDetailsHide.setVisibility(View.GONE);
              else {
                  detailBinding.appointmentDetailsHide.setVisibility(View.VISIBLE);
              }
            }
        });

        detailBinding.appointmentServingClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(detailBinding.currentServing.getVisibility()==View.VISIBLE)
                    detailBinding.currentServing.setVisibility(View.GONE);
                else {
                    if(appointment.getAppointmentTransType().equals("2")) {
                        presenter.currentServing(token.getToken(),appointment.getPaymentWindow().getPaymentAssignId());
                    }
                    else {
                        presenter.currentServing(token.getToken(),appointment.getAssessmentWindow().getPaymentAssignId());
                    }
                    detailBinding.currentServing.setVisibility(View.VISIBLE);
                }
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


    @Override
    public void setAppointmentDateNew(final boolean trans, int month, int year, int totalSlots)
    {
        dialogDateSlot = new Dialog(getContext(),R.style.RaffleDialogTheme);

        dialogDateSlot.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        dateslotBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.dialog_dateslot,
                null,
                false);


        dateslotBinding.calendarView.setOnDateChangedListener(this);

        dateslotBinding.calendarView.addDecorator(new FunctionUtils.WeekendDisableDecorator());
        List<CalendarDay> calendarDaysGreen = new ArrayList<>();
        List<CalendarDay> calendarDaysOrange = new ArrayList<>();
        List<CalendarDay> calendarDaysRed = new ArrayList<>();
        slotRealmResults = realm.where(Slot.class).findAll();


        totalslot = totalSlots;

        if(!slotRealmResults.isEmpty()) {
            for (Slot value : slotRealmResults) {
                if(Integer.parseInt(value.getSlotCount()) < totalSlots/2 )
                calendarDaysGreen.add(presenter.getCalendar(value.getSlotDate()));
                else  if(Integer.parseInt(value.getSlotCount()) < totalSlots/1.25 )
                    calendarDaysOrange.add(presenter.getCalendar(value.getSlotDate()));
                else
                    calendarDaysRed.add(presenter.getCalendar(value.getSlotDate()));
            }
        }

        dateslotBinding.calendarView.addDecorator(new FunctionUtils.EventDecoratorAll(Color.GREEN));
        dateslotBinding.calendarView.addDecorator(new FunctionUtils.EventDecorator(Color.RED, calendarDaysRed));
        dateslotBinding.calendarView.addDecorator(new FunctionUtils.EventDecorator(Color.GREEN, calendarDaysGreen));
        dateslotBinding.calendarView.addDecorator(new FunctionUtils.EventDecorator(Color.rgb(255,165,0), calendarDaysOrange));



        Date date = new GregorianCalendar(year, month-1, 1).getTime();
        Calendar dateCounter = new GregorianCalendar(year, month-1, 1);
        Date date2 = new GregorianCalendar(year, month-1, dateCounter.getActualMaximum(Calendar.DAY_OF_MONTH)).getTime();

        if (new Date().after(date)) {

            Log.d(">>>","here");
            dateslotBinding.calendarView.state().edit()
                    .setMinimumDate(new Date())
                    .setMaximumDate(date2)
                    .commit();

        }else
        {


            dateslotBinding.calendarView.state().edit()
                    .setMinimumDate(date)
                    .setMaximumDate(date2)
                    .commit();
        }




        dateslotBinding.reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(trans)
                presenter.assessmentAppointment(assesTypeAdapter.getSelected(), selectedTaxtype, selectedDate, selectedBaranagay, token.getToken());
                else {
                    Appointment appointment = unpaidAdapter.getValue();
                    presenter.paymentAppointment(assesTypeAdapter.getSelected(), appointment.getAppointmentTaxType(), selectedDate, appointment.getAppointmentBaranagay(), token.getToken(), appointment.getAppointmentId());
                }
            }
        });



        dateslotBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDateSlot.dismiss();
            }
        });

        dialogDateSlot.setContentView(dateslotBinding.getRoot());
        dialogDateSlot.setCancelable(true);
        dialogDateSlot.show();

    }

    @Override
    public void onDateSelected(
            @NonNull MaterialCalendarView widget,
            @NonNull CalendarDay date,
            boolean selected) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        slotRealmResults = realm.where(Slot.class).findAll();
        String slottaken="0";
        if(!slotRealmResults.isEmpty()) {
            for (Slot value : slotRealmResults) {

                if(date.equals(presenter.getCalendar(value.getSlotDate())))
                {

                    slottaken = value.getSlotCount();
                  break;
                }

            }
        }
        dateslotBinding.dateSlot.setText(selected ? dateFormatter.format(date.getDate()) : "No Selection");


        selectedDate = dateFormatter.format(date.getDate());

        dateslotBinding.dateSlot.setText("Available Slots:  "+slottaken+"/"+totalslot);
    }



    @Override
    public void setAppointmentDate() {


//        Calendar sunday,saturday;
//        List<Calendar> weekends = new ArrayList<>();
//        int weeks = 50;
//
//        for (int i = 0; i < (weeks * 7) ; i = i + 7) {
//            sunday = Calendar.getInstance();
//            sunday.add(Calendar.DAY_OF_YEAR, (Calendar.SUNDAY - sunday.get(Calendar.DAY_OF_WEEK) + 7 + i));
//             saturday = Calendar.getInstance();
//             saturday.add(Calendar.DAY_OF_YEAR, (Calendar.SATURDAY - saturday.get(Calendar.DAY_OF_WEEK) + i));
//             weekends.add(saturday);
//            weekends.add(sunday);
//        }
//        Calendar[] disabledDays = weekends.toArray(new Calendar[weekends.size()]);
//
//
//        Calendar newCalendar = Calendar.getInstance();
//        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
//                AppointmentActivity.this,
//                newCalendar.get(Calendar.YEAR),
//                newCalendar.get(Calendar.MONTH),
//                newCalendar.get(Calendar.DAY_OF_MONTH)
//        );
//        int daysallowable = 1;//get from database
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, daysallowable);
//      //  Cal dateBefore30Days = cal.getTime();
//        datePickerDialog.setMinDate(cal);
//        datePickerDialog.setDisabledDays(disabledDays);
//        datePickerDialog.show(getActivity().getFragmentManager(),"");



        YearMonthPickerDialog yearMonthPickerDialog = new YearMonthPickerDialog(getContext(), new YearMonthPickerDialog.OnDateSetListener() {
            @Override
            public void onYearMonthSet(int year, int month) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);

                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");

                    selectYear = year;
                    selectMonth = month+1;




                    dialogBinding.etDate.setText(dateFormat.format(calendar.getTime()));

            }
        });

        yearMonthPickerDialog.show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        Calendar newDate = Calendar.getInstance();
        newDate.set(year, monthOfYear, dayOfMonth);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        dialogBinding.etDate.setText(dateFormatter.format(newDate.getTime()));

        selectedDate = dialogBinding.etDate.getText().toString();

    }


    @Override
    public void setAppointment(){






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









    public void dialogSetAppointment()
    {

        dialogBinding.setView(getMvpView());
        dialogBinding.recyclerView.setAdapter(assesTypeAdapter);


        dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dialogBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());

        final List<String> assestType = new ArrayList<>();
        assestType.add("Assessment and Payment");
        assestType.add("Payment");
        assesTypeAdapter.setAssessResult(assestType);




        dialogBinding.recyclerView2.setAdapter(unpaidAdapter);


        dialogBinding.recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        dialogBinding.recyclerView2.setItemAnimator(new DefaultItemAnimator());

        appointmentlmResults = realm.where(Appointment.class).findAll();
        unpaidAdapter.setAppointmentResult(realm.copyToRealmOrUpdate(appointmentlmResults.where()
                .contains("appointmentTransType","2" )
                .contains("appointmentTransStatus","P")
                .findAll()));//Sorted("eventDateFrom", Sort.ASCENDING)));

        unpaidAdapter.notifyDataSetChanged();



        if(unpaidAdapter.getItemCount()==0)
        {

            dialogBinding.unpaidList.setVisibility(View.VISIBLE);
            dialogBinding.recyclerView2.setVisibility(View.GONE);
        }




//        dialogBinding.layoutAppointment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!selectedDealerId.equals(""))
//                    chooseDateandSlot();
//                else
//                    showError("Please Select Dealer First");
//            }
//        });



        dialogBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                assesTypeAdapter.reset();
                selectedBaranagay="";selectedTaxtype="";selectedDate="";
                hideAssessTypeAdapter();
            }
        });

        dialogBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(assesTypeAdapter.getSelected().equals(""))
                {

                    showError("Please Select Transacion");
                }else if(selectedTaxtype.equals("")&&assesTypeAdapter.getSelected().equals("1"))
                {
                    presenter.loadTaxType(token.getToken());
                    showError("Please Select Tax Type Again");

                }else if(selectMonth == 0 || selectYear == 0)
                {
                    showError("Please Select Month");
                }else if(selectedBaranagay.equals("")&&assesTypeAdapter.getSelected().equals("1"))
                {
                    presenter.loadBarangay(token.getToken());
                    showError("Please Select Barangay Again");
                }
                else {

                    if(assesTypeAdapter.getSelected().equals("1"))
                   // presenter.assessmentAppointment(assesTypeAdapter.getSelected(), selectedTaxtype, selectedDate, selectedBaranagay, token.getToken());
                        presenter.slotAppointment(assesTypeAdapter.getSelected(), selectedTaxtype,selectMonth,selectYear, selectedBaranagay, token.getToken(),true);

                    else if(assesTypeAdapter.getSelected().equals("2")) {
                        if(unpaidAdapter.getSelected().equals("true")) {

                            Appointment appointment = unpaidAdapter.getValue();
                          //  presenter.paymentAppointment(assesTypeAdapter.getSelected(), appointment.getAppointmentTaxType(), selectedDate, appointment.getAppointmentBaranagay(), token.getToken(),appointment.getAppointmentId());
                            presenter.slotAppointment(assesTypeAdapter.getSelected(), selectedTaxtype,selectMonth,selectYear, selectedBaranagay, token.getToken(),false);

                        }

                        else
                            showError("Please Select Unpaid Transaction");
                    }
                }

            }
        });

        dialog.setContentView(dialogBinding.getRoot());
        dialog.setCancelable(false);
        dialog.show();


    }


    @Override
    public void layoutSwitch(int switcher)
    {
        if(switcher==1) {
            dialogBinding.taxAssess.setVisibility(View.GONE);
            dialogBinding.taxPayment.setVisibility(View.VISIBLE);
        }
            else
        {
            dialogBinding.taxAssess.setVisibility(View.VISIBLE);
            dialogBinding.taxPayment.setVisibility(View.GONE);
        }
    }

    public void  hideAssessTypeAdapter()
    {
        dialogBinding.taxAssess.setVisibility(View.GONE);
        dialogBinding.taxPayment.setVisibility(View.GONE);
    }




    @Override
    public void closeDialog(String message) {

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        onRefresh();



    }
    @Override
    public void logOut() {
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
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
                realm.close();
                Toast.makeText(getContext(), "Realm Error", Toast.LENGTH_SHORT).show();
            }
        });

    }



}
