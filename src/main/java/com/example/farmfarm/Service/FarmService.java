package com.example.farmfarm.Service;

import com.example.farmfarm.Entity.FarmEntity;
import com.example.farmfarm.Entity.ProductEntity;
import com.example.farmfarm.Entity.UserEntity;
import com.example.farmfarm.Repository.Farm.FarmCustomRepositoryImpl;
import com.example.farmfarm.Repository.Farm.FarmJpaRepository;
import com.example.farmfarm.Repository.Farm.FarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class FarmService {
    @Autowired
    private FarmRepository farmRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    // 농장 등록
    public FarmEntity saveFarm(UserEntity user, FarmEntity farm) {
        farm.setUser(user);
        farm.setStatus("yes");
        return farmRepository.save(farm);
    }

    public List<FarmEntity> getFarmList(String criteria, String keyword) {
        return farmRepository.findFarmList(criteria, keyword);
    }

    public List<FarmEntity> searchByLocation(String locationCity, String locationGu) {
        return farmRepository.findAllByLocationCityAndLocationGu(locationCity, locationGu);
    }

    // 농장 상세 조회
    public FarmEntity getFarm(Long fId) {
        FarmEntity fa = farmRepository.findByfIdAndStatusLike(fId, "yes");
        if (fa.getStatus().equals("no"))
            return null;
        return fa;
    }

    //나의 농장 조회
    public FarmEntity getMyFarm(UserEntity user) {
        FarmEntity myFarm = farmRepository.findByUserAndStatusLike(user, "yes");
        if (myFarm != null) {
            System.out.println(myFarm.getStatus());
        }
        return myFarm;
    }

    // 농장 수정
    public FarmEntity updateFarm(HttpServletRequest request, Long fId, FarmEntity farm) {
        UserEntity user = userService.getUser(request);
        FarmEntity newFarm = farmRepository.findByfIdAndStatusLike(fId, "yes");

        if (Objects.equals(user.getUId(), newFarm.getUser().getUId())) {
            // 수정되는 것들  (농장 이름, 위치-시, 위치-구, 상세, 이미지, 경매시간, 경매 참여 여부, 생성 시간?)
            newFarm.setName(farm.getName());
            newFarm.setLocationCity(farm.getLocationCity());
            newFarm.setLocationGu(farm.getLocationGu());
            newFarm.setLocationFull(farm.getLocationFull());
            newFarm.setLocationDetail(farm.getLocationDetail());
            newFarm.setDetail(farm.getDetail());
            newFarm.setImage(farm.getImage());
            newFarm.setAuction_time(farm.getAuction_time());
            newFarm.setAuction(farm.isAuction()); // 이게 왜 이런걸까요 ?
            newFarm.setCreated_at(farm.getCreated_at());
            newFarm.setDetail(farm.getDetail());
            farmRepository.save(newFarm);
            return newFarm;
        } else {
            return null;
        }
    }

    // 농장 삭제
    public void deleteFarm(HttpSession session, Long fId) throws Exception {
        UserEntity user = (UserEntity) session.getAttribute("user");
        FarmEntity farm = farmRepository.findByfIdAndStatusLike(fId, "yes");
        if (Objects.equals(user.getUId(), farm.getUser().getUId())) {
            if (productService.getFarmProduct(farm) == null || productService.getFarmProduct(farm).isEmpty()) {  // 농장에 상품이 없으면
                System.out.println("농장에 상품 없음!!!");
                farm.setStatus("no");
                farmRepository.save(farm);
            }
            else {
                System.out.println("농장에 상품 있음!!!");
                System.out.println("상품이 등록되어 있어 삭제할 수 없습니다.");
                throw new Exception();
            }
        } else {
            System.out.println("유저가 달라 삭제할 수 없습니다.");
            throw new Exception();
        }
    }
}
