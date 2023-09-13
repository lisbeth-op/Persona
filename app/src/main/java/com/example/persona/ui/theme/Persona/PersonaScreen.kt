package com.example.persona.ui.theme.Persona

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.persona.R
import com.example.persona.data.local.entity.PersonaEntity
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonasScreen(
    viewModel: PersonaViewModel = hiltViewModel()
){
    val personas by viewModel.Personas.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.isMessageShownFlow.collectLatest {
            if (it) {
                snackbarHostState.showSnackbar(
                    message = "Persona Guardada Correctamente",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Registro de Personas") }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                item {

                    AddPersonas(viewModel)
                }
                items(personas) { persona ->
                    PersonaCard(
                        Modifier.padding(8.dp),
                        persona,
                        viewModel

                    )


                }
            }
        }


    }






}
@Composable
fun AddPersonas(
    viewModel: PersonaViewModel
){
    Card {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                ExpandedContent(viewModel = viewModel)
            }
        }
    }
}



@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)


@Composable
fun ExpandedContent(viewModel: PersonaViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Nombre") },
            singleLine = true,
            maxLines=1,
            value = viewModel.nombre,
            onValueChange = viewModel::onNombreChanged,
            leadingIcon ={
                Icon(imageVector = Icons.Filled.Person, contentDescription = "person icon")
            },
            isError = viewModel.nombreError,
            trailingIcon = {
                AnimatedVisibility(
                    visible = viewModel.nombreError,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = "")
                }
            } ,
            keyboardOptions = KeyboardOptions(
                keyboardType =  KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            ),
            keyboardActions = KeyboardActions{
                focusManager.moveFocus(FocusDirection.Next)
            }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Telefono") },
            singleLine = true,
            maxLines=1,
            value = viewModel.telefono,
            onValueChange = viewModel::onTelefonoChanged,
            leadingIcon ={
                Icon(imageVector = Icons.Filled.Call, contentDescription = "phone icon")
            },
            isError = viewModel.telefonoError,
            trailingIcon = {
                AnimatedVisibility(
                    visible = viewModel.telefonoError,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = "")
                }
            } ,
            keyboardOptions = KeyboardOptions(
                keyboardType =  KeyboardType.Phone,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            ),
            keyboardActions = KeyboardActions{
                focusManager.moveFocus(FocusDirection.Next)
            }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Celular") },
            singleLine = true,
            maxLines=1,
            value = viewModel.celular,
            onValueChange = viewModel::oncelularChanged,
            leadingIcon ={
                Icon(imageVector = Icons.Filled.Call, contentDescription = "phone icon")
            },
            isError = viewModel.celularError,
            trailingIcon = {
                AnimatedVisibility(
                    visible = viewModel.celularError,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = "")
                }
            } ,
            keyboardOptions = KeyboardOptions(
                keyboardType =  KeyboardType.Phone,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            ),
            keyboardActions = KeyboardActions{
                focusManager.moveFocus(FocusDirection.Next)
            }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Email") },
            singleLine = true,
            maxLines=1,
            value = viewModel.email,
            onValueChange = viewModel::onEmailChanged,
            leadingIcon ={
                Icon(imageVector = Icons.Filled.Email, contentDescription = "email icon")
            },
            isError = viewModel.emailError,
            trailingIcon = {
                AnimatedVisibility(
                    visible = viewModel.emailError,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = "")
                }
            } ,
            keyboardOptions = KeyboardOptions(
                keyboardType =  KeyboardType.Email,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            ),
            keyboardActions = KeyboardActions{
                focusManager.moveFocus(FocusDirection.Next)
            }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Fecha Nacimiento") },
            singleLine = true,
            maxLines=1,
            value = viewModel.fechaNac,
            onValueChange = viewModel::onfechaNacChanged,
            leadingIcon ={
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "email icon")
            },
            isError = viewModel.fechaNacError,
            trailingIcon = {
                AnimatedVisibility(
                    visible = viewModel.fechaNacError,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = "")
                }
            } ,
            keyboardOptions = KeyboardOptions(
                keyboardType =  KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            ),
            keyboardActions = KeyboardActions{
                focusManager.moveFocus(FocusDirection.Next)
            }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Ocupacion") },
            singleLine = true,
            maxLines=1,
            value = viewModel.ocupacion,
            onValueChange = viewModel::onOcupacionChanged,
            leadingIcon ={
                Icon(imageVector = Icons.Filled.Build, contentDescription = "email icon")
            },
            isError = viewModel.ocupacionError,
            trailingIcon = {
                AnimatedVisibility(
                    visible = viewModel.ocupacionError,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = "")
                }
            } ,
            keyboardOptions = KeyboardOptions(
                keyboardType =  KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            ),
            keyboardActions = KeyboardActions{
                focusManager.moveFocus(FocusDirection.Down)
                keyboardController?.hide()
            }
        )
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                keyboardController?.hide()
                viewModel.savePersona()
                viewModel.setMessageShown()
            })
        {
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Guardar")
            Text(text = "Guardar")
        }


    }
}


@Composable
fun PersonaCard(
    modifier: Modifier,
    persona: PersonaEntity,
    viewModel: PersonaViewModel
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = persona.Nombre,
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Spacer(Modifier.weight(1f))
                ItemButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded },
                )
            }
            if (expanded) {
                PersonaInfo(
                        persona, modifier = Modifier.padding(
                            start = 16.dp,
                            top = 8.dp,
                            bottom = 16.dp,
                            end = 16.dp
                        ),
                    viewModel
                )
            }

        }
    }




}
@Composable
fun PersonaInfo(
    persona: PersonaEntity,
    modifier: Modifier = Modifier,
    viewModel: PersonaViewModel
) {
    Column(
        modifier = modifier
    ) {
        Row() {
            Text(
                text = stringResource(R.string.Telefono),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = persona.Telefono,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(Modifier.height(2.dp))
        Row() {
            Text(
                text = stringResource(R.string.Celular),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = persona.Celular,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(Modifier.height(2.dp))

        Row() {
            Text(
                text = stringResource(R.string.Email),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = persona.Email,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(Modifier.height(2.dp))

        Row() {
            Text(
                text = stringResource(R.string.FechaNacimiento),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = persona.FechaNacimiento,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Row() {
            Text(
                text = stringResource(R.string.Ocupacion),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = persona.Ocupacion,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(Modifier.weight(2f))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                viewModel.deletePersona(persona)

            }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "guardar")
                Text(text = "Eliminar")

            }

        }

    }
}
@Composable
private fun ItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}