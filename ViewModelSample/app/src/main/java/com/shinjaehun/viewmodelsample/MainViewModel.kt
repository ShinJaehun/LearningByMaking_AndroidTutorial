package com.shinjaehun.viewmodelsample

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

const val TAG = "MainViewModel"
class MainViewModel() : ViewModel() {

    // version 1 : 얘도 정상적으로 자료를 유지함
    var score: Int = 0

    init {
        score = 0
    }
    fun scorePlus() {
        score++
    }
    fun scoreMinus() {
        score--
    }

    // version 2, version 3
//    private val _score = MutableLiveData<Int>()
//
//    val score: LiveData<Int>
//        get() = _score
//
//    init {
//        Log.i(TAG, "MainViewModel 생성")
//        _score.value = 0
//    }
//
//    fun scorePlus() {
//        _score.value = _score.value?.plus(1)
//    }
//
//    fun scoreMinus() {
//        _score.value = _score.value?.minus(1)
//    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "MainViewModel 종료")
        // 앱이 종료되더라도(view가 비활성상태일지라도) viewModel은 존재할 수 있음...
    }

}