package pasigqueueportal.com.pqpapp.ui.main.pastAppointment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import pasigqueueportal.com.pqpapp.R;


import pasigqueueportal.com.pqpapp.databinding.ItemPastAppointmentBinding;
import pasigqueueportal.com.pqpapp.model.data.Appointment;
import pasigqueueportal.com.pqpapp.model.data.TaxType;
import pasigqueueportal.com.pqpapp.util.FunctionUtils;


public class PastAppointmentAdapter extends RecyclerView.Adapter<PastAppointmentAdapter.ViewHolder> {
    private List<Appointment> pastappointment;
    private final Context context;
    private final PastAppointmentView view;
    private static final int VIEW_TYPE_DEFAULT = 0;
    private Realm realm;


    public PastAppointmentAdapter(Context context, PastAppointmentView view,Realm realm) {
        this.context = context;
        this.view = view;
        pastappointment = new ArrayList<>();
        this.realm = realm;
    }


    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_DEFAULT;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPastAppointmentBinding itemAppointmentBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_past_appointment,
                parent,
                false);




        return new ViewHolder(itemAppointmentBinding);
    }

    @Override
    public void onBindViewHolder(final PastAppointmentAdapter.ViewHolder holder,final int position) {






            holder.itemAppointmentBinding.setAppointment(pastappointment.get(position));
            holder.itemAppointmentBinding.setView(view);
        holder.itemAppointmentBinding.appointListTaxType.setText((getTaxType(pastappointment.get(position).getAppointmentTaxType())).getTaxTypeDesc());
        holder.itemAppointmentBinding.appointListTransType.setText(getTransactionType(Integer.parseInt(pastappointment.get(position).getAppointmentTransType())));
        holder.itemAppointmentBinding.appointListDate.setText(FunctionUtils.appointListTimestampMonDate(pastappointment.get(position).getAppointmentTransDate()));
        holder.itemAppointmentBinding.appointListYear.setText(FunctionUtils.appointListTimestampYear(pastappointment.get(position).getAppointmentTransDate()));
        holder.itemAppointmentBinding.appointListTime.setText(FunctionUtils.hour24to12hour(pastappointment.get(position).getAppointmentTransTime()));
        if(pastappointment.get(position).getAppointmentTransType().equals("2")&&pastappointment.get(position).getAppointmentTransStatus().equals("P"))
        {
            holder.itemAppointmentBinding.appointmentStatusColor.setBackgroundColor(Color.parseColor("#b95d5d"));
            holder.itemAppointmentBinding.pastappointstatus.setText("Unpaid");
        }
            else{
        holder.itemAppointmentBinding.appointmentStatusColor.setBackgroundColor(getStatusColor(pastappointment.get(position).getAppointmentTransStatus()));
        holder.itemAppointmentBinding.pastappointstatus.setText(FunctionUtils.convertStatus(pastappointment.get(position).getAppointmentTransStatus()));
        }



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
        pastappointment.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pastappointment.size();
    }

    public void setAppointmentResult(List<Appointment> event) {
        this.pastappointment = event;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemPastAppointmentBinding itemAppointmentBinding;

        public ViewHolder(ItemPastAppointmentBinding itemAppointmentBinding) {
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

}
