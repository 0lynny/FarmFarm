package com.example.farmfarm.Service;

import com.example.farmfarm.Entity.FarmEntity;
import com.example.farmfarm.Entity.ProductEntity;
import com.example.farmfarm.Entity.UserEntity;
import com.example.farmfarm.Repository.OrderDetailRepository;
import com.example.farmfarm.Repository.Product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    // 상품 등록
    public ProductEntity saveProduct(ProductEntity product, FarmEntity farm) throws ParseException {
        ProductEntity addProduct = product;
        if (product.getType() == 1){
            product.setGroup(true);
            product.setAuction(false);
        }
        else if (product.getType() == 2) {
            product.setAuction(true);
            product.setGroup(false);
        }
        else {
            product.setGroup(false);
            product.setAuction(false);
        }
        addProduct.setFarm(farm);
        if (addProduct.isAuction()) {
            if (farm.isAuction() == true) { // 경매 농장일 경우 auction_quantity 설정
                addProduct.setAuction_quantity(product.getQuantity());
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                cal.set(Calendar.YEAR, product.getDate().getYear() + 1900);
                cal.set(Calendar.MONTH, product.getDate().getMonth());
                cal.set(Calendar.DATE, product.getDate().getDate());
                cal.set(Calendar.HOUR_OF_DAY, product.getHour());
                cal.set(Calendar.MINUTE, product.getMinute());
                String closeDate = format.format(cal.getTime());
                addProduct.setCloseCalendar(closeDate);

                addProduct.setDate(format.parse(closeDate));
            } else { // 경매 농장이 아닐 경우 예외처리(추후에 설정)
                return null;
            }
        }
        addProduct.setStatus("yes");
        return productRepository.save(addProduct);
    }

    // 상품 상세 조회
    public ProductEntity getProduct(Long p_id){
        ProductEntity product = productRepository.findBypIdAndStatusLike(p_id, "yes");
        return product;
    }

    //TODO 이것도 합치자
    public List<ProductEntity> getProductList(Boolean isAuction) {
        return productRepository.findProductList(isAuction, null, null);
    }

    //TODO - QueryDsl로 Refactoring 필요
    // 검색어 조회
    // 정렬 기능 (인기순, 낮은 가격순, 높은 가격순)
    //상품 정렬 및 검색
    public List<ProductEntity> getProductList(Boolean isAuction, String criteria, String keyword) {
        List<ProductEntity> productList = productRepository.findProductList(isAuction, criteria, keyword);
        return productList;
    }

    // 농장별 상품 리스트 조회
    public List<ProductEntity> getFarmProduct(FarmEntity farm) {
        return productRepository.findAllByFarmAndStatusLike(farm, "yes");
    }

    // 상품 수정
    public ProductEntity updateProduct(HttpServletRequest request, Long p_id, ProductEntity product) {
        UserEntity user = userService.getUser(request);
        ProductEntity newProduct = productRepository.findBypIdAndStatusLike(p_id, "yes");

        // 수정 항목
        // 이름, 상세정보, 이미지, 수량, 가격, 직거래장소, 경매최저가격
        if (Objects.equals(user.getUId(), newProduct.getFarm().getUser().getUId())) {
            newProduct.setName(product.getName());
            newProduct.setDetail(product.getDetail());
            newProduct.setImage1(product.getImage1());
            newProduct.setImage2(product.getImage2());
            newProduct.setImage3(product.getImage3());
            newProduct.setQuantity(product.getQuantity());
            newProduct.setPrice(product.getPrice());
            newProduct.setDirect_location(product.getDirect_location());
            newProduct.setLow_price(product.getLow_price());
            return productRepository.save(newProduct);
        }
        else {
            return null;
        }
    }

    // 상품 삭제
    public void deleteProduct(HttpSession session, Long p_id) throws Exception{
        UserEntity user = (UserEntity)session.getAttribute("user");
        ProductEntity product = productRepository.findBypIdAndStatusLike(p_id, "yes");
        System.out.println("유저는 ? " + product.getFarm().getUser().getUId()); //
        if (Objects.equals(user.getUId(), product.getFarm().getUser().getUId())) {
            if (orderDetailRepository.findAllByProduct(product) == null || orderDetailRepository.findAllByProduct(product).isEmpty()) { // 상품에 대한 주문건이 없으면
                System.out.println("주문건 없음!!!");
                product.setStatus("no");
                productRepository.save(product);
            }
            else {
                System.out.println("주문건 있음!!!");
                System.out.println("상품 삭제 불가");
                throw new Exception();
            }
        }
        else {
            throw new Exception();
        }
    }

}
