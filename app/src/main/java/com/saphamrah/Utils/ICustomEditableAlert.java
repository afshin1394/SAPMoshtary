package com.saphamrah.Utils;

import com.saphamrah.CustomView.CustomTextInputLayout;

public interface ICustomEditableAlert <T extends Object>{
    void setOnCancelClick();

    void setOnApplyClick(CustomTextInputLayout customTextInputLayout,T message);

    void onTextChange(CustomTextInputLayout customTextInputLayout,String s);
}
