package com.godeltech.mastery.backend.rest

import com.godeltech.mastery.backend.domain.dto.responce.BrandDto
import com.godeltech.mastery.backend.domain.dto.responce.ModelDto
import com.godeltech.mastery.backend.service.BrandService
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.Min

@RestController
@RequestMapping("/api/v1/brands")
class BrandController(private val brandService: BrandService) {

    @GetMapping
    fun getAllBrands(): ResponseEntity<List<BrandDto>> = ok(brandService.getAllBrands())

    @GetMapping("/{brand_id}/models")
    fun getAllModelById(@PathVariable(name = "brand_id") @Min(1) id: Long): ResponseEntity<List<ModelDto>> =
        ok(brandService.getModelsByBrandId(id))
}