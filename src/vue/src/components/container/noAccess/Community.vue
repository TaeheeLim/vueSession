<template>
<div class="head-container">
  <div class="container">
    <div class="real-container"> 
      <div class="left-container">
        <router-link @click="step=0; category='free'" class ="board-direction" to="/community/free">자유게시판</router-link>
        <router-link @click="step=0; category='qna'" class ="board-direction" to="/community/qna">문의 게시판</router-link>
        <button  @click="[click(), step=1, this.changeUpdateCheck()]" class="board-direction" :disabled="blockWrite == true">글 작성</button>
      </div>
      <div class="input-container">
        <input type="text" class="search-input" @keyup.enter="search" v-model="key">
        <img src="@/assets/돋보기2.png" @click="search">
      </div>
    </div>  
    <div class="body-router">
      <div id="write-div">
        <Write v-if="isOpen" 
              :step="step" 
              :category="category"/>
      </div>
      <router-view></router-view>
    </div>
  </div>
</div>  
</template>

<script>
import Write from '@/components/component/noAccess/Community/BoardWrite.vue';
import { mapMutations, mapState } from 'vuex';

export default {
  data(){
    return {
      key: "",
      step : 0,
      category : "free",
      isOpen : false,
    }
  },

  components: {
    Write,
  },
  computed : {
    ...mapState({
      updateCheck : state => state.community.updateCheck,
      blockWrite : state => state.community.blockWrite,
    })
  },

  methods: {
    ...mapMutations({
      changeUpdateCheck : 'community/changeUpdateCheck'
    }),

    search() {
      console.log(this.key)
    },

    click(){
      this.isOpen = !this.isOpen;
      console.log(this.isOpen)
    },

  }
}
</script>

<style scoped>
input {
  color: #000;
}

.head-container {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 22px;
  margin-bottom: 10px;
  width: 100vm;
  height: calc(100vh - 70px);
  overflow: hidden;
}

.real-container {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.board-direction {
  background-color: coral;
  border-radius: 10px;
  padding: 2px 16px;
  font-size: 16px;
  margin-right: 10px;
  cursor: pointer;
}

.board-direction:last-child {
  margin: 0;
}

.search-input {
  border-radius: 10px;
  color : white;
  background-color: #414556;
  padding: 0 5px;
  position: relative;
  height: 20px;
  width : 160px;
}

.input-container {
  position: relative;
}

img {
  height : 20px;
  width : 20px;
  position: absolute;
  transform: translate(-50%,-50%);
  top: 50%;
  right: 5px;
}

.write-div {
  width: 60vw;
  height: 80%;
  background-color: #2C2F3B;
  margin-bottom: 20px;
  position : relative;
}

</style>