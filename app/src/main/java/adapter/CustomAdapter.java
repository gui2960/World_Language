package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.guifactory.R;
import entity.JsonObject;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> implements Filterable {

    private List<JsonObject> jsonObjects;
    private List<JsonObject> jsonObjectsFull;
    private Context context;

    public CustomAdapter(Context context, List<JsonObject> jsonObjects) {
        this.context = context;
        this.jsonObjects = jsonObjects;
        jsonObjectsFull = new ArrayList<>(jsonObjects);

    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView textViewResource;
        TextView textViewUpdated;
        TextView textViewValue;


        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            textViewResource = mView.findViewById(R.id.textViewResourceValue);
            textViewUpdated = mView.findViewById(R.id.textViewUpdateValue);
            textViewValue = mView.findViewById(R.id.textViewValueValue);

        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.textViewResource.setText(jsonObjects.get(position).getResource().getResource_id());
        holder.textViewValue.setText(jsonObjects.get(position).getResource().getValue());
        holder.textViewUpdated.setText(jsonObjects.get(position).getResource().getUpdated_at());


    }

    @Override
    public int getItemCount() {
        return jsonObjects.size();
    }


    @Override
    public Filter getFilter() {
        return jsonObjectsFilter;
    }

    Filter jsonObjectsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<JsonObject> filteredList = new ArrayList<>();

            if (constraint.equals(null) || constraint.length() == 0) {
                filteredList.addAll(jsonObjects);
            } else {
                String filterPattern = constraint.toString().toLowerCase();

                for (JsonObject jo : jsonObjectsFull) {
                    if (jo.getResource().getModule_id().toLowerCase().contains(filterPattern) ||
                            jo.getResource().getLanguage_id().toLowerCase().contains(filterPattern)) {
                        filteredList.add(jo);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            jsonObjects.clear();
            jsonObjects.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


}
