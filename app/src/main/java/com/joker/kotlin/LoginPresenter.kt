package com.joker.kotlin

import com.jokermk.mvp.impl.BasePresenter

/**
 * Created by Joker on 2019/6/24.
 */
class LoginPresenter : BasePresenter<MainActivity>() {

    fun login(name:String,age:String) {
        view.loginSuccess(false)
    }


}