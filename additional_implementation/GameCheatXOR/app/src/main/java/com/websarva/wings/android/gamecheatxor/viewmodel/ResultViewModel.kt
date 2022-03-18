package com.websarva.wings.android.gamecheatxor.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.websarva.wings.android.gamecheatxor.model.Result
import com.websarva.wings.android.gamecheatxor.repository.EncodeResultRepositoryClient
import com.websarva.wings.android.gamecheatxor.repository.HttpConnectRepositoryClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val httpConnectRepository: HttpConnectRepositoryClient,
    private val encodeResultRepository: EncodeResultRepositoryClient,
    application: Application
) : AndroidViewModel(application) {
    private val _result = MutableLiveData<Boolean>()
    val result: LiveData<Boolean> = Transformations.map(_result){
        it
    }

    fun connect(result: Result){
        httpConnectRepository.connect(encodeResultRepository.encode(result)){
            _result.value = it
        }
    }
}