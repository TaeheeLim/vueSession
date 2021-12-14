<template>
  <div class="erd-container">

    <SideBar  :topTitle="$store.state.erd.sideBarData.title"
              :top="true"
              :bottom="false"
              :topPath="$store.state.erd.sideBarData.path"
              :topData="$store.state.erd.sideBarData.topData"
              :isTree="false"
              :textColor="$store.state.erd.sideBarData.textColor"/>

    <router-view></router-view>

    <ShowQuery v-if="$store.state.erd.isShowQuery"/>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import SideBar from '../../../component/acess/projectDetail/erdAndView/SideBar.vue'
import ShowQuery from '../../../component/acess/projectDetail/erdAndView/erd/ShowQuery.vue'

export default {
  name: "erd",
  data() {
    return {
      
    }
  },
  components: {
    SideBar,
    ShowQuery
  },
  methods: {
    ...mapMutations({
    })
  },
  mounted() {
  }
}
</script>

<style scoped>
.erd-container {
  height: calc(100vh - 70px);
  display: flex;
  position: relative;
}

</style>