package org.firespeed.phpconfukuoka18.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.NewThreadScheduler
import org.firespeed.phpconfukuoka18.model.Session

class SessionViewModel(application: Application) : AndroidViewModel(application) {
    private var live: MutableLiveData<List<Session>?>? = null

    fun getCurrent(): MutableLiveData<List<Session>?> {
        val instance = live ?: MutableLiveData()
        live = instance
        return instance
    }
    fun getAll(failedCallback: ((exception: Throwable) -> Unit)?): Disposable {
        return Session.getAll().subscribe({
            getCurrent().postValue(it)
        },{
            failedCallback?.invoke(it)
        })
    }




}