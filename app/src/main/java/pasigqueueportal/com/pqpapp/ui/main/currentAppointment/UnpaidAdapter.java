package pasigqueueportal.com.pqpapp.ui.main.currentAppointment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
    public void onBindViewHolder(final UnpaidAdapter.ViewHolder holder, final int position) {



        holder.itemAppointmentBinding.unpaidListCardview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                if(chooseAssessment == position)
                {
                    chooseAssessment =  -1;
                    holder.itemAppointmentBinding.unpaidListCardview.setCardBackgroundColor(Color.WHITE);
                }else
                {
                    chooseAssessment = position;
                    holder.itemAppointmentBinding.unpaidListCardview.setCardBackgroundColor(Color.parseColor("#26007a60"));
                    notifyDataSetChanged();
                }


            }
        });


        checkClickStatus(position,holder);


        holder.itemAppointmentBinding.setAppointment(appointment.get(position));
           holder.itemAppointmentBinding.setView(view);


           holder.itemAppointmentBinding.appointListTaxType.setText("Tax Type: "+(getTaxType(appointment.get(position).getAppointmentTaxType())).getTaxTypeDesc());
         holder.itemAppointmentBinding.appointListDate.setText(FunctionUtils.appointListTimestampMonDate(appointment.get(position).getAppointmentTransDate()));
         holder.itemAppointmentBinding.appointListYear.setText(FunctionUtils.appointListTimestampYear(appointment.get(position).getAppointmentTransDate()));





    }
    public void checkClickStatus(int position,UnpaidAdapter.ViewHolder holder)
    {

        if(chooseAssessment != position)
        {
            holder.itemAppointmentBinding.unpaidListCardview.setCardBackgroundColor(Color.WHITE);
        }else
        {
            holder.itemAppointmentBinding.unpaidListCardview.setCardBackgroundColor(Color.parseColor("#26007a60"));
        }



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
