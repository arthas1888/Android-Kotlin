package co.edu.aulamatriz.listaapplication

import co.edu.aulamatriz.listaapplication.models.Country

interface CountryAdapterListener {
    fun onDeleteCountryClick(item: Country, pos: Int)
    fun onUpdateCountryClick(item: Country, pos: Int)
}