package pasigqueueportal.com.pqpapp.ui.main.currentAppointment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pasigqueueportal.com.pqpapp.R;
import pasigqueueportal.com.pqpapp.databinding.ItemAppointmentAssessmentBinding;


public class AssesTypeAdapter extends RecyclerView.Adapter<AssesTypeAdapter.ViewHolder> {
    private List<String> assess;
    private final Context context;
    private final AppointmentView view;
    private static final int VIEW_TYPE_DEFAULT = 0;
    private Boolean reloadImage;
    private int chooseAssessment;



    public AssesTypeAdapter(Context context, AppointmentView view) {
        this.context = context;
        this.view = view;
        assess = new ArrayList<>();
        chooseAssessment = -1;


    }


    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_DEFAULT;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       final ItemAppointmentAssessmentBinding itemAssessmentBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_appointment_assessment,
                parent,
                false);





        return new ViewHolder(itemAssessmentBinding);
    }

    @Override
    public void onBindViewHolder(final AssesTypeAdapter.ViewHolder holder, final int position) {




        holder.itemAssessmentBinding.appointmentAssessment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {




               if(chooseAssessment == position)
                {
                    chooseAssessment =  -1;
                    holder.itemAssessmentBinding.appointmentAssessment.setCardBackgroundColor(Color.WHITE);
                }else
                {
                    chooseAssessment = position;
                    holder.itemAssessmentBinding.appointmentAssessment.setCardBackgroundColor(Color.parseColor("#26007a60"));
                    notifyDataSetChanged();
                }

                view.layoutSwitch(position);
            }
        });


        checkClickStatus(position,holder);

            holder.itemAssessmentBinding.appointmentAssessmentText.setText(assess.get(position));
            holder.itemAssessmentBinding.setView(view);

    }

    public void checkClickStatus(int position,AssesTypeAdapter.ViewHolder holder)
    {

        if(chooseAssessment != position)
        {
            holder.itemAssessmentBinding.appointmentAssessment.setCardBackgroundColor(Color.WHITE);
        }else
        {
            holder.itemAssessmentBinding.appointmentAssessment.setCardBackgroundColor(Color.parseColor("#26007a60"));
        }



    }

    public void reset()
    {
        chooseAssessment = -1;

    }


    public void clear() {
        assess.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return assess.size();
    }



    public String getSelected()
    {
        if(chooseAssessment==-1)
            return "";
        return String.valueOf(chooseAssessment+1);
    }





    public void setAssessResult(List<String> event) {
        this.assess = event;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemAppointmentAssessmentBinding itemAssessmentBinding;

        public ViewHolder(ItemAppointmentAssessmentBinding itemAssessmentBinding) {
            super(itemAssessmentBinding.getRoot());
            this.itemAssessmentBinding = itemAssessmentBinding;
        }



    }
}
