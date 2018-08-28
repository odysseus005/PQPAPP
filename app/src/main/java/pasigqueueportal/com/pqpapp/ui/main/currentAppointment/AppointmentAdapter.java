package pasigqueueportal.com.pqpapp.ui.main.currentAppointment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import pasigqueueportal.com.pqpapp.R;
import pasigqueueportal.com.pqpapp.databinding.ItemCurrentAppointmentBinding;
import pasigqueueportal.com.pqpapp.model.data.Appointment;
import pasigqueueportal.com.pqpapp.model.data.Barangay;
import pasigqueueportal.com.pqpapp.model.data.TaxType;
import pasigqueueportal.com.pqpapp.util.FunctionUtils;


public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private List<Appointment> appointment;
    private final Context context;
    private final AppointmentView view;
    private static final int VIEW_TYPE_DEFAULT = 0;
    private Realm realm;


    public AppointmentAdapter(Context context, AppointmentView view,Realm realm) {
        this.context = context;
        this.view = view;
        appointment = new ArrayList<>();
        this.realm = realm;
    }


    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_DEFAULT;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCurrentAppointmentBinding itemAppointmentBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_current_appointment,
                parent,
                false);

        return new ViewHolder(itemAppointmentBinding);
    }

    @Override
    public void onBindViewHolder(AppointmentAdapter.ViewHolder holder, int position) {



           holder.itemAppointmentBinding.setAppointment(appointment.get(position));
           holder.itemAppointmentBinding.setView(view);


    //    Log.d(">>>>>",(getTaxType(appointment.get(position).getAppointmentTaxType())).getTaxTypeDesc());
           holder.itemAppointmentBinding.appointListTaxType.setText((getTaxType(appointment.get(position).getAppointmentTaxType())).getTaxTypeDesc());
           holder.itemAppointmentBinding.appointListTransType.setText(getTransactionType(Integer.parseInt(appointment.get(position).getAppointmentTransType())));
           holder.itemAppointmentBinding.appointListDate.setText(FunctionUtils.appointListTimestampMonDate(appointment.get(position).getAppointmentTransDate()));
           holder.itemAppointmentBinding.appointListYear.setText(FunctionUtils.appointListTimestampYear(appointment.get(position).getAppointmentTransDate()));
           holder.itemAppointmentBinding.appointListTime.setText(FunctionUtils.hour24to12hour(appointment.get(position).getAppointmentTransTime()));
           holder.itemAppointmentBinding.appointmentStatusColor.setBackgroundColor(getStatusColor(appointment.get(position).getAppointmentTransStatus()));

    }

    public int getStatusColor(String status)
    {
        int returnColor=0;
        switch (status)
        {
            case "P":
                returnColor = Color.parseColor("#9ccc65");
                break;
            case "N":

                returnColor = Color.parseColor("#b95d5d");
                break;

            case "S":
                returnColor = Color.parseColor("#9ccc65");

                break;

            case "C":

                returnColor = Color.parseColor("#b95d5d");
                break;

            default:
                returnColor = Color.parseColor("#9ccc65");
                break;

        }

        return  returnColor;
    }


    public void clear() {
        appointment.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return appointment.size();
    }

    public void setAppointmentResult(List<Appointment> event) {
        this.appointment = event;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemCurrentAppointmentBinding itemAppointmentBinding;

        public ViewHolder(ItemCurrentAppointmentBinding itemAppointmentBinding) {
            super(itemAppointmentBinding.getRoot());
            this.itemAppointmentBinding = itemAppointmentBinding;
        }



    }

    public String getTransactionType(int id)
    {

        if(id==1)
            return "Assessment and Payment";
        else
            return "Payment";
    }

    TaxType getTaxType(String id){
        return realm.where(TaxType.class)
                .equalTo("taxTypeId", id)
                .findFirst();
    }

    Barangay getBarangay(String id){
        return realm.where(Barangay.class)
                .equalTo("barangayId", id)
                .findFirst();
    }


}
