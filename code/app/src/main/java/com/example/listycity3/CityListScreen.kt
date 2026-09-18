package com.example.listycity3

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.clickable



@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit, // Takes a city object, returns nothing
    onUpdateCity: (City, City) -> Unit,
    modifier: Modifier = Modifier
) {
    var newCityName by remember({ mutableStateOf<String>("") }) // long form, the last (/ only) param is a lambda,
    // so we can just drop the parentheses and move the whole declaration outside, kotlin also infers types so we can drop the <String>, the "" is enough of a clue
    var newProvinceName by remember { mutableStateOf("") }
    var showAddCityFields by remember { mutableStateOf(false) }
    var selectedCity by remember { mutableStateOf<City?>(null) }
    var editCityName by remember { mutableStateOf("") }
    var editProvinceName by remember { mutableStateOf("") }


    Column(modifier = modifier.fillMaxSize()) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    showAddCityFields = !showAddCityFields
                }
            ) {
                Text("+")
            }
        }
        if (showAddCityFields) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField( // City field
                    value = newCityName,
                    onValueChange = { newCityName = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField( // Province field
                    value = newProvinceName,
                    onValueChange = { newProvinceName = it },
                    label = { Text("Province") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))
                Button( // Add city
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        if (newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
                            onAddCity(
                                City(
                                    name = newCityName,
                                    province = newProvinceName
                                )
                            )
                            newCityName = ""
                            newProvinceName = ""
                            showAddCityFields = false

                        }
                    }
                ) {
                    Text("Add City")
                }


            }
        }
        if (selectedCity != null) {
            Row( // UI design implemented with assistance from Claude (Anthropic), 2026-09-18
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                OutlinedTextField(
                    value = editCityName,
                    onValueChange = { editCityName = it },
                    label = { Text("Edit City") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = editProvinceName,
                    onValueChange = { editProvinceName = it },
                    label = { Text("Edit Province") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        if (editCityName.isNotBlank() && editProvinceName.isNotBlank()) {
                            onUpdateCity(
                                selectedCity!!, // never null, alr inside null check
                                City(name = editCityName, province = editProvinceName)
                            )
                            selectedCity = null
                        }
                    }
                ) {
                    Text("Save")
                }
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(cities) { index, city ->
                    CityRow(
                        city = city,
                        onClick = {
                            selectedCity = city
                            editCityName = city.name
                            editProvinceName = city.province
                        }
                    )
                    if (index < cities.lastIndex) {
                        HorizontalDivider()
                    }
                }
        }
    }
}

    @Composable
    fun CityRow(city: City, onClick: () -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                text = city.name,
                fontSize = 30.sp,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = city.province,
                fontSize = 30.sp,
                modifier = Modifier.weight(1f)
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun CityListScreenPreview() {
        ListyCity3Theme {
            CityListScreen(
                cities = listOf(
                    City("Edmonton", "AB"),
                    City("Vancouver", "BC"),
                    City("Calgary", "AB")
                ),
                onAddCity = {},
                onUpdateCity = {_, _ ->}, // Implemented with assistance from Claude (Anthropic), 2026-09-18
            )
        }
    }

