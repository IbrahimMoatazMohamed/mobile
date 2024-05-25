@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.doctsys.ui.screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.doctsys.R
import com.example.doctsys.model.Disease
import com.example.doctsys.model.Schedule
import com.example.doctsys.model.ScheduleList
import com.example.doctsys.ui.screen.navigation.DocBottomNavBar
import com.example.doctsys.ui.screen.navigation.NavigationDestination
import java.text.SimpleDateFormat
import java.time.LocalDate

object PatientsDestination : NavigationDestination {
    override val route = "patients"
    override val title = "Patients"
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PatientScreenNavigation(
    navigateToProfile: (Int)->Unit,
    navigateToSchedules: ()->Unit,
    profileId: Int
) {
    Scaffold(
        floatingActionButton = { FloatingActionButton() },
        bottomBar = { DocBottomNavBar(navigateToProfile, navigateToSchedules, {},profileId) }
    ) {
        PatientsScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PatientsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(ScheduleList.scheduleList) { schedule ->
                PatientCard(schedule = schedule)
            }
        }
    }
}

@Composable
fun PatientCard(schedule: Schedule) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .width(350.dp)
            .height(110.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 10.dp)
        ) {
            Image(
                painter = painterResource(id = getScheduleImage(schedule.disease)),
                contentDescription = "schedule image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(7.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(text = schedule.patientName, fontSize = 18.sp)

                        Text(text = schedule.disease.value, fontSize = 13.sp)

                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = schedule.description, fontSize = 15.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text(text = schedule.date, fontSize = 16.sp)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FloatingActionButton() {
    var showDialog by remember { mutableStateOf(false) }

    SmallFloatingActionButton(
        onClick = { showDialog = true },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.secondary
    ) {
        Icon(Icons.Filled.Add, "Small floating action button.")
    }

    if (showDialog) {
        PatientDialog(onDismiss = { showDialog = false })
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Enter Details")

                Spacer(modifier = Modifier.height(8.dp))


                var patientName by remember { mutableStateOf("") }
                var patientSurName by remember { mutableStateOf("") }
                var DOB by remember { mutableStateOf("") }
                var email by remember { mutableStateOf("") }
                var phoneNumber by remember { mutableStateOf("") }
                var description by remember { mutableStateOf("") }


                //design values
                val shape = RoundedCornerShape(10.dp)

                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        value = patientName,
                        onValueChange = {
                            patientName = it
                        },
                        shape = shape,
                        label = { Text("Name") },
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        shape = shape,
                        value = patientSurName,
                        onValueChange = {
                            patientSurName = it
                        },
                        label = { Text("Surname") },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    shape = shape,
                    value = phoneNumber,
                    onValueChange = {
                        phoneNumber = it
                    },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    shape = shape,
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                )

                var openCalendar by remember { mutableStateOf(false) }
                val state = rememberDatePickerState()

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    shape = shape,
                    value = DOB,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Date Of Birth") },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(
                            onClick = { openCalendar = true }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Select Date")
                        }
                    },

                    )
                if (openCalendar) {
                    DatePickerDialog(
                        onDismissRequest = { openCalendar = false },
                        confirmButton = {
                            Button(onClick = {
                                openCalendar = false

                                val dateString =
                                    SimpleDateFormat("yyyy-MM-dd").format(state.selectedDateMillis)
                                val selectedDate = LocalDate.parse(dateString)
                                DOB =
                                    "${selectedDate.year}-${selectedDate.month}-${selectedDate.dayOfMonth}"
                            }

                            ) {
                                Text(text = "Confirm")
                            }
                        }) {
                        DatePicker(
                            state = state
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    shape = shape,
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { onDismiss() }) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        onDismiss()
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}


//fun getScheduleImagee(disease: Disease): Int {
//    return when (disease) {
//        Disease.BACKPAIN -> R.drawable.what_to_do_back_pain_1200x628
//        Disease.TEETHPAIN -> R.drawable.toothache_scaled
//        Disease.ARMPAIN -> R.drawable.shoulder_pain_495x400
//        Disease.LEGPAIN -> R.drawable.sciatica
//        else -> R.drawable.sciatica
//    }
//}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PatientsScreenPreview() {
    PatientsScreen()
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PatientScreenNavigationPreview(){
    PatientScreenNavigation({},{},1)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PatientDialogPreview() {
    PatientDialog {}
}