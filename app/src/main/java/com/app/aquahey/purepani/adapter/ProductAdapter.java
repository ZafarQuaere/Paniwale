package com.app.aquahey.purepani.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.app.aquahey.purepani.R;
import com.app.aquahey.purepani.activity.BookingActivity;
import com.app.aquahey.purepani.databinding.ProductViewBinding;
import com.app.aquahey.purepani.model.Product;
import com.app.aquahey.purepani.viewmodel.AquasViewHolder;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.DataViewHolder> {
    private List<Product> data;
    private Context context;

    public ProductAdapter(final Context context) {
        this.data = new ArrayList<>();
        this.context = context;
    }


    @Override
    public ProductAdapter.DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view,
                new FrameLayout(parent.getContext()), false);
        return new ProductAdapter.DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.DataViewHolder holder, int position) {
        final Product product = data.get(position);
        holder.setViewModel(new AquasViewHolder(product));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(context, BookingActivity.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Product", product);
                context.startActivity(intent);
            }
        });

    }

    public void updateData(@Nullable List<Product> data) {
        if (data == null || data.isEmpty()) {
            this.data.clear();
        } else {
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onViewAttachedToWindow(ProductAdapter.DataViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.bind();
    }

    @Override
    public void onViewDetachedFromWindow(ProductAdapter.DataViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unBind();
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {
        ProductViewBinding binding;

        public DataViewHolder(View itemView) {
            super(itemView);
            bind();
        }

        void bind() {
            if (null == binding) {
                binding = DataBindingUtil.bind(itemView);
            }
        }

        void unBind() {
            if (null != binding) {
                binding.unbind();
            }
        }

        void setViewModel(final AquasViewHolder viewModel) {
            if (null != binding) {
                binding.setAquaModel(viewModel);
            }
        }
    }

}