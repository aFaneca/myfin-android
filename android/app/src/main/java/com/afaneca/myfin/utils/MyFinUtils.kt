package com.afaneca.myfin.utils

import com.afaneca.myfin.data.db.accounts.UserAccountEntity

/**
 * Created by me on 19/06/2021
 */
object MyFinUtils {

    /**
     * Returns a sublist of the [originalList] containing only operating funds accounts
     */
    fun getOnlyOperatingFundsAccountsFromFullList(originalList: List<UserAccountEntity>): List<UserAccountEntity> =
        originalList.filter { acc ->
            !acc.type.isNullOrBlank() && when (acc.type) {
                MyFinConstants.ACCOUNT_TYPE.CHECKING.value,
                MyFinConstants.ACCOUNT_TYPE.SAVINGS.value,
                MyFinConstants.ACCOUNT_TYPE.MEAL.value,
                MyFinConstants.ACCOUNT_TYPE.WALLET.value
                -> true
                else -> false
            }
        }

    /**
     * Returns a sublist of the [originalList] containing only investing accounts
     */
    fun getOnlyInvestingAccountsFromFullList(originalList: List<UserAccountEntity>): List<UserAccountEntity> =
        originalList.filter { acc ->
            !acc.type.isNullOrBlank() && when (acc.type) {
                MyFinConstants.ACCOUNT_TYPE.INVESTING.value,
                -> true
                else -> false
            }
        }

    /**
     * Returns a sublist of the [originalList] containing only credit accounts
     */
    fun getOnlyCreditAccountsFromFullList(originalList: List<UserAccountEntity>): List<UserAccountEntity> =
        originalList.filter { acc ->
            !acc.type.isNullOrBlank() && when (acc.type) {
                MyFinConstants.ACCOUNT_TYPE.CREDIT.value,
                -> true
                else -> false
            }
        }


}