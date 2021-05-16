package com.godeltech.mastery.backend.service

import org.springframework.web.multipart.MultipartFile

interface AwsService {

    fun uploadImage(photo: MultipartFile, carId: Long): String
}