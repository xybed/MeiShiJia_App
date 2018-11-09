package com.mumu.meishijia.adapter.mine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.BaseRecyclerAdapter;
import com.mumu.meishijia.constant.ReceivingAddressType;
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
        final ReceivingAddress item = datas.get(position);
        holder.txtName.setText(item.getName());
        holder.txtPhone.setText(item.getPhone());
        if(ReceivingAddressType.DEFAULT.getCode().intValue() == item.getType()){
            holder.txtType.setVisibility(View.VISIBLE);
        }else {
            holder.txtType.setVisibility(View.GONE);
        }
        holder.txtAddress.setText(context.getString(R.string.order_receiving_address_placeholder,
                item.getProvince(), item.getCity(), item.getAddress()));
        //设置点击监听
        holder.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onClickEdit(item);
            }
        });
        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onClickDelete(item.getId());
            }
        });
        holder.llayReceivingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onClickItem(item);
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    private OnItemClickListener listener;
    public interface OnItemClickListener{
        void onClickEdit(ReceivingAddress receivingAddress);
        void onClickDelete(int addressId);
        void onClickItem(ReceivingAddress receivingAddress);
    }

    class Holder extends RecyclerView.ViewHolder{
        @BindView(R.id.llay_receiving_address)
        LinearLayout llayReceivingAddress;
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
