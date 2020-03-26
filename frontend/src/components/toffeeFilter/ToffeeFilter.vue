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
      <!--   toffing area   -->
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
          <!-- 세부 토핑 선택 : 호출되는 곳에 따라 다르게 출력(0:검색, 1:등록/수정) -->
          <div class="mb-1">
            <!-- 검색 -->
            <div v-if="filterMode == 0" class="w-full max-w-sm ml-3">
              <div class="mb-3">
                <label class="flex items-center font-semibold">
                  <div class="w-1/3">
                    <span class="text-sm">
                      프라푸치노용 시럽
                    </span>
                  </div>
                  <div class="w-2/3">
                    <input class="leading-tight" type="checkbox" checked="checked" disabled="disabled">
                  </div>
                </label>
              </div>
              <div class="mb-3">
                <label class="flex items-center font-semibold">
                  <div class="w-1/3">
                    <span class="text-sm">
                      바닐라 시럽
                    </span>
                  </div>
                  <div class="w-2/3">
                    <input class="leading-tight" type="checkbox">
                  </div>
                </label>
              </div>
              <div class="mb-3">
                <label class="flex items-center font-semibold">
                  <div class="w-1/3">
                    <span class="text-sm">
                      헤이즐넛 시럽
                    </span>
                  </div>
                  <div class="w-2/3">
                    <input class="leading-tight" type="checkbox">
                  </div>
                </label>
              </div>
            </div>
            <!-- 등록/수정 -->
            <div v-else class="w-full max-w-sm ml-3">
              <div v-for="topping in toppings" :key="topping.no">
                <topping v-show="topping.categoryNo == selectedToppingCategoryNo" :topping="topping"></topping>
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
    props: {
      filterMode: Number
    },
    data () {
      return {
        productCategories: [
          { no: 1, name: '에스프레소' },
          { no: 2, name: '프라푸치노' },
          { no: 3, name: '블렌디드' },
          { no: 4, name: '피지오' },
          { no: 5, name: '티(티바나)' },
          { no: 6, name: '기타' },
          { no: 7, name: '병음료' }
        ],
        products: [
          { categoryNo: 1, no: 1, name: '카페 아메리카노' },
          { categoryNo: 1, no: 2, name: '아이스 카페 아메리카노' },
          { categoryNo: 1, no: 3, name: '카페 모카' },
          { categoryNo: 1, no: 4, name: '아이스 카페 모카' },
          { categoryNo: 1, no: 5, name: '카페 라떼' },
          { categoryNo: 1, no: 6, name: '아이스 카페 라떼' },
          { categoryNo: 1, no: 7, name: '카푸치노' },
          { categoryNo: 1, no: 8, name: '아이스 카푸치노' },
          { categoryNo: 1, no: 9, name: '화이트 초콜릿 모카' },
          { categoryNo: 1, no: 10, name: '아이스 화이트 초콜릿 모카' },
          { categoryNo: 1, no: 11, name: '스타벅스 돌체 라떼' },
          { categoryNo: 1, no: 12, name: '아이스 스타벅스 돌체 라떼' },
          { categoryNo: 4, no: 13, name: '핑크 자몽 피지오' },
          { categoryNo: 4, no: 14, name: '쿨 라임 피지오' },
          { categoryNo: 4, no: 15, name: '블랙 티 레모네이드 피지오' },
          { categoryNo: 4, no: 16, name: '패션 탱고 티 레모네이드 피지오' }
        ],
        productToppingMap: [
          { productNo: 1, toppingNo: 1, defaultValue: 1, checkFlag: 1 }
        ],
        toppingCategories: [
          { no: 1, name: '커피' },
          { no: 2, name: '시럽' },
          { no: 3, name: '베이스' },
          { no: 4, name: '우유/음료 온도' },
          { no: 5, name: '기타' },
          { no: 6, name: '얼음' },
          { no: 7, name: '자바칩' },
          { no: 8, name: '휘핑크림' },
          { no: 9, name: '드리즐' }
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
          { categoryNo: 6, no: 11, name: '얼음', toppingPrice: 0, quantityType: 3 },
          // 자바칩
          { categoryNo: 7, no: 12, name: '자바칩', toppingPrice: 0, quantityType: 0 },
          { categoryNo: 7, no: 13, name: '프라푸치노 자바칩', toppingPrice: 0, quantityType: 1 },
          // 휘핑크림
          { categoryNo: 8, no: 14, name: '휘핑크림', toppingPrice: 0, quantityType: 3 },
          // 드리즐
          { categoryNo: 9, no: 15, name: '카라멜 드리즐', toppingPrice: 0, quantityType: 3 },
          { categoryNo: 9, no: 16, name: '초콜릿 드리즐', toppingPrice: 0, quantityType: 3 }
        ],
        quantityCode: [
          { quantityCode: 1, seqNo: 1, quantityName: '없이', quantityType: '4' },
          { quantityCode: 2, seqNo: 2, quantityName: '적게', quantityType: '2,3,4' },
          { quantityCode: 3, seqNo: 3, quantityName: '보통', quantityType: '2,3,4' },
          { quantityCode: 4, seqNo: 4, quantityName: '많이', quantityType: '3,4' },
          { quantityCode: 5, seqNo: 5, quantityName: '클래식 시럽 없이', quantityType: '5' }
        ],
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

        // 상품의 토핑 목록 조회
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
