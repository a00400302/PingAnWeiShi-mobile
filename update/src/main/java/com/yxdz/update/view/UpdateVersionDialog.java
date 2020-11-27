package com.yxdz.update.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yxdz.update.R;
import com.yxdz.update.utils.Constants;
import com.yxdz.update.utils.SPUtils;

/**
 * 版本更新提示框
 * Created by huang on 2018/6/1.
 */
public class UpdateVersionDialog extends Dialog {

    private TextView versionText, versionNumText, timeText, sizeText, bodyText;
    private Button btnCancel, btnOk;
    private View lineView;
    private boolean type;
    private Context context;

    public UpdateVersionDialog(Context context, boolean type) {
        super(context, R.style.update_custon_dialog);
        this.context=context;
        this.type = type;
        setCustomDialog();
    }

    private void setCustomDialog() {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.update_dialog_layout, null);
        versionText = view.findViewById(R.id.update_version);//版本
        versionNumText = view.findViewById(R.id.update_version_num);//版本号
        timeText = view.findViewById(R.id.update_time);//更新时间
        sizeText = view.findViewById(R.id.update_size);//更新包大小
        bodyText = view.findViewById(R.id.update_body);//更新内容
        btnCancel = view.findViewById(R.id.update_cancel);//取消
        btnOk = view.findViewById(R.id.update_ok);//确定
        lineView = view.findViewById(R.id.update_cancel_line);

        btnCancel.setOnClickListener(onClick);
        btnOk.setOnClickListener(onClick);
        setCanceledOnTouchOutside(false);
        setOnKeyListener(keylistener);

        //是否显示取消按钮
        if (type) {
            btnCancel.setVisibility(View.VISIBLE);
            lineView.setVisibility(View.VISIBLE);
        } else {
            btnCancel.setVisibility(View.GONE);
            lineView.setVisibility(View.GONE);
        }

        super.setContentView(view);
    }

    /**
     * 禁止返回键点击
     */
    OnKeyListener keylistener = new OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            return keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0;
        }
    };

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.update_cancel) {
                dismiss();
                SPUtils.put(context, Constants.UPDATE_CANCLE_FLAG,true);
            } else if (i == R.id.update_ok) {
                updateVersion.updateOk();
                SPUtils.put(context,Constants.UPDATE_CANCLE_FLAG,false);
            }
        }
    };


    public interface UpdateVersionInterface {
        void updateOk();
    }

    private UpdateVersionInterface updateVersion;

    public void setUpdateVersion(UpdateVersionInterface updateVersion) {
        this.updateVersion = updateVersion;
    }

    public TextView getVersionText() {
        return versionText;
    }

    public TextView getVersionNumText() {
        return versionNumText;
    }

    public TextView getTimeText() {
        return timeText;
    }

    public TextView getSizeText() {
        return sizeText;
    }

    public TextView getBodyText() {
        return bodyText;
    }

    public Button getOkBtn() {
        return btnOk;
    }
}
