<template>
  <div>
    <table class="table-fixed">
      <tbody>
      <tr>
        <td class="w-1/6 border px-4 py-2 text-center font-semibold">음료</td>
        <td class="w-5/6 border px-4 py-2">
          <div class="mb-3">
            <nav class="bg-white flex">
              <div class="-mb-px flex justify-left w-4/5">
                <a v-for="item in categories" :key="item.no" v-on:click="selectTab(item.no)" role="button" class="no-underline text-black border-b-2 border-transparent hover:border-black tracking-wide font-semibold text-sm py-3 mr-8">
                  {{item.name}}
                </a>
              </div>
              <div class="flex w-1/5">
                <form>
                  <div class="flex items-center border border-gray-500">
                    <input class="appearance-none bg-transparent border-none w-full text-gray-700 text-xs mr-3 py-1 px-2 leading-tight focus:outline-none" type="text" placeholder="음료를 검색하세요." aria-label="Full name">
                    <button class="flex-shrink-0 bg-gray-500 border-gray-500 text-xs border-4 text-white py-1 px-2" type="button">검색</button>
                  </div>
                </form>
              </div>
            </nav>
          </div>
          <div class="mb-1" id="categoryTab">
            <div v-show="show" v-for="category in categories" :key="category.no">
              category {{category.no}}
            </div>
            <div v-for="item in products" :key="item.no" class="mr-3 mb-2 p-2 bg-gray-300 items-center text-gray-900 text-sm leading-none rounded-full inline-flex">
              <a class="mx-2 text-left flex-auto" role="button">{{item.name}}</a>
            </div>
          </div>
        </td>
      </tr>
      <tr>
        <td class="border px-4 py-2 text-center font-semibold">토핑</td>
        <!-- 토핑 카테고리 -->
        <td class="border px-4 py-2">
          <div class="mb-3">
            <v-tabs>
              <v-tab>Item One</v-tab>
              <v-tab>Item Two</v-tab>
              <v-tab>Item Three</v-tab>
            </v-tabs>
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
  export default {
    name: 'ToffeeFilter',
    components: { NumberCounter },
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
        toppingCategories: [
          { no: 1, name: '커피' },
          { no: 2, name: '시럽' },
          { no: 3, name: '베이스' },
          { no: 4, name: '얼음' },
          { no: 5, name: '휘핑크림' }
        ],
        products: [
          { categoryNo: 1, no: 1, name: '카페 아메리카노' },
          { categoryNo: 1, no: 2, name: '아이스 카페 아메리카노' },
          { categoryNo: 1, no: 3, name: '카페 모카' },
          { categoryNo: 1, no: 4, name: '아이스 카페 모카' },
          { categoryNo: 2, no: 5, name: '카페 라떼' },
          { categoryNo: 2, no: 6, name: '아이스 카페 라떼' },
          { categoryNo: 2, no: 7, name: '카푸치노' },
          { categoryNo: 2, no: 8, name: '아이스 카푸치노' },
          { categoryNo: 3, no: 9, name: '화이트 초콜릿 모카' },
          { categoryNo: 3, no: 10, name: '아이스 화이트 초콜릿 모카' },
          { categoryNo: 3, no: 11, name: '스타벅스 돌체 라떼' },
          { categoryNo: 3, no: 12, name: '아이스 스타벅스 돌체 라떼' }
        ]
      }
    },
    methods: {
      selectTopping (itemNo) {
        console.log(itemNo)
      }
    }
  }
</script>
