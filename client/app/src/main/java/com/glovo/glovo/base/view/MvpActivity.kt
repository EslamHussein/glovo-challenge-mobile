package com.glovo.glovo.base.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.glovo.glovo.base.presenter.MvpPresenter

abstract class MvpActivity<V : MvpView, P : MvpPresenter>:AppCompatActivity(),MvpView {


    abstract val presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onAttach()

    }

    override fun onResume() {
        super.onResume()

        presenter.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

}