package com.jokermk.mvp.impl

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jokermk.mvp.IMvpView
import com.jokermk.mvp.IPresenter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.coroutines.experimental.buildSequence
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

abstract class BaseActivity<out P : BasePresenter<BaseActivity<P>>> : AppCompatActivity(), IMvpView<P> {

    final override val mPresenter: P

    init {
        mPresenter = createPresenterKt()
        mPresenter.mView = this
    }

    private fun createPresenterKt(): P {
        buildSequence {
            var thisClass: KClass<*> = this@BaseActivity::class
            while (true) {
                yield(thisClass.supertypes)
                thisClass = thisClass.supertypes.firstOrNull()?.jvmErasure ?: break
            }
        }.flatMap {
            it.flatMap { it.arguments }.asSequence()
        }.first {
                    it.type?.jvmErasure?.isSubclassOf(IPresenter::class) ?: false
                }.let {
                    return it.type!!.jvmErasure.primaryConstructor!!.call() as P
                }
    }

    private fun createPresenter(): P {
        buildSequence<Type> {
            var thisClass: Class<*> = this@BaseActivity.javaClass
            while (true) {
                yield(thisClass.genericSuperclass)
                thisClass = thisClass.superclass ?: break
            }
        }.filter {
            it is ParameterizedType
        }.flatMap {
                    (it as ParameterizedType).actualTypeArguments.asSequence()
                }.first {
                    it is Class<*> && IPresenter::class.java.isAssignableFrom(it)
                }.let {
                    return (it as Class<P>).newInstance()
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.onCreate(savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {}

    override fun onStart() {
        super.onStart()
        mPresenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        mPresenter.onPause()
    }

    override fun onStop() {
        super.onStop()
        mPresenter.onStop()
    }

    override fun onDestroy() {
        mPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mPresenter.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        onViewStateRestored(savedInstanceState)
        mPresenter.onViewStateRestored(savedInstanceState)
    }
}
