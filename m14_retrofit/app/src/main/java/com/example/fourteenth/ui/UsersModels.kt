package com.example.fourteenth.ui

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UsersModels(
    @Json(name = "results") val results: List<Result>
)

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "gender") val gender: String,
    @Json(name = "name") val name: Name,
    @Json(name = "location") val location: Location,
    @Json(name = "picture") val picture: Picture,
    @Json(name = "email") val email: String,
    @Json(name = "phone") val phone: String
)

@JsonClass(generateAdapter = true)
data class Picture(
    @Json(name = "large") val large: String,
    @Json(name = "medium") val medium: String,
    @Json(name = "thumbnail") val thumbnail: String
)

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "street") val street: Street,
    @Json(name = "city") val city: String,
    @Json(name = "state") val state: String,
    @Json(name = "country") val country: String,
)

@JsonClass(generateAdapter = true)
data class Street(
    @Json(name = "number") val number: Int,
    @Json(name = "name") val name: String
)

@JsonClass(generateAdapter = true)
data class Name(
    @Json(name = "first") val first: String,
    @Json(name = "last") val last: String,
    @Json(name = "title") val title: String
)
