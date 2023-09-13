package com.example.persona.ui.theme.Persona

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.persona.data.Repository.PersonaRepository
import com.example.persona.data.local.entity.PersonaEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonaViewModel @Inject constructor(
    private val PersonaRepository: PersonaRepository
) : ViewModel()  {
    var nombre by mutableStateOf("")
    var nombreError by mutableStateOf(false)

    var telefono by mutableStateOf("")
    var telefonoError by mutableStateOf(false)

    var celular by mutableStateOf("")
    var celularError by mutableStateOf(false)

    var email by mutableStateOf("")
    var emailError by mutableStateOf(false)

    var fechaNac by mutableStateOf("")
    var fechaNacError by mutableStateOf(false)

    var ocupacion by mutableStateOf("")
    var ocupacionError by mutableStateOf(false)

    private val _isMessageShown = MutableSharedFlow<Boolean>()
    val isMessageShownFlow = _isMessageShown.asSharedFlow()

    fun setMessageShown() {
        viewModelScope.launch {
            _isMessageShown.emit(true)
        }
    }
    fun onNombreChanged(valor: String) {
        nombre = valor
        nombreError = valor.isBlank()|| valor.length < 3
    }
    fun onTelefonoChanged(valor: String) {
        telefono= valor
        telefonoError = valor.isBlank()
    }
    fun validarCampos(): Boolean {
        nombreError = nombre.isBlank() || nombre.length < 3
        telefonoError = telefono.isBlank() || !telefono.matches("^\\d{11}\$".toRegex())

        return !(nombreError || telefonoError  || celularError|| emailError||fechaNacError)
    }
    fun oncelularChanged(valor: String) {
        celular= valor
        celularError = valor.isBlank()|| !valor.matches("^\\d{10}\$".toRegex())
    }
    fun onEmailChanged(valor: String) {
        email= valor
        emailError = valor.isBlank()|| !valor.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$".toRegex())
    }

    fun onfechaNacChanged(valor: String) {
        fechaNac= valor
        fechaNacError = valor.isBlank()||!valor.matches("^\\d{2}/\\d{2}/\\d{4}\$".toRegex())
    }

    fun onOcupacionChanged(valor: String) {

        ocupacion= valor
        ocupacionError = valor.isBlank()
    }


    val Personas: StateFlow<List<PersonaEntity>> = PersonaRepository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
    fun savePersona() {
        if (validarCampos()) {
            viewModelScope.launch {
                val persona = PersonaEntity(
                    Nombre = nombre,
                    Telefono = telefono,
                    Celular = celular,
                    Email = email,
                    FechaNacimiento = fechaNac,
                    Ocupacion = ocupacion
                )
                PersonaRepository.save(persona)
                limpiar()
            }
        }
    }

    private fun limpiar() {
        nombre=""
        telefono=""
        celular=""
        email=""
        fechaNac=""
        ocupacion=""
    }

    fun deletePersona(persona: PersonaEntity)
    {
        viewModelScope.launch {

            PersonaRepository.delete(persona)
            limpiar()
        }
    }





}