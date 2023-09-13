package com.example.moodtrackr.models

import java.time.LocalDate

class Profile(
    var name: String = "user",
    var surname: String = "",
    var sex: String = "M",
    var birthday: LocalDate = LocalDate.now()
)