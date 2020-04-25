<template>
  <div class="mb-3">
    <div class="flex items-center">
      <!--  topping name area  -->
      <div class="w-1/3">
        <span class="font-semibold text-sm">
          {{ topping.name }}
        </span>
      </div>
      <!--  subtopping area : Sub Toppings  -->
      <div v-if="topping.quantityType == 0 && subToppings != null" class="flex items-center">
        <radio-button v-for="subTopping in subToppings" :key="subTopping.subToppingNo" :name="topping.name + 'Radio'" @clickEvent="selectRadio"
                      :title="subTopping.subToppingName" :value="subTopping.subToppingNo" :checked="subTopping.subToppingNo === topping.subToppingNo ? true : false"></radio-button>
      </div>
      <!--  quantity area : Number Counter  -->
      <div v-else-if="topping.quantityType == 1" class="w-2/3">
        <number-counter :defaultValue="topping.value"></number-counter>
      </div>
      <!--  quantity area : Quantity Code  -->
      <div v-else class="flex items-center">
        <div v-for="quantityCode in quantityCodes" :key="quantityCode.quantityCode">
          <radio-button v-if="quantityCode.quantityType.indexOf(topping.quantityType) != -1" :name="topping.name + 'Radio'" @clickEvent="selectRadio"
                        :title="quantityCode.quantityName" :value="quantityCode.quantityCode" :checked="quantityCode.quantityCode === topping.quantityCode ? true : false"></radio-button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import NumberCounter from './NumberCounter'
  import RadioButton from './RadioButton'

  export default {
    name: 'Topping',
    components: { NumberCounter, RadioButton },
    props: {
      topping: Object
    },
    data () {
      return {
        quantityCodes: [
          { quantityCode: 1, quantityName: '없이', quantityType: '4,6,7' },
          { quantityCode: 2, quantityName: '적게', quantityType: '2,3,4,7,8' },
          { quantityCode: 3, quantityName: '보통', quantityType: '2,3,4,5,6,7' },
          { quantityCode: 4, quantityName: '많이', quantityType: '3,4' },
          { quantityCode: 5, quantityName: '클래식 시럽 없이', quantityType: '9' },
          { quantityCode: 6, quantityName: '바닐 시럽 없이', quantityType: '10' }
        ],
        subToppings: [ // subTopping ex
          { subToppingNo: 1, toppingNo: 2, subToppingName: '디카페인' },
          { subToppingNo: 2, toppingNo: 2, subToppingName: '1/2디카페인' }
        ],
        selectedValue: Number
      }
    },
    mounted () {
      // init default value
      this.selectedValue = this.topping.value

      // quantityCodes 세팅
      // subToppings 세팅
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

        if (this.topping.optionType === 1 && eventTarget.value === this.selectedValue) {
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
