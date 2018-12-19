package com.glovo.glovo.selectlocation.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.glovo.glovo.R
import com.glovo.glovo.base.view.MvpActivity
import com.glovo.glovo.map.data.dto.City
import com.glovo.glovo.map.data.dto.Country
import com.glovo.glovo.selectlocation.domain.dto.Locations
import com.glovo.glovo.selectlocation.presenter.SelectLocationPresenter
import kotlinx.android.synthetic.main.activity_select_location.*
import kotlinx.android.synthetic.main.include_select_location.*
import kotlinx.android.synthetic.main.include_select_location_error.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import android.app.Activity


const val SELECT_LOCATION_ACTIVITY_RESULT_CODE = 12
const val CITY_CODE_ARG = "city_code"


class SelectLocationActivity : MvpActivity<SelectLocationView, SelectLocationPresenter>(), SelectLocationView {


    private var countriesAdapter: ArrayAdapter<Country>? = null
    private var citiesAdapter: ArrayAdapter<City>? = null
    private var selectedCityCode: String? = null
    private var filteredCities: List<City>? = null

    override val presenter: SelectLocationPresenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_location)
        presenter.getCountriesAndCities()


        countriesAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, ArrayList()
        )
        countriesSpinner.adapter = countriesAdapter


        citiesAdapter = ArrayAdapter(
            this@SelectLocationActivity,
            android.R.layout.simple_spinner_item, ArrayList()
        )
        citiesSpinner.adapter = citiesAdapter


        selectButton.setOnClickListener {
            val intent = Intent()


            intent.putExtra(CITY_CODE_ARG, filteredCities?.get(citiesSpinner.selectedItemPosition)?.code)
            setResult(Activity.RESULT_OK, intent)
            finish()

        }

    }


    override fun showLoading() {
        selectLocationInclude.visibility = View.GONE
        selectLocationErrorInclude.visibility = View.VISIBLE

    }

    override fun hideLoading() {

        selectLocationProgressBar.visibility = View.INVISIBLE
    }


    override fun showError(error: String) {

        errorLoadingTextView.text = error

    }


    override fun showCountriesCities(locations: Locations) {


        selectLocationInclude.visibility = View.VISIBLE
        selectLocationErrorInclude.visibility = View.GONE

        countriesAdapter?.addAll(locations.counties)



        countriesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {


            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filteredCities = locations.cities.filter {
                    locations.counties[position].code == it.countryCode
                }

                citiesAdapter?.clear()
                citiesAdapter?.addAll(filteredCities)
                citiesSpinner.setSelection(0)

            }

        }
        citiesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {


            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCityCode = filteredCities?.get(position)?.code

            }

        }


    }

    override fun onBackPressed() {

        val intent = Intent()
        setResult(Activity.RESULT_CANCELED, intent)
        super.onBackPressed()


    }


}
