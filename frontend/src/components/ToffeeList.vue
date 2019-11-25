<template>
  <div>
    <div class="images-container">
      <div class="images-item" v-for="(image,index) of images" :key="index">
        <div class="images-card">
          <img class="images-card__image" :src="image.urls.small" @load="masksHide.push(index)">
          <div class="images-card__mask" :style="{'background-color':image.color}" v-if="!masksHide.includes(index)"></div>
        </div>
      </div>
    </div>
    <scroll-loader :loader-method="getImagesInfo" :loader-enable="loadMore" loader-color="rgba(102,102,102,.5)">
    </scroll-loader>
  </div>
</template>
<script src="../assets/js/toffeeList.js"></script>
