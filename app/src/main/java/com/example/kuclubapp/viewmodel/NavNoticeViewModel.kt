package com.example.kuclubapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kuclubapp.firebaseDB.Notice
import kotlinx.coroutines.launch

class NoticeViewModelFactory(private val repository: NoticeRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NavNoticeViewModel::class.java)) {
            return NavNoticeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class NavNoticeViewModel(private val repository: NoticeRepository): ViewModel() {
    private val _notices = MutableLiveData<List<Notice>>()
    val notices: LiveData<List<Notice>> = _notices

    private val _noticeDetail = MutableLiveData<Notice?>()
    val noticeDetail: LiveData<Notice?> = _noticeDetail

    fun insertNotice(notice: Notice, context: Context){
        viewModelScope.launch {
            repository.insertNotice(notice, context)
        }
    }
    fun deleteNotice(notice: Notice){
        viewModelScope.launch {
            repository.deleteNotice(notice)
        }
    }

    fun getAllNotices() {
        viewModelScope.launch {
            repository.getAllNotices { notices ->
                _notices.value = notices
            }
        }
    }

    fun getNoticeDetail(noticeNum: Int) {
        viewModelScope.launch {
            repository.getNoticeDetail(noticeNum) { notice ->
                _noticeDetail.value = notice
            }
        }
    }
}