package io.github.feelfreelinux.wykopmobilny.ui.modules.notificationslist.notification

import io.github.feelfreelinux.wykopmobilny.api.notifications.NotificationsApi
import io.github.feelfreelinux.wykopmobilny.base.BasePresenter
import io.github.feelfreelinux.wykopmobilny.base.Schedulers
import io.github.feelfreelinux.wykopmobilny.ui.modules.notificationslist.NotificationsListView

class NotificationsListPresenter(
    val schedulers: Schedulers,
    val notificationsApi: NotificationsApi
) : BasePresenter<NotificationsListView>() {

    var page = 1

    fun loadData(shouldRefresh: Boolean) {
        if (shouldRefresh) page = 1
        compositeObservable.add(
            notificationsApi.getNotifications(page)
                .subscribeOn(schedulers.backgroundThread())
                .observeOn(schedulers.mainThread())
                .subscribe({
                    if (it.isNotEmpty()) {
                        page++
                        view?.addNotifications(it, shouldRefresh)
                    } else view?.disableLoading()
                }, { view?.showErrorDialog(it) })
        )
    }

    fun readNotifications() {
        compositeObservable.add(
            notificationsApi.readNotifications()
                .subscribeOn(schedulers.backgroundThread())
                .observeOn(schedulers.mainThread())
                .subscribe({ view?.showReadToast() }, { view?.showErrorDialog(it) })
        )
    }
}