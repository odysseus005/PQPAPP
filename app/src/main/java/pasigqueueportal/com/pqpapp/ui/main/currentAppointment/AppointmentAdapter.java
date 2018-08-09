package pasigqueueportal.com.pqpapp.ui.main.currentAppointment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pasigqueueportal.com.pqpapp.R;
import pasigqueueportal.com.pqpapp.databinding.ItemCurrentAppointmentBinding;
import pasigqueueportal.com.pqpapp.model.data.Appointment;


public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private List<Appointment> appointment;
    private final Context context;
    private final AppointmentView view;
    private static final int VIEW_TYPE_DEFAULT = 0;


    public AppointmentAdapter(Context context, AppointmentView view) {
        this.context = context;
        this.view = view;
        appointment = new ArrayList<>();
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


//           holder.itemAppointmentBinding.appointListDate.setText(FunctionUtils.appointListTimestampMonDate(appointment.get(position).getAppointDate()));
//           holder.itemAppointmentBinding.appointListYear.setText(FunctionUtils.appointListTimestampYear(appointment.get(position).getAppointDate()));
//
//           holder.itemAppointmentBinding.appointListTime.setText(FunctionUtils.hour24to12hour(appointment.get(position).getAppointschedTime()));
//
//           holder.itemAppointmentBinding.appointmentStatusColor.setBackgroundColor(getStatusColor(appointment.get(position).getAppointStatus()));

    }

    public int getStatusColor(String status)
    {
        int returnColor=0;
        switch (status)
        {
            case "CONFIRMED":
                returnColor = Color.parseColor("#9ccc65");
                break;
            case "CANCELLED":

                returnColor = Color.parseColor("#b95d5d");
                break;

            case "RESCHEDULE":
                returnColor = Color.parseColor("#78a741");

                break;

            case "NO SHOW":
                returnColor = Color.parseColor("#424242");

                break;
            case "SUCCESSFUL":
                returnColor = Color.parseColor("#9ccc65");

                break;

            default:
                returnColor = Color.parseColor("#caad51");
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
}
