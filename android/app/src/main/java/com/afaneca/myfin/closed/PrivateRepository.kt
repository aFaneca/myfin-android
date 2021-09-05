package com.afaneca.myfin.closed

import androidx.lifecycle.LiveData
import com.afaneca.myfin.data.UserDataManager
import com.afaneca.myfin.data.db.MyFinDatabase
import com.afaneca.myfin.data.db.accounts.UserAccountEntity
import com.afaneca.myfin.data.network.BaseRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by me on 14/06/2021
 */

class PrivateRepository
@Inject
constructor(
    private val db: MyFinDatabase,
    private var userData: UserDataManager
) : BaseRepository() {

    // CRUD
    fun insertAccount(userAccountObj: UserAccountEntity) {
        GlobalScope.launch(IO) {
            db.userAccountsDao()
                .insert(userAccountObj)
        }
    }

    fun insertAccounts(userAccountObj: List<UserAccountEntity>) {
        GlobalScope.launch(IO) {
            db.userAccountsDao()
                .insertAll(userAccountObj)
        }
    }

    fun getAllUserAccounts(): LiveData<List<UserAccountEntity>> = db.userAccountsDao().getAll()

    fun deleteUserAccount(userAccountObj: UserAccountEntity) =
        db.userAccountsDao().delete(userAccountObj)

    fun deleteAllUserAccounts() = db.userAccountsDao().deleteAll()
    fun clearUserSessionData() {
        userData.clearUserSessionData()
        db.clearAllTables()
    }
}