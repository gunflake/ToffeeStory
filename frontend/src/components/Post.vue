<template>
  <transition name="modal">
    <div class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container w-full mx-auto">
          <div>
            <div class="bg-white p-6" style="margin-top:10%;">
              <div class="modal-header">
                <slot name="header">
                  <button class="modal-default-button" style="float:right;color:gray;" @click="$emit('close')">X</button>
                  </slot>
              </div>
              <br>
              <div class="modal-body">
                <slot name="body">
                  <div class="inline">
                    <div class="inline">
                      <img class="h-10 w-10 rounded-full mr-10 inline" style="margin:10px;" src="https://randomuser.me/api/portraits/women/21.jpg">
                      <span style="font-size:110%;">coffeelover</span>
                    </div>
                    <div class="inline" style="margin-left:62%;">
                      <a style="cursor: pointer"><i class="fa fa-heart-o fa-2x"></i></a>
                      <span style="margin-left:1%;margin-right: 2%;">256</span>
                      <a style="cursor: pointer"><i class="fa fa-bookmark-o fa-2x"></i></a>
                      <span style="margin-left:1%;">256</span>
                    </div>
                  </div>
                  <!-- related post -->
                  <img src="@/assets/image/main_img1.jpg" style="height: 400px;margin-left:50%"/>
                  <!-- related tag -->
                </slot>
              </div>

              <div class="modal-footer">
                <slot name="footer">
                </slot>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
  export default {
    name: 'Post'
  }
</script>

<style scoped>
  .modal-mask {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .5);
    display: table;
  }
  .modal-container {
    width: 900px;
    top: 10%;
  }
</style>
