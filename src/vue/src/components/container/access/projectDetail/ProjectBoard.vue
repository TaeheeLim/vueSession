<template>
<div>
  <Insert/>
  <List/>
</div>  
</template>

<script>
import List from '../../../component/acess/projectDetail/projectBoard/List.vue'
import Insert from '../../../component/acess/projectDetail/projectBoard/Insert.vue'
export default {
  components: {
    List,
    Insert
  }
}
</script>

<style scoped>
* {
  color: #fff;
}
</style>