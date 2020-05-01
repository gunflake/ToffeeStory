<template>
  <div @click="closeSearchList">
    <table class="table-fixed table border w-full">
      <tbody>
      <tr class="border">
        <td class="flex items-center px-2 py-2">
          <!--   filter area   -->
          <div class="w-3/4">
            <component v-if="toppingVisible" :is="filter" :productName="selectedProductName" :productNo="selectedProductNo" v-on:deleteEvent="deleteProduct"></component>
          </div>
          <!--   search area   -->
          <div class="search-area flex items-center border border-gray-500 w-1/4 h-8" style="position: relative">
            <svg version="1.1" viewBox="0 0 32 32" width="32" height="32" aria-hidden="false" class="_2-tlh _1azRR _1mPD6"><path d="M31 28.64l-7.57-7.57a12.53 12.53 0 1 0-2.36 2.36l7.57 7.57zm-17.5-6a9.17 9.17 0 1 1 6.5-2.64 9.11 9.11 0 0 1-6.5 2.67z"></path></svg>
            <input @keyup="openSearchList" class="search-area appearance-none bg-transparent border-none w-full text-gray-700 text-xs mr-3 py-1 px-2 leading-tight focus:outline-none" type="text" placeholder="음료를 검색하세요." aria-label="Full name" v-model="searchQuery">
            <div v-show="isSearching" class="search-area search-list w-full h-40 overflow-scroll">
              <div v-for="product in searchedList" :key="product.no" @click="selectProduct(product)"
                  class="search-area cursor-pointer select-none text-xs text-gray-800 bg-gray-100 hover:bg-gray-300 px-3 py-1">{{ product.name }}</div>
            </div>
          </div>
        </td>
      </tr>
      <!--   product area   -->
      <tr v-show="productVisible" class="flex text-sm">
        <td class="flex-1 px-4 py-2">
          <!--   product category   -->
          <div class="mb-3">
            <nav class="bg-white flex">
              <div class="-mb-px flex justify-left">
                <a :id="'productCategory'+productCategory.productCategoryNo" v-for="productCategory in productCategories" :key="productCategory.productCategoryNo" @click="selectProductCategory($event, productCategory.productCategoryNo)"
                   role="button" :class="firstTabUnderline(productCategory.productCategoryNo)" class="product-category no-underline text-black border-b-2 border-transparent hover:border-black tracking-wide font-semibold py-3 mr-8">
                  {{productCategory.productCategoryName}}
                </a>
              </div>
            </nav>
          </div>
          <!--   product   -->
          <div class="mb-1">
            <div v-for="product in products" :key="product.no" class="inline-flex">
              <div role="button" v-if="product.categoryNo == selectedProductCategoryNo" @click="selectProduct(product)" class="mr-3 mb-2 p-2 bg-gray-300 items-center text-gray-900 leading-none rounded-full">
                <a class="mx-2 text-left flex-auto">{{product.name}}</a>
              </div>
            </div>
          </div>
        </td>
      </tr>
      <!--   topping area   -->
      <tr v-show="toppingVisible" class="flex text-sm">
        <td class="flex-1 px-4 py-2">
          <div class="mb-3">
            <nav class="bg-white flex">
              <div class="-mb-px flex justify-left">
                <a :id="'toppingCategory'+toppingCategory.no" v-for="toppingCategory in toppingCategories" :key="toppingCategory.no" @click="selectToppingCategory($event, toppingCategory.no)"
                   role="button" :class="firstTabUnderline(toppingCategory.no)" class="topping-category no-underline text-black border-b-2 border-transparent hover:border-black tracking-wide font-semibold py-3 mr-8">
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
<style>
  .search-list {
    position: absolute;
    left: 0;
    top: 2rem;
  }
</style>
<script>
  import NumberCounter from '@/components/toffeeFilter/NumberCounter'
  import ButtonWithX from '@/components/toffeeFilter/ButtonWithX'
  import Topping from '@/components/toffeeFilter/Topping'
  import axios from 'axios'
  export default {
    name: 'ToffeeFilter',
    components: { NumberCounter, ButtonWithX, Topping },
    data () {
      return {
        productCategories: [],
        products: [],
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
          { categoryNo: 1, no: 1, name: '에스프레소 샷', toppingPrice: 0, quantityType: 1, subToppingNo: 0, quantityCode: 0, value: 0, optionType: 1 },
          { categoryNo: 1, no: 2, name: '디카페인', toppingPrice: 0, quantityType: 0 },
          { categoryNo: 1, no: 3, name: '프라푸치노 로스트', toppingPrice: 0, quantityType: 1, subToppingNo: 0, quantityCode: 0, value: 0, optionType: 1 },
          // 시럽
          { categoryNo: 2, no: 4, name: '클래식 시럽', toppingPrice: 0, quantityType: 1, subToppingNo: 0, quantityCode: 0, value: 3, optionType: 1 },
          { categoryNo: 2, no: 5, name: '바닐라 시럽', toppingPrice: 0, quantityType: 1, subToppingNo: 0, quantityCode: 0, value: 0, optionType: 1 },
          { categoryNo: 2, no: 6, name: '헤이즐넛 시럽', toppingPrice: 0, quantityType: 1, subToppingNo: 0, quantityCode: 0, value: 0, optionType: 1 },
          { categoryNo: 2, no: 7, name: '카라멜 시럽', toppingPrice: 0, quantityType: 1, subToppingNo: 0, quantityCode: 0, value: 0, optionType: 1 },
          // 베이스
          { categoryNo: 3, no: 8, name: '물', toppingPrice: 0, quantityType: 3, subToppingNo: 0, quantityCode: 3, value: 0, optionType: 1 },
          // 우유/음료 온도
          { categoryNo: 4, no: 9, name: '우유종류', toppingPrice: 0, quantityType: 0, subToppingNo: 1, quantityCode: 0, value: 0, optionType: 1 },
          // 기타
          { categoryNo: 5, no: 10, name: '저지방 요거트', toppingPrice: 0, quantityType: 0, subToppingNo: 1, quantityCode: 0, value: 0, optionType: 1 },
          // 얼음
          { categoryNo: 6, no: 11, name: '얼음', toppingPrice: 0, quantityType: 2, subToppingNo: 0, quantityCode: 3, value: 0, optionType: 1 },
          // 자바칩
          { categoryNo: 7, no: 12, name: '자바칩', toppingPrice: 0, quantityType: 0, subToppingNo: 1, quantityCode: 0, value: 0, optionType: 1 },
          { categoryNo: 7, no: 13, name: '프라푸치노 자바칩', toppingPrice: 0, quantityType: 1, subToppingNo: 0, quantityCode: 0, value: 0, optionType: 1 },
          // 휘핑크림
          { categoryNo: 8, no: 14, name: '휘핑크림', toppingPrice: 0, quantityType: 3, subToppingNo: 0, quantityCode: 3, value: 0, optionType: 1 },
          // 드리즐
          { categoryNo: 9, no: 15, name: '카라멜 드리즐', toppingPrice: 0, quantityType: 3, subToppingNo: 0, quantityCode: 3, value: 0, optionType: 1 },
          { categoryNo: 9, no: 16, name: '초콜릿 드리즐', toppingPrice: 0, quantityType: 3, subToppingNo: 0, quantityCode: 3, value: 0, optionType: 1 }
        ],
        productTopping:
          { subToppingNo: 1, quantityCode: 1, value: 0, optionType: 1 },
        productVisible: true,
        toppingVisible: false,
        selectedProductCategoryNo: 1,
        selectedProductNo: 0,
        selectedProductName: '',
        selectedToppingCategoryNo: 1,
        filter: '',
        searchQuery: '',
        isSearching: false
      }
    },
    created () {
      // productCategories 세팅
      axios.get(`/api/products/getProductCategories`).then(response => {
        if (response.status === 200) {
          this.productCategories = response.data
        }
      }).catch(e => {
        console.log(e)
      })

      // products 세팅
      axios.get(`/api/products/getProducts`).then(response => {
        if (response.status === 200) {
          this.products = response.data
        }
      }).catch(e => {
        console.log(e)
      })
    },
    computed: {
      // 검색 결과 리스트
      searchedList () {
        if (this.searchQuery !== '') {
          return this.products.filter(product => {
            return product.name.includes(this.searchQuery)
          })
        } else {
          return null
        }
      }
    },
    methods: {
      // 첫번째 탭에 밑줄
      firstTabUnderline (productCategoryNo) {
        if (productCategoryNo === 1) {
          return 'border-black'
        }
      },
      // 상품 검색 리스트 활성화
      openSearchList () {
        this.isSearching = true
      },
      // 상품 검색 리스트 비활성화
      closeSearchList (event) {
        if (!event.target.matches('.search-area')) {
          this.isSearching = false
        }
      },
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
      selectProduct (product) {
        this.selectedProductNo = product.no
        this.selectedProductName = product.name
        this.productVisible = false
        this.toppingVisible = true
        this.filter = 'button-with-x'
        this.searchQuery = ''
        this.isSearching = false

        document.getElementById('toppingCategory' + this.selectedToppingCategoryNo).classList.add('border-black')

        // toppings 세팅 (초기값까지)
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
