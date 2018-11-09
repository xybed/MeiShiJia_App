package com.mumu.meishijia.view.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.R;
import com.mumu.meishijia.constant.ReceivingAddressType;
import com.mumu.meishijia.model.mine.ReceivingAddress;
import com.mumu.meishijia.presenter.mine.ReceivingAddressEditPresenter;
import com.mumu.meishijia.view.BaseActivity;
import com.mumu.meishijia.view.common.SelectCityActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.utils.RegexUtil;
import lib.utils.StringUtil;

public class ReceivingAddressEditActivity extends BaseActivity<ReceivingAddressEditPresenter> implements ReceivingAddressEditView{
    public static final String TITLE = "title";
    public static final String RECEIVING_ADDRESS = "receiving_address";
    public static final int REQ_CITY = 1;

    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.txt_province_city)
    TextView txtProvinceCity;
    @BindView(R.id.edit_address)
    EditText editAddress;
    @BindView(R.id.img_switch)
    ImageView imgSwitch;
    @BindView(R.id.btn_save)
    Button btnSave;

    private ReceivingAddress receivingAddress;
    private String province;
    private String city;
    private boolean isDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiving_address_edit);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI(){
        Intent intent = getIntent();
        receivingAddress = intent.getParcelableExtra(RECEIVING_ADDRESS);
        if(receivingAddress != null){
            editName.setText(receivingAddress.getName());
            editPhone.setText(receivingAddress.getPhone());
            txtProvinceCity.setText(getString(R.string.user_province_city_placeholder,
                    receivingAddress.getProvince(), receivingAddress.getCity()));
            editAddress.setText(receivingAddress.getAddress());
            if(ReceivingAddressType.DEFAULT.getCode().intValue() == receivingAddress.getType()){
                imgSwitch.setImageResource(R.drawable.icon_switch_open);
            }else {
                imgSwitch.setImageResource(R.drawable.icon_switch_close);
            }
        }

        String title = intent.getStringExtra(TITLE);
        setTitle(title);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setButtonStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editName.addTextChangedListener(textWatcher);
        editPhone.addTextChangedListener(textWatcher);
        editAddress.addTextChangedListener(textWatcher);
    }

    private void setButtonStatus(){
        if(!StringUtil.isEmpty(editName.getText().toString()) &&
                !StringUtil.isEmpty(editPhone.getText().toString()) &&
                !StringUtil.isEmpty(province) &&
                !StringUtil.isEmpty(city) &&
                !StringUtil.isEmpty(editAddress.getText().toString())){
            btnSave.setEnabled(true);
        }else {
            btnSave.setEnabled(false);
        }
    }

    @OnClick({R.id.txt_province_city, R.id.img_switch, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_province_city:
                startActivityForResult(new Intent(this, SelectCityActivity.class), REQ_CITY);
                break;
            case R.id.img_switch:
                isDefault = !isDefault;
                imgSwitch.setImageResource(isDefault ? R.drawable.icon_switch_open : R.drawable.icon_switch_close);
                break;
            case R.id.btn_save:
                save();
                break;
        }
    }

    private void save(){
        if(editName.getText().toString().trim().length() < 2){
            toast("收货人最少两个字");
            return;
        }
        if(!RegexUtil.checkMobile(editPhone.getText().toString())){
            toast(R.string.user_input_phone_number);
            return;
        }
        presenter.addReceivingAddress(MyApplication.getInstance().getUser().getId(),
                editName.getText().toString(), editPhone.getText().toString(),
                province, city, editAddress.getText().toString(), isDefault);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK)
            return;
        //选择城市回调的结果
        if(requestCode == REQ_CITY){
            province = data.getStringExtra(SelectCityActivity.RESULT_PROVINCE);
            city = data.getStringExtra(SelectCityActivity.RESULT_CITY);
            txtProvinceCity.setText(getString(R.string.user_province_city_placeholder, province, city));
            setButtonStatus();
        }
    }

    @Override
    public void addSuccess(String s) {
        toast(s);
        //TODO 刷新收货地址列表

    }

    @Override
    public void updateSuccess(String s) {
        toast(s);
        //TODO 刷新收货地址列表
    }
}
