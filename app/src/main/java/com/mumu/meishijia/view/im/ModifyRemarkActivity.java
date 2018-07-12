package com.mumu.meishijia.view.im;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.mumu.meishijia.R;
import com.mumu.meishijia.presenter.im.ModifyRemarkPresenter;
import com.mumu.meishijia.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import lib.utils.ToastUtil;

public class ModifyRemarkActivity extends BaseActivity<ModifyRemarkPresenter> implements ModifyRemarkView {
    public static final String FRIEND_ID = "friend_id";
    public static final String REMARK = "remark";

    @BindView(R.id.edit_remark)
    EditText editRemark;

    private int friendId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_remark);

        initUI();
    }

    private void initUI(){
        ButterKnife.bind(this);
        Intent intent = getIntent();
        friendId = intent.getIntExtra(FRIEND_ID, 0);
        String remark = intent.getStringExtra(REMARK);
        if(!TextUtils.isEmpty(remark)){
            editRemark.setText(remark);
        }
    }

    @Override
    protected void onRightButtonClick() {
        modifyRemark();
    }

    private void modifyRemark(){
        if(TextUtils.isEmpty(editRemark.getText().toString())){
            ToastUtil.show(R.string.im_input_remark);
            return;
        }
        showLoadingDialog(getString(R.string.com_committing));
        presenter.modifyRemark(friendId, editRemark.getText().toString());
    }

    @Override
    public void modifySuccess(String result) {
        dismissLoadingDialog();
        Intent intent = new Intent();
        intent.putExtra(ContactsDetailActivity.RESULT_REMARK, editRemark.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

}
