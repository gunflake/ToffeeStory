<template>
  <div>
    <table class="table-fixed w-full">
      <tbody>
      <tr>
        <td class="w-1/6 border px-4 py-2 text-center font-semibold">필터</td>
        <td class="w-5/6 border px-4 py-2">
          <div class="p-2 bg-gray-300 items-center text-gray-900 text-sm leading-none rounded-full inline-flex">
            <span class="mx-2 text-left flex-auto">카페 아메리카노</span>
            <button @click="removeFilter">
              <svg class="fill-current opacity-75 h-4 w-4" role="button" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"><path d="M14.348 14.849a1.2 1.2 0 0 1-1.697 0L10 11.819l-2.651 3.029a1.2 1.2 0 1 1-1.697-1.697l2.758-3.15-2.759-3.152a1.2 1.2 0 1 1 1.697-1.697L10 8.183l2.651-3.031a1.2 1.2 0 1 1 1.697 1.697l-2.758 3.152 2.758 3.15a1.2 1.2 0 0 1 0 1.698z"/></svg>
            </button>
          </div>
        </td>
      </tr>
      <tr>
        <td class="border px-4 py-2 text-center font-semibold">음료</td>
        <td class="border px-4 py-2">
          <div class="mb-3">
            <nav class="bg-white flex">
              <div class="-mb-px flex justify-left w-4/5">
                <a v-for="item in categories" :key="item.name" class="no-underline text-black border-b-2 border-transparent hover:border-black tracking-wide font-semibold text-sm py-3 mr-8" href="#">
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
          <div class="mb-1">
            <div v-for="item in products" :key="item.name" class="mr-3 mb-2 p-2 bg-gray-300 items-center text-gray-900 text-sm leading-none rounded-full inline-flex">
              <a class="mx-2 text-left flex-auto" role="button">{{item.name}}</a>
            </div>
          </div>
        </td>
      </tr>
      <tr>
        <td class="border px-4 py-2 text-center font-semibold">토핑</td>
        <td class="border px-4 py-2">
          <div class="mb-3">
            <nav class="bg-white flex">
              <div class="-mb-px flex justify-left">
                <a v-for="item in toppingCategories" :key="item.name" class="no-underline text-black border-b-2 border-transparent hover:border-black tracking-wide font-semibold text-sm py-3 mr-8" href="#">
                  {{item.name}}
                </a>
              </div>
            </nav>
          </div>
          <div class="mb-1">
            <table class="table-fixed text-sm">
              <tbody>
                <tr>
                  <td class="px-4 py-2 font-semibold">프라푸치노용 시럽</td>
                  <td class="px-4 py-2">
                    <label class="inline-flex items-center">
                      <input type="radio" class="form-radio" name="radio" value="1" checked="checked">
                      <span class="ml-2">일반</span>
                    </label>
                    <label class="inline-flex items-center">
                      <input type="radio" class="form-radio" name="radio" value="2">
                      <span class="ml-2">라이트 (휘핑없이 추천)</span>
                    </label>
                  </td>
                </tr>
                <tr>
                  <td class="px-4 py-2 font-semibold">바닐라 시럽</td>
                  <td class="px-4 py-2 flex">
                    <div class="flex justify-start">
                      <div class="container w-32 m-auto">
                        <div class="custom-number-input h-10 w-32">
                          <div class="flex flex-row h-10 w-full rounded-lg relative bg-transparent mt-1">
                            <button @click="decrement" data-action="decrement" class=" bg-gray-300 text-gray-600 hover:text-gray-700 hover:bg-gray-400 h-full w-20 rounded-l cursor-pointer outline-none">
                              <span class="m-auto text-2xl font-thin">−</span>
                            </button>
                            <input type="number" class="outline-none focus:outline-none text-center w-full bg-white font-semibold text-md hover:text-black focus:text-black  md:text-basecursor-default flex items-center text-gray-700  outline-none" name="custom-input-number" value="0">
                            <button @click="increment" data-action="increment" class="bg-gray-300 text-gray-600 hover:text-gray-700 hover:bg-gray-400 h-full w-20 rounded-r cursor-pointer">
                              <span class="m-auto text-2xl font-thin">+</span>
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <td class="px-4 py-2 font-semibold">헤이즐넛 시럽</td>
                  <td class="px-4 py-2 flex">
                    <div class="flex justify-start">
                      <div class="container w-32 m-auto">
                        <div class="custom-number-input h-10 w-32">
                          <div class="flex flex-row h-10 w-full rounded-lg relative bg-transparent mt-1">
                            <button @click="decrement" data-action="decrement" class=" bg-gray-300 text-gray-600 hover:text-gray-700 hover:bg-gray-400 h-full w-20 rounded-l cursor-pointer outline-none">
                              <span class="m-auto text-2xl font-thin">−</span>
                            </button>
                            <input type="number" class="outline-none focus:outline-none text-center w-full bg-white font-semibold text-md hover:text-black focus:text-black  md:text-basecursor-default flex items-center text-gray-700  outline-none" name="custom-input-number" value="0">
                            <button @click="increment" data-action="increment" class="bg-gray-300 text-gray-600 hover:text-gray-700 hover:bg-gray-400 h-full w-20 rounded-r cursor-pointer">
                              <span class="m-auto text-2xl font-thin">+</span>
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>
<script>
  export default {
    name: 'ToffeeFilter',
    props: {
      title: String,
      type: String,
      visible: Boolean,
      url: String
    },
    data () {
      return {
        categories: [
          { name: '에스프레소' },
          { name: '프라푸치노' },
          { name: '블렌디드' },
          { name: '피지오' },
          { name: '티바나' },
          { name: '기타 제조음료' },
          { name: '병음료' }
        ],
        toppingCategories: [
          { name: '커피' },
          { name: '시럽' },
          { name: '베이스' },
          { name: '얼음' },
          { name: '휘핑크림' }
        ],
        products: [
          { name: '카페 아메리카노' },
          { name: '아이스 카페 아메리카노' },
          { name: '카페 모카' },
          { name: '아이스 카페 모카' },
          { name: '카페 라떼' },
          { name: '아이스 카페 라떼' },
          { name: '카푸치노' },
          { name: '아이스 카푸치노' },
          { name: '화이트 초콜릿 모카' },
          { name: '아이스 화이트 초콜릿 모카' },
          { name: '스타벅스 돌체 라떼' },
          { name: '아이스 스타벅스 돌체 라떼노' }
        ]
      }
    },
    methods: {
      decrement (e) {
        const btn = e.target.parentNode.parentElement.querySelector(
          'button[data-action="decrement"]'
        )
        const target = btn.nextElementSibling
        let value = Number(target.value)
        value--
        target.value = value
      },
      increment (e) {
        const btn = e.target.parentNode.parentElement.querySelector(
          'button[data-action="decrement"]'
        )
        const target = btn.nextElementSibling
        let value = Number(target.value)
        value++
        target.value = value
      },
      removeFilter (e) {
        e.target.parent.remove()
      },
      addToFilter () {

      }
    }
  }
</script>
<style>
  input[type="number"]::-webkit-inner-spin-button,
  input[type="number"]::-webkit-outer-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }

  .custom-number-input input:focus {
    outline: none !important;
  }

  .custom-number-input button:focus {
    outline: none !important;
  }

  button span {
    line-height: 35px;
  }
</style>
