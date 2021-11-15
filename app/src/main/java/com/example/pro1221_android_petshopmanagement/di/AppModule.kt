package com.example.pro1221_android_petshopmanagement.di

import android.app.Application
import androidx.room.Room
import com.example.pro1221_android_petshopmanagement.data.data_source.AppDatabase
import com.example.pro1221_android_petshopmanagement.data.repository.AnimalRepositoryImpl
import com.example.pro1221_android_petshopmanagement.data.repository.CustomerRepositoryImpl
import com.example.pro1221_android_petshopmanagement.data.repository.PetRepositoryImpl
import com.example.pro1221_android_petshopmanagement.domain.repository.AnimalRepository
import com.example.pro1221_android_petshopmanagement.domain.repository.CustomerRepository
import com.example.pro1221_android_petshopmanagement.domain.repository.PetRepository
import com.example.pro1221_android_petshopmanagement.domain.use_case.AnimalUseCases
import com.example.pro1221_android_petshopmanagement.domain.use_case.CustomerUseCase
import com.example.pro1221_android_petshopmanagement.domain.use_case.PetUseCases
import com.example.pro1221_android_petshopmanagement.domain.use_case.animal.*
import com.example.pro1221_android_petshopmanagement.domain.use_case.customer.AddCustomer
import com.example.pro1221_android_petshopmanagement.domain.use_case.customer.DeleteCustomer
import com.example.pro1221_android_petshopmanagement.domain.use_case.customer.GetCustomer
import com.example.pro1221_android_petshopmanagement.domain.use_case.customer.GetCustomers
import com.example.pro1221_android_petshopmanagement.domain.use_case.pet.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        AppDatabase.DB_NAME
    ).build()

    // provides repos
    @Provides
    @Singleton
    fun provideAnimalRepository(appDatabase: AppDatabase): AnimalRepository = AnimalRepositoryImpl(
        animalDao = appDatabase.animalDao
    )

    @Provides
    @Singleton
    fun providePetRepository(appDatabase: AppDatabase): PetRepository = PetRepositoryImpl(
        petDao = appDatabase.petDao
    )

    @Provides
    @Singleton
    fun provideCustomerRepository(appDatabase: AppDatabase): CustomerRepository =
        CustomerRepositoryImpl(customerDao = appDatabase.customerDao)

    // provides use cases
    @Provides
    @Singleton
    fun provideAnimalUseCase(animalRepository: AnimalRepository): AnimalUseCases = AnimalUseCases(
        addAnimal = AddAnimal(animalRepository = animalRepository),
        deleteAnimal = DeleteAnimal(animalRepository = animalRepository),
        getAnimalById = GetAnimalById(animalRepository = animalRepository),
        getAnimals = GetAnimals(animalRepository = animalRepository),
        updateAnimal = UpdateAnimal(animalRepository = animalRepository)
    )

    @Provides
    @Singleton
    fun providePetUseCase(petRepository: PetRepository): PetUseCases = PetUseCases(
        addPet = AddPet(petRepository = petRepository),
        deletePet = DeletePet(petRepository = petRepository),
        getPetById = GetPetById(petRepository = petRepository),
        getPets = GetPets(petRepository = petRepository),
        updatePet = UpdatePet(petRepository = petRepository),
        soldPet = SetPetSold(petRepository = petRepository),
        updatePetTime = UpdatePetTime(petRepository = petRepository),
        restorePet = RestorePet(petRepository = petRepository)
    )

    @Provides
    @Singleton
    fun provideCustomerUseCase(customerRepository: CustomerRepository):CustomerUseCase = CustomerUseCase(
        addCustomer = AddCustomer(customerRepository = customerRepository),
        deleteCustomer = DeleteCustomer(customerRepository = customerRepository),
        getCustomer = GetCustomer(customerRepository = customerRepository),
        getCustomers = GetCustomers(customerRepository = customerRepository)
    )


}