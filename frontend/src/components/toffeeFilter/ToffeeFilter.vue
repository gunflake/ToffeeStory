<template>
  <div @click="closeSearchList">
    <table class="table-fixed table border w-full">
      <tbody>
      <tr class="border">
        <td class="flex items-center px-2 py-2">
          <!--   filter area   -->
          <div class="w-3/4">
            <component v-if="toppingVisible" :is="filter" :buttonText="selectedBeverage.beverageName" :buttonNo="selectedBeverage.beverageNo" v-on:deleteEvent="deleteProduct"></component>
          </div>
          <!--   search area   -->
          <div class="search-area flex items-center border border-gray-500 w-1/4 h-8" style="position: relative">
            <svg version="1.1" viewBox="0 0 32 32" width="32" height="32" aria-hidden="false" class="_2-tlh _1azRR _1mPD6"><path d="M31 28.64l-7.57-7.57a12.53 12.53 0 1 0-2.36 2.36l7.57 7.57zm-17.5-6a9.17 9.17 0 1 1 6.5-2.64 9.11 9.11 0 0 1-6.5 2.67z"></path></svg>
            <input @keyup="openSearchList" class="search-area appearance-none bg-transparent border-none w-full text-gray-700 text-xs mr-3 py-1 px-2 leading-tight focus:outline-none" type="text" placeholder="음료를 검색하세요." aria-label="Full name" v-model="searchQuery">
            <div v-show="isSearching" class="search-area search-list w-full h-40 overflow-scroll">
              <div v-for="beverage in searchedList" :key="beverage.beverageNo" @click="selectBeverage(beverage)"
                  class="search-area cursor-pointer select-none text-xs text-gray-800 bg-gray-100 hover:bg-gray-300 px-3 py-1">{{ beverage.beverageName }}</div>
            </div>
          </div>
        </td>
      </tr>
      <!--   beverage area   -->
      <tr v-show="beverageVisible" class="flex text-sm">
        <td class="flex-1 px-4 py-2">
          <!--   beverage category   -->
          <div class="mb-3">
            <nav class="bg-white flex">
              <div class="-mb-px flex justify-left">
                <a :id="'beverageCategory'+beverageCategory.beverageCategoryNo" v-for="beverageCategory in beverageCategoryList" :key="beverageCategory.beverageCategoryNo" @click="selectBeverageCategory($event, beverageCategory.beverageCategoryNo)"
                   role="button" :class="firstTabUnderline(beverageCategory.beverageCategoryNo)" class="beverage-category no-underline text-black border-b-2 border-transparent hover:border-black tracking-wide font-semibold py-3 mr-8">
                  {{beverageCategory.beverageCategoryName}}
                </a>
              </div>
            </nav>
          </div>
          <!--   beverage   -->
          <div class="mb-1">
            <div v-for="beverage in beverageList" :key="beverage.beverageNo" class="inline-flex">
              <div role="button" v-if="beverage.beverageCategory.beverageCategoryNo == selectedBeverage.beverageCategoryNo" @click="selectBeverage(beverage)" class="mr-3 mb-2 p-2 bg-gray-300 items-center text-gray-900 leading-none rounded-full">
                <a class="mx-2 text-left flex-auto">{{beverage.beverageName}}</a>
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
                <a :id="'toppingCategory'+toppingCategory.toppingCategoryNo" v-for="toppingCategory in toppingCategoryList" :key="toppingCategory.toppingCategoryNo" @click="selectToppingCategory($event, toppingCategory.toppingCategoryNo)"
                   role="button" :class="firstTabUnderline(toppingCategory.toppingCategoryNo)" class="topping-category no-underline text-black border-b-2 border-transparent hover:border-black tracking-wide font-semibold py-3 mr-8">
                  {{toppingCategory.toppingCategoryName}}
                </a>
              </div>
            </nav>
          </div>
          <div class="mb-1">
            <div class="w-full ml-3">
              <div v-for="beverageTopping in beverageToppingList" :key="beverageTopping.topping.toppingNo">
                <topping v-show="beverageTopping.topping.toppingCategory.toppingCategoryNo == selectedToppingCategoryNo" :beverageTopping="beverageTopping" :quantityCodeList="quantityCodeList"></topping>
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
        beverageCategoryList: [],
        beverageList: [],
        toppingCategoryList: [
          { toppingCategoryNo: 1, toppingCategoryName: '' },
          { toppingCategoryNo: 2, toppingCategoryName: '' }
        ],
        beverageToppingList: [],
        quantityCodeList: [],
        beverageVisible: true,
        toppingVisible: false,
        selectedBeverage: {
          beverageCategoryNo: 1,
          beverageNo: 0,
          beverageName: ''
        },
        selectedToppingCategoryNo: 1,
        filter: '',
        searchQuery: '',
        isSearching: false
      }
    },
    created () {
      // beverageCategoryList 세팅
      axios.get(`/api/products/beverageCategories`).then(response => {
        if (response.status === 200) {
          this.beverageCategoryList = response.data
        }
      }).catch(e => {
        console.log(e)
      })

      // beverageList 세팅
      axios.get(`/api/products/beverages`).then(response => {
        if (response.status === 200) {
          this.beverageList = response.data
        }
      }).catch(e => {
        console.log(e)
      })

      // quantityCodes 세팅
      axios.get(`/api/products/quantityCodes`).then(response => {
        if (response.status === 200) {
          this.quantityCodeList = response.data
        }
      }).catch(e => {
        console.log(e)
      })
    },
    computed: {
      // 검색 결과 리스트
      searchedList () {
        if (this.searchQuery !== '') {
          return this.beverageList.filter(beverage => {
            return beverage.beverageName.includes(this.searchQuery)
          })
        } else {
          return null
        }
      }
    },
    methods: {
      // 첫번째 탭에 밑줄
      firstTabUnderline (beverageCategoryNo) {
        if (beverageCategoryNo === 1) {
          return 'border-black'
        }
      },
      // 음료 검색 리스트 활성화
      openSearchList () {
        this.isSearching = true
      },
      // 음료 검색 리스트 비활성화
      closeSearchList (event) {
        if (!event.target.matches('.search-area')) {
          this.isSearching = false
        }
      },
      // 음료 카테고리 선택
      selectBeverageCategory (event, beverageCategoryNo) {
        this.selectedBeverage.beverageCategoryNo = beverageCategoryNo
        this.tabUnderline(true, event.target, '.beverage-category')
      },
      // 토핑 카테고리 선택
      selectToppingCategory (event, toppingCategoryNo) {
        this.selectedToppingCategoryNo = toppingCategoryNo
        this.tabUnderline(true, event.target, '.topping-category')
      },
      // 음료 선택
      selectBeverage (beverage) {
        this.selectedBeverage.beverageNo = beverage.beverageNo
        this.selectedBeverage.beverageName = beverage.beverageName
        this.beverageVisible = false
        this.toppingVisible = true
        this.filter = 'button-with-x'
        this.searchQuery = ''
        this.isSearching = false

        document.getElementById('toppingCategory' + this.selectedToppingCategoryNo).classList.add('border-black')

        const params = new URLSearchParams()
        params.append('beverageNo', beverage.beverageNo)

        // toppingCategoryList, toppingList 세팅
        axios.post(`/api/products/toppings`, params).then(response => {
          if (response.status === 200) {
            this.beverageToppingList = response.data

            // toppingCategoryList 세팅
            const map = new Map()
            this.beverageToppingList.forEach(function (data) {
              const toppingCategory = data.topping.toppingCategory
              map.set(toppingCategory.toppingCategoryNo, toppingCategory.toppingCategoryName)
            })

            const toppingCategoryList = []
            map.forEach(function (value, key) {
              const toppingCategory = {}
              toppingCategory.toppingCategoryNo = key
              toppingCategory.toppingCategoryName = value
              toppingCategoryList.push(toppingCategory)
            })
            this.toppingCategoryList = toppingCategoryList
          }
        }).catch(e => {
          console.log(e)
        })
      },
      // 필터에서 상품 삭제
      deleteProduct () {
        this.selectedBeverage.beverageCategoryNo = 1
        this.selectedToppingCategoryNo = 1
        this.beverageVisible = true
        this.toppingVisible = false
        this.tabUnderline(false, 'beverageCategory', '.beverage-category')
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
          document.getElementById(element + this.selectedBeverage.beverageCategoryNo).classList.add('border-black')
        }
      }
    }
  }
</script>
