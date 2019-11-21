<template>
<div class="image-container">
  <scroll-loader :loader-method="getImagesInfo" :loader-enable="loadMore">
  </scroll-loader>

  <!-- Replace the default loading animation with slot -->
  <scroll-loader :loader-method="getImagesInfo" :loader-enable="loadMore">
    <div>Loading...</div>
  </scroll-loader>
</div>
</template>
<script src="../assets/js/toffeeList.js"></script>
<script>

</script>
