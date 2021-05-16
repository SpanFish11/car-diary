package com.godeltech.mastery.backend.service.impl

import com.godeltech.mastery.backend.domain.dto.responce.BrandDto
import com.godeltech.mastery.backend.domain.dto.responce.ModelDto
import com.godeltech.mastery.backend.domain.entity.Brand
import com.godeltech.mastery.backend.domain.entity.Model
import com.godeltech.mastery.backend.exception.EntityNotFoundException
import com.godeltech.mastery.backend.repository.BrandRepository
import com.godeltech.mastery.backend.service.BrandService
import org.springframework.stereotype.Service

@Service
class BrandServiceImpl(private val brandRepository: BrandRepository) : BrandService {

    override fun getById(id: Long): Brand =
        brandRepository.findById(id).orElseThrow { EntityNotFoundException("brand", id) }

    override fun getAllBrands(): MutableList<BrandDto> =
        brandRepository.findAll().map(Brand::toDto).toMutableList()

    override fun getModelsByBrandId(id: Long): MutableList<ModelDto> =
        getById(id).models?.map(Model::toDto)?.toMutableList() ?: mutableListOf()
}