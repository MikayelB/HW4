package com.example.cities_mobile_dubl2.data

import com.example.cities_mobile_dubl2.constants.MADRID_DESCRIPTION
import com.example.cities_mobile_dubl2.constants.MADRID_IMAGE_RES
import com.example.cities_mobile_dubl2.constants.WASHINGTON_DESCRIPTION
import com.example.cities_mobile_dubl2.constants.WASHINGTON_IMAGE_RES
import com.example.cities_mobile_dubl2.constants.YEREVAN_DESCRIPTION
import com.example.cities_mobile_dubl2.constants.YEREVAN_IMAGE_RES
import com.example.cities_mobile_dubl2.model.City


val cities = listOf(
    City(
        "Yerevan",
        YEREVAN_DESCRIPTION,
        YEREVAN_IMAGE_RES
    ),
    City(
        "Washington",
        WASHINGTON_DESCRIPTION,
        WASHINGTON_IMAGE_RES
    ),
    City(
        "Madrid",
        MADRID_DESCRIPTION,
        MADRID_IMAGE_RES
    )
)
