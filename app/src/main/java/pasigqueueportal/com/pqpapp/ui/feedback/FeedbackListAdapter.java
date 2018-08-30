package pasigqueueportal.com.pqpapp.ui.feedback;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import pasigqueueportal.com.pqpapp.R;
import pasigqueueportal.com.pqpapp.databinding.ItemFeedbackBinding;
import pasigqueueportal.com.pqpapp.model.data.Feedback;


public class FeedbackListAdapter extends RecyclerView.Adapter<FeedbackListAdapter.ViewHolder> {
    private List<Feedback> feedback;
    private final Context context;
    private final FeedbackListView view;
    private static final int VIEW_TYPE_DEFAULT = 0;


    public FeedbackListAdapter(Context context, FeedbackListView view) {
        this.context = context;
        this.view = view;
        feedback = new ArrayList<>();
    }


    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_DEFAULT;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemFeedbackBinding itemFeedbackBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_feedback,
                parent,
                false);








        return new ViewHolder(itemFeedbackBinding);
    }

    @Override
    public void onBindViewHolder(FeedbackListAdapter.ViewHolder holder, final int position) {
        holder.itemFeedbackBinding.setFeedback(feedback.get(position));
        holder.itemFeedbackBinding.setView(view);

        holder.itemFeedbackBinding.ratingBar.setRating(Float.parseFloat(feedback.get(position).getFeedbackRate()));
        holder.itemFeedbackBinding.ratingText.setText(feedbackString(Float.parseFloat(feedback.get(position).getFeedbackRate())));



    }

    public String feedbackString(float rating) {

        String comment="";
        switch (Math.round(rating)) {
            case 0:
                comment = "Very Bad!";
                break;
            case 1:
                comment = "Very Bad!";
                break;
            case 2:
                comment = "Not good!";
                break;
            case 3:
                comment = "Quite ok!";
                break;
            case 4:
                comment = "Very Good!";
                break;
            case 5:
                comment = "Excellent!";
                break;
        }
        return  comment;
    }


    public void clear() {
        feedback.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return feedback.size();
    }

    public void setEventResult(List<Feedback> feedback) {
        this.feedback = feedback;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemFeedbackBinding itemFeedbackBinding;

        public ViewHolder(ItemFeedbackBinding itemFeedbackBinding) {
            super(itemFeedbackBinding.getRoot());
            this.itemFeedbackBinding = itemFeedbackBinding;
        }



    }
}
