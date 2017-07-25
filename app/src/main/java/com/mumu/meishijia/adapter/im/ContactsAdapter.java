package com.mumu.meishijia.adapter.im;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mumu.meishijia.R;
import com.mumu.meishijia.model.im.ContactsModel;
import com.mumu.meishijia.model.im.ContactsRealmModel;

import java.util.List;

import lib.swipelayout.BaseSwipeAdapter;
import lib.swipelayout.SwipeLayout;
import lib.utils.MyLogUtil;
import lib.utils.ToastUtil;

/**
 * 联系人的adapter
 * Created by Administrator on 2017/4/6.
 */

public class ContactsAdapter extends BaseSwipeAdapter{

    private Context context;
    private List<ContactsModel> contactsList;

    public ContactsAdapter(Context context, List<ContactsModel> contactsList){
        this.context = context;
        this.contactsList = contactsList;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_layout;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.layout_contacts_item, null);
    }

    @Override
    public void fillValues(int position, View convertView) {
        final ContactsModel model = contactsList.get(position);
        Holder holder = new Holder();
        holder.txtLetter = (TextView) convertView.findViewById(R.id.txt_letter);
        holder.swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipe_layout);
        holder.imgIm = (ImageView) convertView.findViewById(R.id.img_im);
        holder.imgAvatar = (ImageView) convertView.findViewById(R.id.img_avatar);
        holder.txtName = (TextView) convertView.findViewById(R.id.txt_name);

        //显示字母
        if(position == 0 || !model.getSort_letter().equals(contactsList.get(position - 1).getSort_letter())){
            holder.txtLetter.setText(model.getSort_letter());
            holder.txtLetter.setVisibility(View.VISIBLE);
        }else {
            holder.txtLetter.setVisibility(View.GONE);
        }

        Glide.with(context).load(model.getAvatar()).placeholder(R.drawable.icon_default_avatar).into(holder.imgAvatar);
        holder.txtName.setText(model.getRemark());

        holder.imgIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("进入聊天");
            }
        });
    }

    @Override
    public int getCount() {
        if(contactsList == null)
            return 0;
        return contactsList.size();
    }

    @Override
    public Object getItem(int position) {
        if(contactsList == null){
            return null;
        }
        return contactsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class Holder{
        TextView txtLetter;
        SwipeLayout swipeLayout;
        ImageView imgIm;
        ImageView imgAvatar;
        TextView txtName;
    }

    /**
     * 设置数据
     * @param list
     */
    public void setData(List<ContactsModel> list) {
        if (list == null){
            this.contactsList.clear();
            notifyDataSetChanged();
            return;
        }
        this.contactsList.clear();
        for (ContactsModel t : list) {
            this.contactsList.add(t);
        }
        notifyDataSetChanged();
    }

    /**
     * 增加数据
     * @param list
     */
    public void addData(List<ContactsModel> list){
        if(list == null)
            return;
        this.contactsList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 获取数据
     * @return
     */
    public List<ContactsModel> getData() {
        return this.contactsList;
    }

    /**
     * 清除数据
     */
    public void clearData(){
        contactsList.clear();
        notifyDataSetChanged();
    }
}
