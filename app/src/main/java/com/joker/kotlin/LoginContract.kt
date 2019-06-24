package com.joker.kotlin

/**
 * Created by Joker on 2019/6/24.
 */
interface LoginContract {
    interface LoginView{
        fun loginSuccess()
    }
    interface LoginPresenter {
        fun login(name:String,age:Int)
    }
}