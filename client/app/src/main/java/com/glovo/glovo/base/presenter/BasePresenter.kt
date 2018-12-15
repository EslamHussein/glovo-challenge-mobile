package com.glovo.glovo.base.presenter

import com.glovo.glovo.base.view.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference


/**
 * Created Eslam Hussein .
 */
abstract class BasePresenter<V : MvpView>(private val view: V) : MvpPresenter {

    private var viewRef: WeakReference<V>? = null

    private var disposables: CompositeDisposable? = null

    fun getMyView(): V? = viewRef?.get()

    override fun onAttach() {
        viewRef = WeakReference(view)
    }

    fun addDisposable(disposable: Disposable) {
        if (disposables == null)
            disposables = CompositeDisposable()

        disposables?.add(disposable)
    }


    override fun onResume() {
        // Not mandatory for all views, if views are interested in receiving this event, they should
        // override this method
    }

    override fun onDetach() {
        if (viewRef != null) {
            viewRef?.clear()
            viewRef = null
        }
        if (disposables != null)
            disposables!!.dispose()

    }

}