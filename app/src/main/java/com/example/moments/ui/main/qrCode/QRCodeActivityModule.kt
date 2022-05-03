package com.example.moments.ui.main.qrCode

import dagger.Module
import dagger.Provides

@Module
class QRCodeActivityModule {

    @Provides
    internal fun provideQRCodeInteractor(qrCodeActivityInteractor: QRCodeActivityInteractor): IQRCodeActivityInteractor =
        qrCodeActivityInteractor

    @Provides
    internal fun provideQRCodePresenter(qrCodeActivityPresenter: QRCodeActivityPresenter<IQRCodeActivityView, IQRCodeActivityInteractor>): IQRCodeActivityPresenter<IQRCodeActivityView, IQRCodeActivityInteractor> =
        qrCodeActivityPresenter
}