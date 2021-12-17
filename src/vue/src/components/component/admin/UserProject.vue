<template>
  <div>
    <div class="header">
      <span class="title">User Project</span>
      <input class="search" type="text" placeholder="🔎" />
    </div>

    <ul class="body">
      <li v-for="data in projectData" :key="data">
        <span> {{ data.name }} &nbsp; # {{ data.id }} </span>
        <span> 팀장 : &nbsp; {{ data.memId }} </span>
        <span>
          상태 :
          <select @change="(e)=>modifyState(e,data.id)">
            <option :selected="data.isCom!==''" value="진행">진행</option>
            <option :selected="data.isCom!==''" value="완료">완료</option>
            <option :selected="data.isDel!==''" value="삭제">삭제</option>
          </select>
        </span>
        <span> 기간 : &nbsp; {{ data.start }} ~ {{ data.end }} </span>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapMutations, mapState } from "vuex";
export default {
  computed: mapState({
    projectData: (state) => state.userProject.projectData,
  }),
  data() {
    return {
    };
  },
  methods: {
    ...mapMutations({
      update: "userProject/update",
    }),
    modifyState(e,id){

      let payload = [];

      payload.push(id,e.target.value)

      this.update(payload);


    }
  },
};
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 40px;
}

.title {
  font-size: 28px;
  color: #fff;
}

.search {
  background: #414556;
  width: 150px;
  border: none;
  outline: none;
  border-radius: 8px;
  color: #fff;
}

.body {
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: scroll;
  height: 90%;
}

.body li {
  margin-bottom: 22px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  background: #2c2f3b;
  height: 30px;
  width: 90%;
  padding: 15px;
  color: #fff;
  border-radius: 6px;
}

.body li span select {
  border: none;
  background: none;
  color: #fff;
  outline: none;
  width: fit-content;
  text-align: center;
}

.body li span select option {
  background: #2c2f3b;
  color: #fff;
}

.body li:last-child {
  margin-bottom: 35px;
}

::-webkit-scrollbar {
  width: 0px;
}

@media (max-width:1480px) {

  .body li{
    display: grid;
    grid-template-columns: 1fr 1fr;
    row-gap: 15px;
    padding: 15px;
    height: fit-content;
  }
}

@media (max-width:1268px){
  .body{
    height: 50vh;
    overflow: scroll;
  }
}

@media (max-width:768px){
  .header{
    display: flex;
    flex-direction: column;
  }

  .header .search{
    margin-top: 25px;
    height: 30px; 
    width: 50%;
  }

  .body{
    height: 50vh;
    overflow: scroll;
  }

  .body li{
    display: grid;
    grid-template-columns: 1fr 2fr;
    text-align: center;
    width: 100%;
    font-size: 14px;
  }
}

@media(max-width: 535px){
  .header .search{
    margin-top: 25px;
    height: 30px; 
    width: 100%;
  }

  .body{
    overflow: scroll;
    height: 50vh;
  }

   .body li{
    display: flex;
    flex-direction: column;
    width: 100%;
    font-size: 12px;
    align-items: flex-start;
    
  }

  .body li span select{
    font-size: 12px;
  }
}
</style>