package com.mumu.meishijia.adapter.mine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.BaseRecyclerAdapter;
import com.mumu.meishijia.model.mine.ReceivingAddress;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 77 on 2018/10/22 0022.
 * 收货地址adapter
 */

public class ReceivingAddressAdapter extends BaseRecyclerAdapter<ReceivingAddress, ReceivingAddressAdapter.Holder> {
    public ReceivingAddressAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_receiving_address_item;
    }

    @Override
    public Holder createHolder(View view) {
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }


    class Holder extends RecyclerView.ViewHolder{
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_phone)
        TextView txtPhone;
        @BindView(R.id.txt_type)
        TextView txtType;
        @BindView(R.id.txt_address)
        TextView txtAddress;
        @BindView(R.id.txt_edit)
        TextView txtEdit;
        @BindView(R.id.txt_delete)
        TextView txtDelete;

        Holder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
