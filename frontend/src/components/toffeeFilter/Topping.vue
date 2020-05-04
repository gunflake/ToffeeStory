<template>
  <div class="mb-3">
    <div class="flex items-center w-full">
      <!--  topping name area  -->
      <div class="w-1/5">
        <span class="font-semibold text-sm">
          {{ topping.toppingName }}
        </span>
      </div>
      <!--  subTopping area : Sub Toppings  -->
      <div v-if="topping.quantityType.quantityTypeNo == 0 && subToppingList != null" class="flex items-center w-4/5">
        <radio-button v-for="subTopping in subToppingList" :key="subTopping.subToppingNo" :name="topping.toppingName + 'Radio'" @clickEvent="selectRadio"
                      :title="subTopping.subToppingName" :value="subTopping.subToppingNo" :checked="subTopping.subToppingNo === beverageTopping.subTopping.subToppingNo ? true : false"></radio-button>
      </div>
      <!--  quantity area : Number Counter  -->
      <div v-else-if="topping.quantityType.quantityTypeNo == 1" class="w-4/5">
        <number-counter :defaultValue="beverageTopping.toppingValue"></number-counter>
      </div>
      <!--  quantity area : Quantity Code  -->
      <div v-else class="flex items-center w-4/5">
        <div v-for="quantityCode in quantityCodeList" :key="quantityCode.quantityCodeNo">
          <radio-button v-if="quantityCode.quantityType.indexOf(topping.quantityType.quantityTypeNo) != -1" :name="topping.toppingName + 'Radio'" @clickEvent="selectRadio"
                        :title="quantityCode.quantityName" :value="quantityCode.quantityCodeNo" :checked="quantityCode.quantityCodeNo === beverageTopping.quantityCode.quantityCodeNo ? true : false"></radio-button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import NumberCounter from './NumberCounter'
  import RadioButton from './RadioButton'
  import axios from 'axios'
  export default {
    name: 'Topping',
    components: { NumberCounter, RadioButton },
    props: {
      beverageTopping: Object,
      quantityCodeList: Array
    },
    data () {
      return {
        subToppingList: [],
        selectedValue: 0,
        topping: {
          toppingNo: 0,
          toppingName: '',
          toppingPrice: 0,
          toppingCategory: { toppingCategoryNo: 0 },
          quantityType: { quantityTypeNo: 0 }
        }
      }
    },
    mounted () {
      this.topping = this.beverageTopping.topping
      this.selectedValue = this.beverageTopping.toppingValue

      // subToppings 세팅
      const params = new URLSearchParams()
      params.append('toppingNo', this.topping.toppingNo)

      axios.post(`/api/products/subToppings`, params).then(response => {
        if (response.status === 200) {
          this.subToppingList = response.data
        }
      }).catch(e => {
        console.log(e)
      })
    },
    methods: {
      deleteButton () {
        this.$emit('deleteEvent')
      },
      selectRadio (eventTarget) {
        let name = eventTarget.name
        let radioButtons = document.querySelectorAll('input[name="' + name + '"]')

        // 전체 버튼 효과 제거
        for (let i = 0; i < radioButtons.length; i++) {
          radioButtons[i].parentElement.classList.remove('bg-gray-500', 'border-transparent', 'text-white')
          radioButtons[i].parentElement.classList.add('bg-transparent', 'border-gray-400', 'text-gray-600')
        }

        if (this.beverageTopping.optionType === 1 && eventTarget.value === this.selectedValue) {
          // 추가옵션 : 재선택시 체크 해제
          eventTarget.checked = false
          this.selectedValue = null
        } else {
          // 선택된 버튼 효과 활성화
          document.querySelector('input[name="' + name + '"]:checked').parentElement.classList.remove('bg-transparent', 'border-gray-400', 'text-gray-600')
          document.querySelector('input[name="' + name + '"]:checked').parentElement.classList.add('bg-gray-500', 'border-transparent', 'text-white')
          this.selectedValue = eventTarget.value
        }
      }
    }
  }
</script>
