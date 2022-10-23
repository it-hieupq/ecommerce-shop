package com.capstone.client.product.domain.service;

import com.capstone.client.product.domain.helper.ImportFileHelper;
import com.capstone.client.product.domain.model.dto.request.ImageReqDTO;
import com.capstone.client.product.domain.model.dto.request.ProductReqDTO;
import com.capstone.client.product.domain.model.dto.response.ProductDTO;
import com.capstone.client.product.domain.model.entity.Category;
import com.capstone.client.product.domain.model.entity.filter.ProductFilter;
import com.capstone.client.product.domain.model.entity.Image;
import com.capstone.client.product.domain.model.entity.Product;
import com.capstone.client.product.domain.repository.ProductRepo;
import com.capstone.client.product.domain.repository.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;



    public boolean isExisted(Integer id){
        return productRepo.existsByProductIdAndActive(id, true);
    }

    public ProductDTO getProductById(Integer productId, Boolean isAdmin) {
        Product product = productRepo.findByProductId(productId);
        return toDTO(product, isAdmin);
    }

    public List<ProductDTO> getAllProducts(Boolean isAdmin){
        List<Product> products = productRepo.findAll();
        return toDTOList(products, isAdmin);
    }

    public List<ProductDTO> getLatestProduct(Integer quantity, boolean isAdmin) {
        List<Product> products = productRepo.getLatestProduct(quantity);
        return toDTOList(products, isAdmin);
    }

    public ProductDTO add(ProductReqDTO productReqDTO) {

        List<Category> categories = Collections.emptyList();
        if(productReqDTO.getCategoryIdList()!=null && !productReqDTO.getCategoryIdList().isEmpty()){
            categories = categoryService.findAllById(productReqDTO.getCategoryIdList());
        }

        Set<Image> images = Collections.emptySet();
        if(productReqDTO.getImages() != null && !productReqDTO.getImages().isEmpty()) {
            images = fileService.findAllById(productReqDTO.getImages().stream().map(ImageReqDTO::getImageId).collect(Collectors.toList()));
        }

        Product product = Product.builder()
                .price(productReqDTO.getPrice())
                .name(productReqDTO.getName())
                .description(productReqDTO.getDescription())
                .inStock(productReqDTO.getInStock())
                .active(true)
                .categories(categories)
                .images(images)
                .build();

        Product savedProduct = productRepo.save(product);

        return toDTO(savedProduct, true);
    }


    public ProductDTO update(ProductReqDTO productReqDTO) {
        Product product = productRepo.getById(productReqDTO.getProductId());

        List<Category> categories = Collections.emptyList();
        if(productReqDTO.getCategoryIdList()!=null && !productReqDTO.getCategoryIdList().isEmpty()){
            categories = categoryService.findAllById(productReqDTO.getCategoryIdList());
        }

        Set<Image> images = Collections.emptySet();
        if(productReqDTO.getImages() != null && !productReqDTO.getImages().isEmpty()) {
            images = fileService.findAllById(productReqDTO.getImages().stream().map(ImageReqDTO::getImageId).collect(Collectors.toList()));
        }

        product.setActive(productReqDTO.getActive());
        product.setDescription(productReqDTO.getDescription());
        product.setInStock(productReqDTO.getInStock());
        product.setName(productReqDTO.getName());
        product.setPrice(productReqDTO.getPrice());
        product.setCategories(categories);
        product.setImages(images);

        return productRepo.save(product).toDTO();
    }

    public List<ProductDTO> toDTOList(List<Product> products, boolean isAdmin){

        if(products == null) return null;

        List<ProductDTO> productDTOList = new ArrayList<>();

        if(products.size() >0){
            products.forEach( e-> productDTOList.add( toDTO(e, isAdmin)));
        }

        return productDTOList;
    }

    public ProductDTO toDTO(Product product, Boolean isAdmin) {

        if(product == null) return null;

        ProductDTO productDTO = product.toDTO();

        if(isAdmin && product.getCategories()!=null && product.getCategories().size() > 0) {
            List<Integer> categoryIdList =
                    product.getCategories().stream()
                            .map(Category::getCategoryId)
                            .collect(Collectors.toList());
            productDTO.setCategoryIdList(categoryIdList);
        }

        return productDTO;
    }

    public void delete(int productId) {
        Product product = productRepo.getById(productId);
        product.setActive(false);
        productRepo.save(product);
    }

    public boolean isValidExcelOrCsvFormat(MultipartFile file){
        return (ImportFileHelper.hasExcelFormat(file) || ImportFileHelper.hasCsvFormat(file));
    }

    public List<ProductDTO> importProductData(MultipartFile file) throws IOException {
        if(ImportFileHelper.hasExcelFormat(file))
            return importExcelData(file);
        else return importCsvData(file);
    }

    public List<ProductDTO> importExcelData(MultipartFile file) throws IOException {

        List<Product> products = ImportFileHelper.excelToProducts(file.getInputStream());

        if(!products.isEmpty())
            productRepo.saveAll(products);

        return toDTOList(products, false);
    }

    public List<ProductDTO> importCsvData(MultipartFile file) {
        return Collections.emptyList();
    }

    public List<ProductDTO> getProductByIds(List<Integer> ids, Boolean isAdmin) {
        List<Product> products = productRepo.findByProductIdInAndActiveTrue(ids);
        return toDTOList(products, isAdmin);
    }

    public List<ProductDTO> search(ProductFilter filterProduct, Boolean isAdmin) {
        List<Product> products = productRepo.findAll(Specification.where(ProductSpecification.search(filterProduct)));
        return toDTOList(products, isAdmin);
    }
}
