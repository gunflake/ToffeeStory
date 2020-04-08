<template>
  <div>
    <table class="table-fixed table w-full">
      <tbody>
      <!--   search area   -->
<!--      <tr>-->
<!--        <td class="border px-4 py-2">-->
<!--          <form>-->
<!--            <div class="flex items-center border border-gray-500">-->
<!--              <input class="appearance-none bg-transparent border-none w-full text-gray-700 text-xs mr-3 py-1 px-2 leading-tight focus:outline-none" type="text" placeholder="음료를 검색하세요." aria-label="Full name">-->
<!--              <button class="flex-shrink-0 bg-gray-500 border-gray-500 text-xs border-4 text-white py-1 px-2" type="button">검색</button>-->
<!--            </div>-->
<!--          </form>-->
<!--        </td>-->
<!--      </tr>-->
      <!--   filter area   -->
      <tr v-if="toppingVisible">
        <td id="toffeeFilter" class="border px-4 py-2">
          <component :is="filter" :productName="selectedProductName" :productNo="selectedProductNo" v-on:deleteEvent="deleteProduct"></component>
        </td>
      </tr>
      <!--   product area   -->
      <tr v-show="productVisible">
        <td class="border px-4 py-2">
          <div class="mb-3">
            <nav class="bg-white flex">
              <div class="-mb-px flex justify-left">
                <a :id="'productCategory'+productCategory.no" v-for="productCategory in productCategories" :key="productCategory.no" @click="selectProductCategory($event, productCategory.no)" role="button" class="product-category no-underline text-black border-b-2 border-transparent hover:border-black tracking-wide font-semibold text-sm py-3 mr-8">
                  {{productCategory.name}}
                </a>
              </div>
            </nav>
          </div>
          <div class="mb-1">
            <div v-for="product in products" :key="product.no" class="inline-flex">
              <div role="button" v-if="product.categoryNo == selectedProductCategoryNo" @click="selectProduct($event, product)" class="mr-3 mb-2 p-2 bg-gray-300 items-center text-gray-900 text-sm leading-none rounded-full">
                <a class="mx-2 text-left flex-auto">{{product.name}}</a>
              </div>
            </div>
          </div>
        </td>
      </tr>
      <!--   topping area   -->
      <tr v-show="toppingVisible">
        <td class="border px-4 py-2">
          <div class="mb-3">
            <nav class="bg-white flex">
              <div class="-mb-px flex justify-left">
                <a :id="'toppingCategory'+toppingCategory.no" v-for="toppingCategory in toppingCategories" :key="toppingCategory.no" @click="selectToppingCategory($event, toppingCategory.no)"
                   role="button" class="topping-category no-underline text-black border-b-2 border-transparent hover:border-black tracking-wide font-semibold text-sm py-3 mr-8">
                  {{toppingCategory.name}}
                </a>
              </div>
            </nav>
          </div>
          <div class="mb-1">
            <div class="w-full max-w-sm ml-3">
              <div v-for="topping in toppings" :key="topping.no">
                <topping v-show="topping.categoryNo == selectedToppingCategoryNo" :topping="topping" :productTopping="productTopping"></topping>
              </div>
            </div>
          </div>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>
<script>
  import NumberCounter from '@/components/toffeeFilter/NumberCounter'
  import ButtonWithX from '@/components/toffeeFilter/ButtonWithX'
  import Topping from '@/components/toffeeFilter/Topping'
  export default {
    name: 'ToffeeFilter',
    components: { NumberCounter, ButtonWithX, Topping },
    data () {
      return {
        productCategories: [
          { no: 1, name: '콜드브루' },
          { no: 2, name: '에스프레소' },
          { no: 3, name: '디카페인 커피' },
          { no: 4, name: '프라푸치노' },
          { no: 5, name: '블렌디드' },
          { no: 6, name: '피지오' },
          { no: 7, name: '티(티바나)' },
          { no: 8, name: '기타' }
        ],
        products: [
          { categoryNo: 1, no: 1, name: '돌체 콜드 브루' },
          { categoryNo: 1, no: 2, name: '콜드 폼 콜드브루' },
          { categoryNo: 1, no: 3, name: '바닐라 크림 콜드 브루' },
          { categoryNo: 1, no: 4, name: '콜드 브루' },
          { categoryNo: 1, no: 5, name: '나이트로 바닐라 크림' },
          { categoryNo: 1, no: 6, name: '나이트로쇼콜라 클라우드' },
          { categoryNo: 1, no: 7, name: '나이트로 콜드 브루' },
          { categoryNo: 2, no: 8, name: '카페 아메리카노' },
          { categoryNo: 2, no: 9, name: '카페 라떼' },
          { categoryNo: 2, no: 10, name: '카푸치노' },
          { categoryNo: 2, no: 11, name: '카라멜 마키아또' },
          { categoryNo: 2, no: 12, name: '화이트 초콜릿 모카' },
          { categoryNo: 2, no: 13, name: '커피 스타벅스 더블 샷' },
          { categoryNo: 2, no: 14, name: '바닐라 스타벅스 더블샷' },
          { categoryNo: 2, no: 15, name: '커라멜 스타벅스 더블 샷' },
          { categoryNo: 2, no: 16, name: '헤이즐넛 스타벅스 더블 샷' },
          { categoryNo: 2, no: 17, name: '에스프레소' },
          { categoryNo: 2, no: 18, name: '에스프레소 마키아또' },
          { categoryNo: 2, no: 19, name: '에스크레소 콘 파나' },
          { categoryNo: 2, no: 20, name: '바닐라 플랫 화이트' },
          { categoryNo: 2, no: 21, name: '스타벅스 돌체 라떼' },
          { categoryNo: 3, no: 22, name: '디카페인 스타벅스 돌체 라떼' },
          { categoryNo: 3, no: 23, name: '디카페인 카라멜 마키아또' },
          { categoryNo: 3, no: 24, name: '디카페인 카페 라떼' },
          { categoryNo: 3, no: 25, name: '디카페인 카페 아메리카노' },
          { categoryNo: 3, no: 26, name: '1/2디카페인 스타벅스 돌체 라떼' },
          { categoryNo: 3, no: 27, name: '1/2디카페인 카라멜 마키아또' },
          { categoryNo: 3, no: 28, name: '1/2디카페인 카페 라떼' },
          { categoryNo: 3, no: 29, name: '1/2디카페인 카페 아메리카노' },
          { categoryNo: 4, no: 30, name: '블론드 카라멜 클라우드 마키아또' },
          { categoryNo: 4, no: 31, name: '블론드 코코아 클라우드 마키아또' },
          { categoryNo: 4, no: 32, name: '블론드 리스트레토 마키아또' },
          { categoryNo: 4, no: 33, name: '블론드 에스프레소 토닉' },
          { categoryNo: 4, no: 34, name: '블론드 스타벅스 돌체 라떼' },
          { categoryNo: 4, no: 35, name: '블론드 카페 라떼' },
          { categoryNo: 4, no: 36, name: '블론드 카페 아메리카노' },
          { categoryNo: 5, no: 37, name: '이천 햅쌀 커피 프라푸치노' },
          { categoryNo: 5, no: 38, name: '자파칩 프라푸치노' },
          { categoryNo: 5, no: 39, name: '화이트 초콜릿 모카 프라푸치노' },
          { categoryNo: 5, no: 40, name: '카라멜 프라푸치노' },
          { categoryNo: 5, no: 41, name: '모카 프라푸치노' },
          { categoryNo: 5, no: 42, name: '에스프레소 프라푸치노' },
          { categoryNo: 5, no: 43, name: '이천 햅쌀 크림 프라푸치노' },
          { categoryNo: 5, no: 44, name: '그린 티 크림 프라푸치노' },
          { categoryNo: 5, no: 45, name: '초콜릿 크림 칩 프라푸치노' },
          { categoryNo: 5, no: 46, name: '초콜릿 크림 프라푸치노' },
          { categoryNo: 5, no: 47, name: '바닐라 크림 프라푸치노' },
          { categoryNo: 5, no: 48, name: '제주 까망 크림 프라푸치노' },
          { categoryNo: 5, no: 49, name: '제주 쑥떡 크림 프라푸치노' },
          { categoryNo: 5, no: 50, name: '제주 말차샷 크림 프라푸치노' },
          { categoryNo: 5, no: 51, name: '화이트 타이거 프라푸치노' },
          { categoryNo: 6, no: 52, name: '망고 바나나 블렌디드' },
          { categoryNo: 6, no: 53, name: '자몽 셔벗 블렌디드' },
          { categoryNo: 6, no: 54, name: '딸기 요거트 블렌디드' },
          { categoryNo: 6, no: 55, name: '망고 패션 후르츠 블렌디드' },
          { categoryNo: 6, no: 56, name: '익스트림 티 블렌디드' },
          { categoryNo: 7, no: 57, name: '핑크 자몽 피지오' },
          { categoryNo: 7, no: 58, name: '쿨 라임 피지오' },
          { categoryNo: 7, no: 59, name: '블랙 티 레모네이드 피지오' },
          { categoryNo: 7, no: 60, name: '패션 탱고 티 레모네이드 피지오' },
          { categoryNo: 7, no: 61, name: '매직 팝 스플래쉬 피지오' },
          { categoryNo: 8, no: 62, name: '그린 티 라떼' },
          { categoryNo: 8, no: 63, name: '라임 패션 티' },
          { categoryNo: 8, no: 64, name: '자몽 허니 블랙 티' },
          { categoryNo: 8, no: 65, name: '차이 티 라떼' },
          { categoryNo: 8, no: 66, name: '제주 유기 녹차' },
          { categoryNo: 8, no: 67, name: '잉글리쉬 프렉퍼스트 티' },
          { categoryNo: 8, no: 68, name: '차이 티' },
          { categoryNo: 8, no: 69, name: '유스베리 티' },
          { categoryNo: 8, no: 70, name: '히비스커스 블렌드 티' },
          { categoryNo: 8, no: 71, name: '민트 블렌드 티' },
          { categoryNo: 8, no: 72, name: '캐모마일 블렌드 티' },
          { categoryNo: 8, no: 73, name: '제주 유자 탱고 티' },
          { categoryNo: 8, no: 74, name: '제주 말차샷 라떼' },
          { categoryNo: 8, no: 75, name: '그랜마 애플 블랙 티' },
          { categoryNo: 8, no: 76, name: '그랜마 애플 블랙 밀크 티' },
          { categoryNo: 8, no: 77, name: '돌체 블랙 밀크 티' },
          { categoryNo: 8, no: 78, name: '유자 민트 티' },
          { categoryNo: 9, no: 79, name: '시크니처 핫 초콜릿' },
          { categoryNo: 9, no: 80, name: '스팀 우유' },
          { categoryNo: 9, no: 81, name: '우유' },
          { categoryNo: 9, no: 82, name: '제주 노랑 고구마 라떼' },
          { categoryNo: 9, no: 83, name: '제주 쑥쑥 라떼' },
          { categoryNo: 9, no: 84, name: '제주 까망 라떼' },
          { categoryNo: 9, no: 85, name: '플러피 판다 핫 초콜릿' },
          { categoryNo: 9, no: 86, name: '매직 윈터 트로피컬 핫 펀치' }
        ],
        toppingCategories: [
          { no: 1, name: '커피' },
          { no: 2, name: '시럽' },
          { no: 3, name: '베이스' },
          { no: 4, name: '우유/음료 온도' },
          { no: 5, name: '기타' },
          { no: 6, name: '과일&채소' },
          { no: 7, name: '얼음' },
          { no: 8, name: '자바칩' },
          { no: 9, name: '휘핑 크림' },
          { no: 10, name: '드리즐' },
          { no: 11, name: '토핑' }
        ],
        toppings: [
          // 커피
          { categoryNo: 1, no: 1, name: '에스프레소 샷', toppingPrice: 0, quantityType: 1 },
          { categoryNo: 1, no: 2, name: '디카페인', toppingPrice: 0, quantityType: 0 },
          { categoryNo: 1, no: 3, name: '프라푸치노 로스트', toppingPrice: 0, quantityType: 1 },
          // 시럽
          { categoryNo: 2, no: 4, name: '클래식 시럽', toppingPrice: 0, quantityType: 1 },
          { categoryNo: 2, no: 5, name: '바닐라 시럽', toppingPrice: 0, quantityType: 1 },
          { categoryNo: 2, no: 6, name: '헤이즐넛 시럽', toppingPrice: 0, quantityType: 1 },
          { categoryNo: 2, no: 7, name: '카라멜 시럽', toppingPrice: 0, quantityType: 1 },
          // 베이스
          { categoryNo: 3, no: 8, name: '물', subFlag: 1 },
          // 우유/음료 온도
          { categoryNo: 4, no: 9, name: '우유종류', toppingPrice: 0, quantityType: 0 },
          // 기타
          { categoryNo: 5, no: 10, name: '저지방 요거트', toppingPrice: 0, quantityType: 0 },
          // 얼음
          { categoryNo: 6, no: 11, name: '얼음', toppingPrice: 0, quantityType: 2 },
          // 자바칩
          { categoryNo: 7, no: 12, name: '자바칩', toppingPrice: 0, quantityType: 0 },
          { categoryNo: 7, no: 13, name: '프라푸치노 자바칩', toppingPrice: 0, quantityType: 1 },
          // 휘핑크림
          { categoryNo: 8, no: 14, name: '휘핑크림', toppingPrice: 0, quantityType: 3 },
          // 드리즐
          { categoryNo: 9, no: 15, name: '카라멜 드리즐', toppingPrice: 0, quantityType: 3 },
          { categoryNo: 9, no: 16, name: '초콜릿 드리즐', toppingPrice: 0, quantityType: 3 }
        ],
        productTopping:
          { subToppingNo: 1, quantityCode: 1, value: 0, optionType: 1 },
        productVisible: true,
        toppingVisible: false,
        selectedProductCategoryNo: 1,
        selectedProductNo: 0,
        selectedProductName: '',
        selectedToppingCategoryNo: 1,
        filter: ''
      }
    },
    mounted () {
      // 첫번째 탭에 밑줄
      document.getElementById('productCategory' + this.selectedProductCategoryNo).classList.add('border-black')
      document.getElementById('toppingCategory' + this.selectedToppingCategoryNo).classList.add('border-black')
    },
    methods: {
      // 상품 카테고리 선택
      selectProductCategory (event, productCategoryNo) {
        this.selectedProductCategoryNo = productCategoryNo
        this.tabUnderline(true, event.target, '.product-category')
      },
      // 토핑 카테고리 선택
      selectToppingCategory (event, toppingCategoryNo) {
        this.selectedToppingCategoryNo = toppingCategoryNo
        this.tabUnderline(true, event.target, '.topping-category')
      },
      // 상품 선택
      selectProduct (event, product) {
        this.selectedProductNo = product.no
        this.selectedProductName = product.name
        this.productVisible = false
        this.toppingVisible = true
        this.filter = 'button-with-x'

        document.getElementById('toppingCategory' + this.selectedToppingCategoryNo).classList.add('border-black')

        // ProductTopping
        // 상품의 토핑 목록 조회
        // this.toppings = ''
        // 상품 토핑 초기값 세팅
        // this.productTopping = ''
      },
      // 필터에서 상품 삭제
      deleteProduct () {
        this.selectedProductCategoryNo = 1
        this.selectedToppingCategoryNo = 1
        this.productVisible = true
        this.toppingVisible = false
        this.tabUnderline(false, 'productCategory', '.product-category')
        this.tabUnderline(false, 'toppingCategory', '.topping-category')
      },
      // 탭 밑줄 설정
      tabUnderline (isSelectMode, element, selector) {
        let tabs = document.querySelectorAll(selector)

        if (isSelectMode) { // select
          for (let i = 0; i < tabs.length; i++) {
            if (tabs[i] === element) {
              element.classList.add('border-black')
            } else {
              tabs[i].classList.remove('border-black')
            }
          }
        } else { // delete
          for (let i = 0; i < tabs.length; i++) {
            tabs[i].classList.remove('border-black')
          }
          document.getElementById(element + this.selectedProductCategoryNo).classList.add('border-black')
        }
      }
    }
  }
</script>
