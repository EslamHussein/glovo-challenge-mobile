package com.glovo.glovo.base.presenter

import com.glovo.glovo.base.view.ShowErrorView
import com.glovo.glovo.base.exception.ErrorHandler
import com.glovo.glovo.base.view.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference


/**
 * Created Eslam Hussein .
 */
abstract class BasePresenter<View>(private val view: View, private val errorHandler: ErrorHandler) :
    MvpPresenter where View : MvpView, View : ShowErrorView {



    private var viewRef: WeakReference<View>? = null

    private var disposables: CompositeDisposable? = null

    fun getView(): View? = viewRef?.get()
    fun getErrorHandler(): ErrorHandler? = errorHandler

    override fun onAttach() {
        viewRef = WeakReference(view)
        errorHandler.attachView(view)

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

        errorHandler.detachView()
    }

}