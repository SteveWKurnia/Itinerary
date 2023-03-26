//package com.wkitinerary.di
//
//import com.wkitinerary.domain.HomeRepository
//import com.wkitinerary.domain.usecase.AddTripUseCase
//import dagger.Binds
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.components.ActivityComponent
//
//@Module
//@InstallIn(ActivityComponent::class)
//class UseCaseModule {
//
//    @Provides
//    fun provideAddTripUseCase(
//        homeRepository: HomeRepository
//    ): AddTripUseCase {
//        return AddTripUseCase(homeRepository)
//    }
//}
