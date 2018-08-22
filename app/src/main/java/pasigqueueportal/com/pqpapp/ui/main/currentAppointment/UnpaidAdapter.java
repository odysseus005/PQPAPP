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
import pasigqueueportal.com.pqpapp.databinding.ItemUnpaidAppointmentBinding;
import pasigqueueportal.com.pqpapp.model.data.Appointment;
import pasigqueueportal.com.pqpapp.model.data.Barangay;
import pasigqueueportal.com.pqpapp.model.data.TaxType;
import pasigqueueportal.com.pqpapp.util.FunctionUtils;


public class UnpaidAdapter extends RecyclerView.Adapter<UnpaidAdapter.ViewHolder> {
    private List<Appointment> appointment;
    private final Context context;
    private final AppointmentView view;
    private static final int VIEW_TYPE_DEFAULT = 0;
    private Realm realm;
    private int chooseAssessment;


    public UnpaidAdapter(Context context, AppointmentView view, Realm realm) {
        this.context = context;
        this.view = view;
        appointment = new ArrayList<>();
        chooseAssessment = -1;
        this.realm = realm;
    }


    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_DEFAULT;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemUnpaidAppointmentBinding itemAppointmentBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_unpaid_appointment,
                parent,
                false);

        return new ViewHolder(itemAppointmentBinding);
    }

    @Override
    public void onBindViewHolder(UnpaidAdapter.ViewHolder holder, int position) {



           holder.itemAppointmentBinding.setAppointment(appointment.get(position));
           holder.itemAppointmentBinding.setView(view);


           holder.itemAppointmentBinding.appointListTaxType.setText((getTaxType(appointment.get(position).getAppointmentTaxType())).getTaxTypeDesc());
           holder.itemAppointmentBinding.appointListTransType.setText(getTransactionType(Integer.parseInt(appointment.get(position).getAppointmentTransType())));


    }



    public String getSelected()
    {
        if(chooseAssessment==-1)
            return "";
        else
            return "true";

    }

    public Appointment getValue()
    {

        return  appointment.get(chooseAssessment);
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
        private final ItemUnpaidAppointmentBinding  itemAppointmentBinding;

        public ViewHolder(ItemUnpaidAppointmentBinding  itemAppointmentBinding) {
            super(itemAppointmentBinding.getRoot());
            this.itemAppointmentBinding = itemAppointmentBinding;
        }



    }

    public String getTransactionType(int id)
    {

        if(id==0)
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
