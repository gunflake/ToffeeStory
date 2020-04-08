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
      <tr v-if="productVisible">
        <td class="border px-4 py-2">
          <div class="mb-3">
            <nav class="bg-white flex">
              <div class="-mb-px flex justify-left">
                <a :id="'category'+category.no" v-for="category in categories" :key="category.no" @click="selectProductCategory($event, category.no)" role="button" class="product-category no-underline text-black border-b-2 border-transparent hover:border-black tracking-wide font-semibold text-sm py-3 mr-8">
                  {{category.name}}
                </a>
              </div>
            </nav>
          </div>
          <div class="mb-1">
            <div v-for="product in products" :key="product.no" class="inline-flex">
              <div role="button" v-if="product.categoryNo == selectedCategoryNo" @click="selectProduct($event, product)" class="mr-3 mb-2 p-2 bg-gray-300 items-center text-gray-900 text-sm leading-none rounded-full">
                <a class="mx-2 text-left flex-auto">{{product.name}}</a>
              </div>
            </div>
          </div>
        </td>
      </tr>
      <!--   toffing area   -->
      <tr v-if="toppingVisible">
        <td class="border px-4 py-2">
          <div class="mb-3">
            <nav class="bg-white flex">
              <div class="-mb-px flex justify-left">
                <a v-for="item in toppingCategories" :key="item.no" v-on:click="selectTopping(item.no)" role="button" class="no-underline text-black border-b-2 border-transparent hover:border-black tracking-wide font-semibold text-sm py-3 mr-8">
                  {{item.name}}
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
              <div class="mb-3 flex items-center">
                <div class="w-1/3">
                  <span class="font-semibold text-sm">
                    프라푸치노용 시럽
                  </span>
                </div>
                <div class="w-2/3">
                  <label class="inline-flex items-center mr-3">
                    <input type="radio" class="form-radio" name="radio" value="1" checked="checked">
                    <span class="ml-2 text-sm">일반</span>
                  </label>
                  <label class="inline-flex items-center mr-3">
                    <input type="radio" class="form-radio" name="radio" value="2">
                    <span class="ml-2 text-sm">라이트 (휘핑없이 추천)</span>
                  </label>
                </div>
              </div>
              <div class="mb-3 flex items-center">
                <div class="w-1/3">
                  <span class="font-semibold text-sm">
                    바닐라 시럽
                  </span>
                </div>
                <div class="w-2/3">
                  <number-counter></number-counter>
                </div>
              </div>
              <div class="mb-3 flex items-center">
                <div class="w-1/3">
                  <span class="font-semibold text-sm">
                    헤이즐넛 시럽
                  </span>
                </div>
                <div class="w-2/3">
                  <number-counter></number-counter>
                </div>
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
  export default {
    name: 'ToffeeFilter',
    components: { NumberCounter, ButtonWithX },
    props: {
      filterMode: Number
    },
    data () {
      return {
        categories: [
          { no: 1, name: '에스프레소' },
          { no: 2, name: '프라푸치노' },
          { no: 3, name: '블렌디드' },
          { no: 4, name: '피지오' },
          { no: 5, name: '티바나' },
          { no: 6, name: '기타 제조음료' },
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
        toppingCategories: [
          { no: 1, name: '커피' },
          { no: 2, name: '시럽' },
          { no: 3, name: '베이스' },
          { no: 4, name: '기타' },
          { no: 5, name: '얼음' },
          { no: 6, name: '휘핑크림' },
          { no: 7, name: '드리즐' }
        ],
        toppings: [
          { categoryNo: 1, no: 1, name: '에스프레소 샷', subFlag: 0 },
          { categoryNo: 2, no: 2, name: '바닐라 시럽', subFlag: 1 },
          { categoryNo: 2, no: 3, name: '헤이즐넛 시럽', subFlag: 1 },
          { categoryNo: 2, no: 4, name: '카라멜 시럽', subFlag: 1 },
          { categoryNo: 3, no: 5, name: '물', subFlag: 1 },
          { categoryNo: 4, no: 6, name: '기타', subFlag: 1 },
          { categoryNo: 5, no: 7, name: '얼음', subFlag: 1 },
          { categoryNo: 5, no: 7, name: '얼음', subFlag: 1 }
        ],
        productVisible: true,
        toppingVisible: false,
        selectedCategoryNo: 1,
        selectedProductNo: 0,
        selectedProductName: '',
        filter: ''
      }
    },
    mounted () {
      document.getElementById('category' + this.selectedCategoryNo).classList.add('border-black')
    },
    methods: {
      selectTopping (itemNo) {
        console.log(itemNo)
      },
      selectProductCategory (event, categoryNo) {
        // 선택된 카테고리 번호로 업데이트
        this.selectedCategoryNo = categoryNo

        let tabs = document.querySelectorAll('.product-category')

        // 탭 밑줄 설정
        for (let i = 0; i < tabs.length; i++) {
          if (tabs[i] === event.target) {
            event.target.classList.add('border-black')
          } else {
            tabs[i].classList.remove('border-black')
          }
        }
      },
      selectProduct (event, product) {
        this.selectedProductNo = product.no
        this.selectedProductName = product.name
        this.productVisible = false
        this.toppingVisible = true
        this.filter = 'button-with-x'
      },
      deleteProduct () {
        this.selectedCategoryNo = 1
        this.productVisible = true
        this.toppingVisible = false

        // 필터에서 지우고 첫번째 탭에 밑줄 - 안
        document.getElementById('category' + this.selectedCategoryNo).classList.add('border-black')
      }
    }
  }
</script>
