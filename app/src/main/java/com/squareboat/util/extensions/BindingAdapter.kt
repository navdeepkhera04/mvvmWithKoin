package com.squareboat.util.extensions

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.squareboat.R

@BindingAdapter("bind:getNameError")
fun getNameError(view: EditText, data: String) {
    view.onTextChanged {
        if(view.isBlank()) {
            view.error = "${view.context.getString(R.string.plz_enter)} $data"
            view.requestFocus()
        }
    }
}

@BindingAdapter("bind:getEmailError")
fun getEmailError(view: EditText, data: String) {
    view.onTextChanged {
        if(view.isBlank()) {
            view.error = view.context.getString(R.string.plz_enter_email)
            view.requestFocus()
        } else if(!view.checkString().isValidEmail()) {
            view.error = view.context.getString(R.string.plz_enter_valid_email)
            view.requestFocus()
        }
    }
}

@BindingAdapter("bind:getPassError")
fun getPassError(view: EditText, data: String) {
    view.onTextChanged {
        if(view.isBlank()) {
            view.error = view.context.getString(R.string.plz_enter_password)
            view.requestFocus()
        } else if(!view.isValidPassword()) {
            view.error = view.context.getString(R.string.plz_enter_valid_password)
            view.requestFocus()
        }
    }
}


@BindingAdapter("bind:setPhoneError")
fun setPhoneError(view: EditText, data: String) {
    view.onTextChanged {
        if(view.isBlank()) {
            view.error = "${view.context.getString(R.string.plz_enter)} $data"
            view.requestFocus()
        } else if(view.getLength()<5) {
            view.error = "$data ${view.context.getString(R.string.should_be_minimum_5_digits)}"
            view.requestFocus()
        }
    }
}


@BindingAdapter("bind:getskillsError")
fun getSkillsError(view: EditText, data: String) {
    view.onTextChanged {
        if(view.isBlank()) {
            view.error = view.context.getString(R.string.select_skills)
            view.requestFocus()
        }
    }
}


@BindingAdapter("bind:getfieldRequiredError")
fun getfieldRequiredError(view: EditText, data: String) {
    view.onTextChanged {
        if(view.isBlank()) {
            view.error = view.context.getString(R.string.field_required)
            view.requestFocus()
        }
    }
}






