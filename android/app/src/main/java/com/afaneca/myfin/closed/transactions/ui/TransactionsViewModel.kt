package com.afaneca.myfin.closed.transactions.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afaneca.myfin.base.objects.MyFinTransaction
import com.afaneca.myfin.closed.transactions.data.LatestTransactionsListResponse
import com.afaneca.myfin.closed.transactions.data.TransactionsRepository
import com.afaneca.myfin.data.network.Resource
import com.afaneca.myfin.utils.SingleLiveEvent
import kotlinx.coroutines.launch

/**
 * Created by me on 20/06/2021
 */
class TransactionsViewModel(
    private val repository: TransactionsRepository
) : ViewModel(), TransactionsListAdapter.TransactionsListItemClickListener {

    private val _transactionsListData: SingleLiveEvent<Resource<LatestTransactionsListResponse>> =
        SingleLiveEvent()

    fun getTransactionsListData() = _transactionsListData

    private val _transactionsListDataset: MutableLiveData<List<MyFinTransaction>> =
        MutableLiveData()
    val transactionsListDataset = _transactionsListDataset

    private val _clickedTransactionDetails: MutableLiveData<MyFinTransaction> = MutableLiveData()
    val clickedTransactionDetails = _clickedTransactionDetails


    fun requestTransactionsList(trxLimit: Int = 50) = viewModelScope.launch {
        _transactionsListData.value =
            repository.getTransactionsList(trxLimit)
        if (_transactionsListData.value is Resource.Success<LatestTransactionsListResponse>) {
            _transactionsListDataset.postValue(
                (_transactionsListData.value as Resource.Success<LatestTransactionsListResponse>).data
            )
        }
    }

    override fun onTransactionClick(trx: MyFinTransaction) {
        _clickedTransactionDetails.postValue(trx)
    }

}