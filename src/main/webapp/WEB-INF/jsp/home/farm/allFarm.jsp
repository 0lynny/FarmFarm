<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:eval expression="@environment.getProperty('serverUrl')" var="jspPath" />
<%
    String serverUrl = "@environment.getProperty('serverUrl')";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>FarmFarm</title>
    <meta charset="UTF-8"/>
    <meta
            name="viewport"
            content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, viewport-fit=cover"
    />
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta
            name="apple-mobile-web-app-status-bar-style"
            content="black-translucent"
    />
    <meta name="theme-color" content="#2196f3"/>
    <meta
            http-equiv="Content-Security-Policy"
            content="default-src * 'self' 'unsafe-inline' 'unsafe-eval' data: gap:"
    />
    <meta name="author" content="DexignZone"/>
    <meta name="robots" content="index, follow"/>
    <meta
            name="keywords"
            content="delivery, eatables, ecommerce, electronics, food delivery, Food Store, fruits, grocery, Grocery & Staples, market, online grocery shop, online vegetables, organic, supermarket, vegetables, "
    />
    <meta
            name="description"
            content="Kede - Grocery Mobile App HTML ( Framework 7 + PWA )"
    />
    <meta
            property="og:title"
            content="Kede - Grocery Mobile App HTML ( Framework 7 + PWA )"
    />
    <meta
            property="og:description"
            content="Kede - Grocery Mobile App HTML ( Framework 7 + PWA )"
    />
    <meta
            property="og:image"
            content="https://farmfarmbucket.s3.ap-northeast-2.amazonaws.com/10591631-d5da-4804-b013-ff6eccbed6f7.png"
    />
    <meta name="format-detection" content="telephone=no"/>

    <title>FarmFarm</title>

    <link rel="stylesheet" href="../style/framework7-bundle.min.css"/>
    <link rel="stylesheet" href="../style/app.css"/>

    <link
            rel="stylesheet"
            href="../style/font-awesome/css/font-awesome.min.css"
    />
    <link
            rel="stylesheet"
            href="../style/line-awesome/css/line-awesome.min.css"
    />
    <link rel="stylesheet" href="../style/flaticons/flaticon.css"/>

    <link rel="stylesheet" href="../style/style.css"/>

    <link rel="apple-touch-icon" href="../images/f7-icon-square.png"/>
    <link rel="icon" href="https://farmfarmbucket.s3.ap-northeast-2.amazonaws.com/10591631-d5da-4804-b013-ff6eccbed6f7.png"/>

    <link rel="preconnect" href="https://fonts.gstatic.com"/>
    <link
            href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
            rel="stylesheet"
    />

    <style>
        table {
            border-collapse: collapse;
            font-size: 18px;
            width: 400px;
        }

        table td, table th {
            height: 40px;
            border-bottom: 1px solid #F3F3F3;
            padding-bottom: 5px;
            padding-top: 5px;
        }

        th[scope="row"] {
            color: #94C015;
        }

        .searchbar-input-wrap {
            display: flex;
            align-items: center;
            padding-top: 15px;
        }

        .searchbar-input-wrap input {
            order: 1;
            width: 400px;
        }

        .searchbar-input-wrap i {
            order: 2;
            margin-right: 5px;
        }
    </style>
    <script>
        function performSearch() {
            var searchInput = document.getElementById("searchInput");
            var searchText = searchInput.value;
            var sortSelect = document.getElementById("sortSelect");
            var selectedSort = sortSelect.value;
            let url = "";

            if (searchText === "") {
                alert('검색어를 입력해주세요.');
            } else {
                url += '?keyword=' + encodeURIComponent(searchText);
            }

            if (selectedSort) {
                url += (url.includes('?') ? '&' : '?') + 'sort=' + encodeURIComponent(selectedSort);
            }

            window.location.href = url;
            console.log("검색어: " + searchText);
            console.log("정렬: " + selectedSort);
        }

        document.addEventListener("DOMContentLoaded", function() {
            var sortSelect = document.getElementById("sortSelect");
            var selectedOption = localStorage.getItem("selectedOption");

            if (selectedOption) {
                sortSelect.value = selectedOption;
            }

            sortSelect.addEventListener("change", function() {
                performSearch(); // 정렬을 변경할 때 검색을 동시에 실행
            });

            var searchInput = document.getElementById("searchInput");
            searchInput.addEventListener("keydown", function(event) {
                if (event.key === "Enter") {
                    performSearch();
                }
            });
        });

    </script>

</head>
<body>
<div class="page page-homepage light" data-name="homepage">
    <div class="navbar navbar-style-1">
        <div class="navbar-inner">
            <div class="left">
                <a href="/" class="link back">
                    <i class="flaticon-left"></i>
                </a>
            </div>
            <div class="title">농장 전체 보기</div>
            <div class="right">
            </div>
        </div>
    </div>
    <div class="page-content content-area pt-30 bottom-sp80">
        <div class="container allProduct">
            <div class="searchbar-input-wrap">
                <input id="searchInput" type="search" placeholder="검색어를 입력해주세요." />
                <i id="searchIcon" class="fa-solid fa-magnifying-glass" onclick="performSearch()"></i>
            </div>
            <div class="allProduct_text">
                <div class="total">
                    <h5>총 <span id="totalProductsCount">${farmList.size()}</span>개</h5>
                </div>
                <div class="sort">
                    <div class="sort_type">
                        <select id="sortSelect">
                            <option value="rating" selected>인기순</option>
                            <option value="new">신규순</option>
                            <option value="old">오래된순</option>
                        </select>
                    </div>
                </div>
            </div>
            <p style="margin-top:-12px">
                <em class="link">
                    <%--                                    <a href="javascript:void(0);" onclick="window.open('http://fiy.daum.net/fiy/map/CsGeneral.daum', '_blank', 'width=981, height=650')">--%>
                    <%--                                        혹시 주소 결과가 잘못 나오는 경우에는 여기에 제보해주세요.--%>
                    <%--                                    </a>--%>
                </em>
            </p>
            <div id="map" style="width:100%;height:350px;"></div>

            <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=547b485b92252f5f3b1f8d3253d2b9d5&libraries=services"></script>

            <script>
                var mapContainer = document.getElementById('map'), // 지도를 표시할 div
                    mapOption = {
                        center: new kakao.maps.LatLng(37.5642135, 127.0016985), // 지도의 중심좌표
                        level: 10 // 지도의 확대 레벨
                    };
                console.log("111111111111111111111111");
                // 지도를 생성합니다
                var map = new kakao.maps.Map(mapContainer, mapOption);
                console.log("22222222222222222222222222");
                // 주소-좌표 변환 객체를 생성합니다
                var geocoder = new kakao.maps.services.Geocoder();

                // 주소로 좌표를 검색합니다

                var farmList = ${jsonFarmList};
                //var farmList = JSON.parse(${jsonFarmList});
                console.log(farmList);
                farmList.forEach(function(farm) {
                    geocoder.addressSearch(farm.locationFull, function(result, status) {

                        // 정상적으로 검색이 완료됐으면
                        if (status === kakao.maps.services.Status.OK) {

                            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

                            // 결과값으로 받은 위치를 마커로 표시합니다
                            var marker = new kakao.maps.Marker({
                                map: map,
                                position: coords
                            });
                            var farmName = farm.name;
                            // 인포윈도우로 장소에 대한 설명을 표시합니다
                            var infowindow = new kakao.maps.InfoWindow({
                                content: '<div style="width:150px;text-align:center;padding:6px 0;">' + farm.name + '</div>'
                            });
                            infowindow.open(map, marker);
                            kakao.maps.event.addListener(marker, 'click', function () {
                                console.log("클릭함! " + farm.fId);
                                window.location.href='/farm/' + farm.fId;
                            })
                            // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                            map.setCenter(coords);
                        }
                    });
                });
            </script>
            <div class="row">
                <c:choose>
                    <c:when test="${farmList.size() eq 0}">
                        <p>검색결과 없습니다.</p>
                    </c:when>
                    <c:otherwise>
                        <table class="table">
                            <tbody>
                            <c:forEach var="farm" items="${farmList}" varStatus="status">
                                <tr>
                                    <th scope="row">${status.count}</th>
                                    <td><a href="/farm/${farm.FId}" style="color: black;">${farm.name}</a></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/jsp/common/tabbar.jsp" %>
</div>
</body>
</html>
