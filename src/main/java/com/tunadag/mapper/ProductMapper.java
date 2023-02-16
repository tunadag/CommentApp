package com.tunadag.mapper;

import com.tunadag.dto.request.ProductCreateRequestDto;
import com.tunadag.dto.response.ProductCreateResponseDto;
import com.tunadag.repository.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    ProductCreateResponseDto toProductCreateResponseDto(final Product product);
    Product toProduct(final ProductCreateRequestDto requestDto);
}
