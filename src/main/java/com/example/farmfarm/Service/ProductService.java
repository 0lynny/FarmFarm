package com.example.farmfarm.Service;

import com.example.farmfarm.Entity.ProductEntity;
import com.example.farmfarm.Entity.UserEntity;
import com.example.farmfarm.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;

    // 상품 등록
    public ProductEntity saveProduct(ProductEntity product){
        productRepository.save(product);
        return product;
    }

    // 상품 상세 조회
    public ProductEntity getProduct(Long p_id){
        ProductEntity product = productRepository.findBypId(p_id);
        return product;
    }

    // 상품 리스트 조회
    public List<ProductEntity> getAllProduct() {
        return (List<ProductEntity>) productRepository.findAll();
    }

    // 상품 수정
    public ProductEntity updateProduct(HttpServletRequest request, Long p_id, ProductEntity product) {
        UserEntity user = userService.getUser(request);
        ProductEntity newProduct = productRepository.findBypId(p_id);

        // 수정 항목
        // 이름, 상세정보, 이미지, 수량, 가격, 직거래장소, 경매최저가격
        if (Objects.equals(user.getUId(), newProduct.getFarm().getUser().getUId())) {
            newProduct.setName(product.getName());
            newProduct.setDetail(product.getDetail());
            newProduct.setImage(product.getImage());
            newProduct.setQuantity(product.getQuantity());
            newProduct.setPrice(product.getPrice());
            newProduct.setDirect_location(product.getDirect_location());
            newProduct.setLow_price(product.getLow_price());
            productRepository.save(newProduct);
            return newProduct;
        }
        else {
            return null;
        }
    }

    // 상품 삭제
    public void deleteProduct(HttpServletRequest request, Long p_id) throws Exception{
        UserEntity user = userService.getUser(request);
        ProductEntity product = productRepository.findBypId(p_id);
        if (Objects.equals(user.getUId(), product.getFarm().getUser().getUId())) {
            productRepository.delete(product);
        }
        else {
            throw new Exception();
        }
    }
}
