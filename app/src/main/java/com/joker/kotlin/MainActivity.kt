package com.joker.kotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

import com.jokermk.mvp.impl.BaseActivity

class MainActivity : BaseActivity<LoginPresenter>() {

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tvTest).setOnClickListener {
            presenter.login("dada","13")
        }
    }


    fun loginSuccess(isFirst:Boolean) {
        Toast.makeText(this,"login",Toast.LENGTH_LONG).show()
    }
}
