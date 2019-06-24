package com.jokermk.mvp

interface IPresenter<out View: IMvpView<IPresenter<View>>>: ILifecycle {
    val mView: View
}

interface IMvpView<out Presenter: IPresenter<IMvpView<Presenter>>>: ILifecycle {
    val mPresenter: Presenter
}