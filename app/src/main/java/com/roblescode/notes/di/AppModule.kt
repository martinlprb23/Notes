package com.roblescode.notes.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.roblescode.notes.data.auth.AuthRepository
import com.roblescode.notes.data.auth.AuthRepositoryImpl
import com.roblescode.notes.data.firestore.repository.NotesRepository
import com.roblescode.notes.data.firestore.repository.NotesRepositoryImpl
import com.roblescode.notes.data.firestore.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    // Authentication ---
    @Provides
    fun provideFirebaseAuth() : FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun providesAuthRepository(impl: AuthRepositoryImpl) : AuthRepository = impl

    // Firestore ----

    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideNotesRef(
        db: FirebaseFirestore
    ) = db.collection("notes-app")

    @Provides
    fun provideNotesRepository(
        notesRef: CollectionReference
    ): NotesRepository = NotesRepositoryImpl(notesRef)

   @Provides
   fun provideUseCases(
       repo: NotesRepository
   ) = UseCases(
       getNotes = GetNotes(repo),
       getNoteById = GetNoteById(repo),
       addNote = AddNote(repo),
       updateNote = UpdateNote(repo),
       deleteNote = DeleteNote(repo)
   )



}